package nazario.grimoire.mixin;

import nazario.grimoire.registry.EnchantmentRegistry;
import nazario.grimoire.registry.ItemRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "getMovementSpeed", at = @At("HEAD"), cancellable = true)
    public void grimoire$getMovementSpeed(CallbackInfoReturnable<Float> cir) {
        PlayerEntity player = (PlayerEntity)(Object)this;

        player.getArmorItems().forEach(stack -> {
            if(EnchantmentHelper.getLevel(EnchantmentRegistry.GOLEM_SHROUD_ENCHANTMENT, stack) > 0) {
                cir.setReturnValue((float)player.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) / 2f);
            }
        });
    }

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    public void grimoire$attack(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;

        if(player.getMainHandStack().getItem().equals(ItemRegistry.ANGELIC_SPEAR)) {
            List<LivingEntity> list = player.world.getNonSpectatingEntities(LivingEntity.class, target.getBoundingBox().expand(0.5, 0.25, 0.5));
            for (LivingEntity livingEntity : list) {
                if (livingEntity == player || livingEntity == target || player.isTeammate(livingEntity) || livingEntity instanceof ArmorStandEntity && ((ArmorStandEntity)livingEntity).isMarker() || !(player.squaredDistanceTo(livingEntity) < 9.0)) continue;
                livingEntity.takeKnockback(0.4f, MathHelper.sin(player.getYaw() * ((float)Math.PI / 180)), -MathHelper.cos(player.getYaw() * ((float)Math.PI / 180)));
                livingEntity.damage(DamageSource.player(player), 3);
            }
            player.world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0f, 1.0f);
        }

        if(player.getMainHandStack().getItem().equals(ItemRegistry.ZEKKENS_VOICE)) {
            player.world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_NOTE_BLOCK_GUITAR, player.getSoundCategory(), 1.0f, 1.0f);
        }
    }


    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void grimoire$damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
         if((LivingEntity)(Object)this instanceof PlayerEntity playerEntity) {
            if(EnchantmentHelper.getLevel(EnchantmentRegistry.GOLEM_SHROUD_ENCHANTMENT, playerEntity.getEquippedStack(EquipmentSlot.CHEST)) > 0) {
                (playerEntity).addExhaustion(0.5f * (float)(6));
            }
        }
    }

    @Inject(method = "dropInventory", at = @At("HEAD"), cancellable = true)
    public void dropInventory(CallbackInfo ci) {
        ci.cancel();
    }
}
