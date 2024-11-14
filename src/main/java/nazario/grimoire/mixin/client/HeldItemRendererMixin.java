package nazario.grimoire.mixin.client;

import nazario.grimoire.registry.ItemRegistry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {

    @Inject(method = "getHandRenderType", at = @At("HEAD"), cancellable = true)
    private static void grimoire$getHandRenderType(ClientPlayerEntity player, CallbackInfoReturnable<HeldItemRenderer.HandRenderType> cir) {
        //ItemStack grimoire$itemStack = player.getMainHandStack();
        //ItemStack grimoire$itemStack2 = player.getOffHandStack();
//
        //boolean bl = grimoire$itemStack.isOf(ItemRegistry.ANGELIC_SPEAR) || grimoire$itemStack2.isOf(ItemRegistry.ANGELIC_SPEAR);
        //if(bl) cir.setReturnValue(HeldItemRenderer.HandRenderType.RENDER_BOTH_HANDS);
    }
}
