package nazario.grimoire.mixin;

import nazario.grimoire.misc.ModTags;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public float fallDistance;

    @Inject(method = "updateWaterState", at = @At("RETURN"), cancellable = true)
    public void grimoire$updateFluidState(CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity)(Object)this;

        boolean bl = entity.updateMovementInFluid(ModTags.FluidTags.VANISHED, 0.002);
        cir.setReturnValue(cir.getReturnValueZ() || bl);
    }

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V"))
    public void grimoire$tick(CallbackInfo ci) {
        if(grimoire$isInVanished()) {
            this.fallDistance = 0F;
        }
    }

    public boolean grimoire$isInVanished() {
        Entity entity = (Entity)(Object)this;
        return !entity.firstUpdate && entity.fluidHeight.getDouble(ModTags.FluidTags.VANISHED) > 0.0;
    }
}
