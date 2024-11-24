package nazario.grimoire.mixin.client;

import nazario.grimoire.common.item.AngelicSpearItem;
import nazario.grimoire.common.item.ZekkensVoiceItem;
import nazario.grimoire.registry.ItemRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> extends AnimalModel<T>  {

    @Shadow @Final public ModelPart rightArm;

    @Shadow @Final public ModelPart leftArm;

    @Shadow @Final public ModelPart head;

    @Shadow public float leaningPitch;

    @Inject(method = "animateArms", at = @At("HEAD"), cancellable = true)
    protected void animateArms(T entity, float animationProgress, CallbackInfo ci) {
        if(entity.getMainHandStack().getItem().equals(ItemRegistry.ZEKKENS_VOICE)) ci.cancel();
    }

    @Inject(method = "positionRightArm", at = @At("HEAD"), cancellable = true)
    private void grimoire$positionRightArm(T entity, CallbackInfo ci) {
        if(entity.getMainHandStack().getItem() instanceof AngelicSpearItem) {
            grimoire$holdAngelicSpear(rightArm, leftArm, head, true);
            ci.cancel();
        } else if(entity.getMainHandStack().getItem() instanceof ZekkensVoiceItem) {
            ci.cancel();
        }
    }

    @Inject(method = "positionLeftArm", at = @At("HEAD"), cancellable = true)
    private void grimoire$positionLeftArm(T entity, CallbackInfo ci) {
        if(entity.getMainHandStack().getItem() instanceof AngelicSpearItem) {
            grimoire$holdAngelicSpear(rightArm, leftArm, head, false);
            ci.cancel();
        } else if(entity.getMainHandStack().getItem() instanceof ZekkensVoiceItem) {
            ci.cancel();
        }
    }

    // Yaw left and right
    // Pitch up and down

    private static void grimoire$holdAngelicSpear(ModelPart holdingArm, ModelPart otherArm, ModelPart head, boolean rightArmed) {
        ModelPart modelPart = rightArmed ? holdingArm : otherArm;
        ModelPart modelPart2 = rightArmed ? otherArm : holdingArm;
        modelPart.yaw = (rightArmed ? -0.4f : 0.4f) + head.yaw*0.5f;
        modelPart2.yaw = (rightArmed ? 0.7f : -0.7f) + head.yaw*0.5f;
        modelPart.pitch = -1.3f + head.pitch*0.5f;  // Previously -1.5707964f
        modelPart2.pitch = -1.3f + head.pitch*0.5f;        // Previously -1.5f
    }


    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("TAIL"), cancellable = true)
    private void disableArmSway(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if(livingEntity instanceof PlayerEntity player) {
            if(player.getMainHandStack().getItem().equals(ItemRegistry.ZEKKENS_VOICE)) {
                rightArm.pitch = -(float)Math.toRadians(19.5108d); // X
                rightArm.yaw = -(float)Math.toRadians(3.841d); // Y
                rightArm.roll = (float)Math.toRadians(-14.5108d); // Z

                leftArm.pitch = -(float)Math.toRadians(2.5308d);
                leftArm.yaw = -(float)Math.toRadians(-0.1016d);
                leftArm.roll = (float)Math.toRadians(-115.0542d);
            }
        }
    }
}
