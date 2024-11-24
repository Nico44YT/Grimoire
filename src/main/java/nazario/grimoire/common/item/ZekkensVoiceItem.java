package nazario.grimoire.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import nazario.grimoire.registry.EnchantmentRegistry;
import nazario.grimoire.registry.SoundRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// * Yuukis Guitar * //
public class ZekkensVoiceItem extends ToolItem implements TwoHanded {
    private final float attackDamage;
    private final float attackSpeed;
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public ZekkensVoiceItem(Item.Settings settings) {
        super(new ToolMaterial() {
            @Override
            public int getDurability() {
                return 512;
            }

            @Override
            public float getMiningSpeedMultiplier() {
                return 0;
            }

            @Override
            public float getAttackDamage() {
                return 4;
            }

            @Override
            public int getMiningLevel() {
                return 0;
            }

            @Override
            public int getEnchantability() {
                return 5;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.ofStacks(new ItemStack(Items.STRING));
            }
        }, settings);

        this.attackDamage = 6f;
        this.attackSpeed = -4f + 1.7f;

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double)this.attackDamage, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", (double)this.attackSpeed, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) return super.use(world, user, hand);

        if (EnchantmentHelper.getLevel(EnchantmentRegistry.ZEKKENS_NOTES_BOOM, user.getStackInHand(hand)) > 0) {
            notesBoom(world, user);
        }

        return super.use(world, user, hand);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(EnchantmentHelper.getLevel(EnchantmentRegistry.ZEKKENS_NOTES_BOOM, context.getStack()) == 0) {
            return shockwave(context.getWorld(), context.getPlayer(), context.getHitPos());
        }

        return ActionResult.PASS;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(1, Text.translatable("item.grimoire.zekkens_voice.desc"));
    }

    private ActionResult shockwave(World world, PlayerEntity player, Vec3d hitPos) {
        Vec3d center = player.getPos();

        if(player.getItemCooldownManager().isCoolingDown(this)) return ActionResult.PASS;

        Vec3d pos = player.getPos();
        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundRegistry.ZEKKENS_SHOCKWAVE, SoundCategory.PLAYERS, 1f, 0.65f);
        if(world.isClient) {
            Random rand = world.getRandom();
            for(int i = 0;i<50;i++) {
                world.addParticle(ParticleTypes.CLOUD,
                        (double)hitPos.getX(),
                        (double)hitPos.getY(),
                        (double)hitPos.getZ(),
                        rand.nextBetweenExclusive(-5, 5)/10f,
                        rand.nextBetweenExclusive(0, 5)/15f,
                        rand.nextBetweenExclusive(-5, 5)/10f);
            }
            return ActionResult.SUCCESS;
        }

        double radius = 20.0;
        for(LivingEntity entity : world.getEntitiesByClass(LivingEntity.class, Box.of(center, radius, radius, radius), (entity) -> {return true;})) {
            if(entity.equals(player)) continue;

            double distance = entity.getPos().distanceTo(player.getPos()) / 10f;

            entity.takeKnockback(1f - distance, center.x - entity.getX(), center.z - entity.getZ());
            entity.addVelocity(0, 0.2d - (distance/10d), 0);
            entity.damage(DamageSource.MAGIC, 2f);
        }

        player.getItemCooldownManager().set(this, 80);
        return ActionResult.SUCCESS;
    }

    private void notesBoom(World world, PlayerEntity user) {
        double maxRange = 15;
        double rayRadius = 1.75; // Thickness of the ray

        Vec3d start = user.getEyePos();
        Vec3d direction = user.getRotationVec(1.0F).normalize();
        Vec3d end = start.add(direction.multiply(maxRange));

        BlockHitResult hitResult = world.raycast(new RaycastContext(
                start, // Starting position
                end,   // Ending position
                RaycastContext.ShapeType.COLLIDER, // What to check for (e.g., COLLIDER for solid blocks)
                RaycastContext.FluidHandling.NONE, // Whether to consider fluids
                user
        ));

        if(hitResult.getType().equals(HitResult.Type.BLOCK)) {
            maxRange = start.distanceTo(hitResult.getPos());
        }

        // Spawn particles along the ray
        for (int i = 0; i < maxRange; i++) {
            Vec3d point = start.add(direction.multiply(i));
            //((ServerWorld)world).spawnParticles(ParticleRegistry.NOTE_BOOM_PARTICLE, point.x, point.y, point.z, 1, 0.0, 0.0, 0.0, 0);

            Random rand = world.getRandom();
            Vec3d randOffset = new Vec3d(
                    rand.nextBetween(-5, 5) / 10d,
                    rand.nextBetween(-5, 5) / 10d,
                    rand.nextBetween(-5, 5) / 10d
            );

            ((ServerWorld)world).spawnParticles(ParticleTypes.NOTE, point.x + randOffset.getX(), point.y + randOffset.getY(), point.z + randOffset.getZ(), 1, 0, 0, 0, 0);
        }

        // Handle entities in a broad bounding box around the ray
        List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class,
                user.getBoundingBox().stretch(direction.multiply(maxRange)).expand(rayRadius),
                e -> e != user);

        for (LivingEntity entity : entities) {
            Vec3d entityPos = entity.getBoundingBox().getCenter();
            Vec3d toEntity = entityPos.subtract(start).normalize();

            // Check if the entity is in front of the player
            if (direction.dotProduct(toEntity) <= 0) continue;

            // Calculate the closest point on the ray to the entity
            Vec3d closestPoint = start.add(direction.multiply(entityPos.subtract(start).dotProduct(direction)));

            // Check if the entity is within the ray's radius
            double distance = entityPos.distanceTo(closestPoint);
            if (distance <= rayRadius && closestPoint.distanceTo(start) <= maxRange) {
                // Apply damage and effects
                entity.damage(DamageSource.MAGIC, 4.5f);

                // Knockback effect
                double knockbackResistance = ((entity.getPos().distanceTo(user.getPos()))/10f) + entity.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE) + 1;
                Vec3d knockback = direction.multiply(1.7);
                entity.addVelocity(knockback.x / knockbackResistance, 1 / knockbackResistance, knockback.z / knockbackResistance);
            }
        }

        ((ServerWorld)world).playSoundFromEntity(null, user, SoundEvents.ENTITY_WARDEN_SONIC_BOOM, SoundCategory.PLAYERS, 2, 0.8f, 0);

        // Cooldown (optional)
        user.getItemCooldownManager().set(this, 80); // 1-second cooldown
    }
}