package nazario.grimoire.entity.oathbreaker_projectile;

import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import nazario.grimoire.GrimoireMain;
import nazario.grimoire.entity.ModEntities;
import nazario.grimoire.misc.ModDamageTypes;
import nazario.grimoire.misc.ModSounds;
import nazario.grimoire.misc.ModVfxEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class OathbreakerProjectileEntity extends PersistentProjectileEntity {
    private static final ParticleEmitterInfo PARTICLE_VFX = new ParticleEmitterInfo(GrimoireMain.id("oathbreaker_projectile"));
    private ParticleEmitterInfo playingParticleVfx;

    public OathbreakerProjectileEntity(EntityType<? extends PersistentProjectileEntity> type, World world) {
        super(type, world);
    }

    protected OathbreakerProjectileEntity(EntityType<? extends PersistentProjectileEntity> type, double x, double y, double z, World world) {
        this(type, world);
        this.setPosition(x, y, z);
    };

    protected OathbreakerProjectileEntity(EntityType<? extends PersistentProjectileEntity> type, LivingEntity owner, World world) {
        this(type, owner.getX(), owner.getEyeY() - 0.1F, owner.getZ(), world);
        this.setOwner(owner);
        if (owner instanceof PlayerEntity) {
            this.pickupType = PickupPermission.ALLOWED;
        }
    }

    public static OathbreakerProjectileEntity create(World world, LivingEntity owner) {
        OathbreakerProjectileEntity entity = new OathbreakerProjectileEntity(ModEntities.OATHBREAKER_PROJECTILE_TYPE, owner, world);
        entity.setOwner(owner);
        if (owner instanceof PlayerEntity) {
            entity.pickupType = PickupPermission.DISALLOWED;
        }

        entity.setVelocity(owner, owner.getPitch(), owner.getYaw(), 0.0F, 6f, 0.0F);

        return entity;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if(this.age > 10 || (this.getOwner() != null && this.getOwner().getPos().distanceTo(this.getPos()) > 16*4)) {
            this.onHit(null);
        }

        if(this.getWorld().isClient()) return;

        if(playingParticleVfx == null) {
            playingParticleVfx = PARTICLE_VFX.clone();
            playingParticleVfx.bindOnEntity(this);
            playingParticleVfx.rotation((float)Math.toRadians(-this.getPitch()), (float)Math.toRadians(this.getYaw()), 0);
            AAALevel.addParticle(this.getWorld(), 1024, playingParticleVfx);
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        this.onHit(null);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if(entityHitResult.getEntity() instanceof LivingEntity livingEntity) this.onHit(livingEntity);
    }

    @Override
    protected void onHit(LivingEntity target) {

        if(target != null) {
            target.damage(ModDamageTypes.playerOathbreakerDirect(target), 7.5f + (target.getArmor() * 0.35f));
        }

        double radius = 8.0;
        double strength = 2.5;
        Box box = new Box(this.getPos().getX() - radius, this.getPos().getY() - (radius/2), this.getPos().getZ() - radius, this.getPos().getX() + radius, this.getPos().getY() + radius, this.getPos().getZ() + radius);
        for(LivingEntity livingEntity : this.getWorld().getEntitiesByClass(LivingEntity.class, box, Entity::isLiving)) {

            if(livingEntity.equals(this.getOwner())) continue;
            if(livingEntity.equals(target)) continue;
            if(livingEntity instanceof TameableEntity tameableEntity && tameableEntity.getOwnerUuid() != null && tameableEntity.getOwnerUuid().equals(this.getOwner().getUuid())) continue;

            double dx = livingEntity.getX() - this.getX();
            double dz = livingEntity.getZ() - this.getZ();
            double distance = -Math.sqrt(dx * dx + dz * dz);
            if (distance != 0) {
                livingEntity.takeKnockback(strength, dx / distance, dz / distance);
            }
            livingEntity.damage(ModDamageTypes.playerOathbreaker(livingEntity), 4.5f + (livingEntity.getArmor() * 0.25f));
        }

        this.getWorld().playSound(null, this.getBlockPos(), ModSounds.OATHBREAKER_EXPLOSION, SoundCategory.PLAYERS, 1, 1);
        AAALevel.addParticle(this.getWorld(), 1024, new ParticleEmitterInfo(ModVfxEffects.SHOCKWAVE).position(this.getSteppingPos().toCenterPos().add(0, -0.5, 0)).scale(1/16f));

        this.discard();
    }

    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }
}
