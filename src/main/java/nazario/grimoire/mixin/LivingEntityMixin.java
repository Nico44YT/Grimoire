package nazario.grimoire.mixin;

import nazario.grimoire.enchantments.EffectStealingEnchantment;
import nazario.grimoire.registry.BlockRegistry;
import nazario.grimoire.registry.EnchantmentRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

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
}
