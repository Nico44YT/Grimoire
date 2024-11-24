package nazario.grimoire.mixin;

import com.mojang.serialization.DataResult;
import nazario.grimoire.common.enchantments.EffectStealingEnchantment;
import nazario.grimoire.common.entity.remains.PlayerRemainsEntity;
import nazario.grimoire.common.item.TwoHanded;
import nazario.grimoire.registry.BlockRegistry;
import nazario.grimoire.registry.EnchantmentRegistry;
import nazario.grimoire.registry.EntityTypeRegistry;
import nazario.grimoire.registry.GrimGameruleRegistry;
import nazario.grimoire.util.TrinketsHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow protected abstract void consumeItem();

    @Inject(method = "getOffHandStack", at = @At("HEAD"), cancellable = true)
    public void grimoire$getOffHandStack(CallbackInfoReturnable<ItemStack> cir) {
        if((LivingEntity)(Object)this instanceof PlayerEntity player) {
            if(player.getMainHandStack().getItem() instanceof TwoHanded) cir.setReturnValue(new ItemStack(Items.AIR));
        }
    }

    @Inject(method = "applyClimbingSpeed", at = @At("TAIL"), cancellable = true)
    private void grimoire$applyClimbingSpeed(Vec3d motion, CallbackInfoReturnable<Vec3d> cir) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if(entity.isClimbing()) {
            entity.onLanding();
            float f = 0.15f;
            double d = MathHelper.clamp(motion.x, (double)-0.15f, (double)0.15f);
            double e = MathHelper.clamp(motion.z, (double)-0.15f, (double)0.15f);
            double g = Math.max(motion.y, (double)-0.15f);
            if (g < 0.0 && !entity.getBlockStateAtPos().isOf(BlockRegistry.IRON_SCAFFOLDING) && entity.isHoldingOntoLadder() && entity instanceof PlayerEntity) {
                g = 0.0;
            }
            motion = new Vec3d(d, g, e);
        }

        cir.setReturnValue(motion);
    }

    @Inject(method = "tryUseTotem", at = @At(value = "HEAD"), cancellable = true)
    private void grimoire$tryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        try{
            ItemStack stack = source.getAttacker().getHandItems().iterator().next();
            if(stack.getItem() instanceof AxeItem) {
                if(EnchantmentHelper.getLevel(EnchantmentRegistry.DEATHBOUND_ENCHANTMENT, stack) > 0) {
                    Random rand = source.getAttacker().getWorld().getRandom();

                    if(rand.nextBetween(0, 3) == 0) cir.setReturnValue(false);
                }
            }
        }catch (Exception ignore) {}
    }


    @Inject(method = "damage", at = @At(value = "HEAD"))
    private void grimoire$damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        try{
                ItemStack attackerMainhandStack = source.getAttacker().getHandItems().iterator().next();
                if(attackerMainhandStack.getItem() instanceof SwordItem) {
                    if(EnchantmentHelper.getLevel(EnchantmentRegistry.SHARED_BLOOD_ENCHANTMENT, attackerMainhandStack) > 0) {
                        EffectStealingEnchantment.logic(source, (LivingEntity)(Object)this);
                    }
                } else if(attackerMainhandStack.getItem() instanceof AxeItem){
                    if (EnchantmentHelper.getLevel(EnchantmentRegistry.BRUTE_FORCE_ENCHANTMENT, attackerMainhandStack) > 0) {
                        LivingEntity victim = (LivingEntity)(Object)this;
                        ItemStack victimMainhandStack = victim.getStackInHand(Hand.MAIN_HAND);

                        if (source.getAttacker() instanceof PlayerEntity sourcePlayer) {
                            if (victim instanceof PlayerEntity victimPlayer) {
                                boolean strongAttack = sourcePlayer.getAttackCooldownProgress(0.5f) > 0.9f;
                                boolean isCriticalAttack = sourcePlayer.fallDistance > 0.0f
                                        && !sourcePlayer.isOnGround()
                                        && !sourcePlayer.isClimbing()
                                        && !sourcePlayer.isTouchingWater()
                                        && !sourcePlayer.hasStatusEffect(StatusEffects.BLINDNESS)
                                        && !sourcePlayer.hasVehicle()
                                        && !sourcePlayer.isSprinting();

                                if (isCriticalAttack && !victimMainhandStack.isEmpty()) {
                                    victimPlayer.getItemCooldownManager().set(victimMainhandStack.getItem(), 70);
                                }
                            }
                        }
                    }

                }
        } catch (Exception ignore) {
        }
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;onDeath(Lnet/minecraft/entity/damage/DamageSource;)V"))
    private void grimoire$onDeath(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(((LivingEntity)(Object)this).getWorld().getGameRules().getBoolean(GrimGameruleRegistry.CREATE_GRAVE) && (Object)this instanceof PlayerEntity player) {
            if(player.getWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) return;

            PlayerRemainsEntity playerRemainsEntity = new PlayerRemainsEntity(EntityTypeRegistry.PLAYER_REMAINS_TYPE, player.world);
            playerRemainsEntity.setPosition(player.getPos());

            playerRemainsEntity.resetInventory();

            for(int i = 0;i<player.getInventory().main.size();i++) {
                playerRemainsEntity.addInventoryStack(player.getInventory().main.get(i).copy());
            }
            for(int i = 0;i<player.getInventory().offHand.size();i++) {
                playerRemainsEntity.addInventoryStack(player.getInventory().offHand.get(i).copy());
            }

            for(int i = 0;i<player.getInventory().armor.size();i++) {
                playerRemainsEntity.addInventoryStack(player.getInventory().armor.get(i).copy());
            }

            if(((LivingEntity)(Object)this).getWorld().getGameRules().getBoolean(GrimGameruleRegistry.SAVE_TRINKETS)) {
                if(FabricLoader.getInstance().isModLoaded("trinkets")) {
                    try{
                        TrinketsHelper.findAllEquippedBy(player).forEach(playerRemainsEntity::addInventoryStack);
                        TrinketsHelper.clearAllEquippedTrinkets(player);
                    } catch (Exception ingore) {}
                }
            }

            playerRemainsEntity.setCustomName(player.getDisplayName());
            playerRemainsEntity.setCustomNameVisible(true);

            player.world.spawnEntity(playerRemainsEntity);
            player.getInventory().clear();
        }
    }

    @Inject(method = "onDeath", at = @At("TAIL"))
    public void grimoire$deathCompass(DamageSource damageSource, CallbackInfo ci) {
        if((Object)this instanceof PlayerEntity player) {
            ItemStack stack = new ItemStack(Items.COMPASS);
            NbtCompound nbt = new NbtCompound();
            stack.writeNbt(writeCompassNbt(nbt, player.getBlockPos()));
            player.giveItemStack(stack);
        }
    }

    private NbtCompound writeCompassNbt(NbtCompound nbt, BlockPos pos) {
        nbt.put("LodestonePos", NbtHelper.fromBlockPos(pos));
        nbt.putBoolean("LodestoneTracked", true);
        return nbt;
    }
}
