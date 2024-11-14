package nazario.grimoire.mixin;

import nazario.grimoire.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
}
