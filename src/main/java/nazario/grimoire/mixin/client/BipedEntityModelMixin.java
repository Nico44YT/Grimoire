package nazario.grimoire.mixin.client;

import nazario.grimoire.registry.ItemRegistry;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> extends AnimalModel<T>  {

    @Shadow @Final public ModelPart rightArm;

    @Shadow @Final public ModelPart leftArm;

    @Shadow @Final public ModelPart head;

    @Inject(method = "animateArms", at = @At("HEAD"), cancellable = true)
    protected void animateArms(T entity, float animationProgress, CallbackInfo ci) {
        if(entity instanceof PlayerEntity player) {

        }
    }

    @Inject(method = "positionRightArm", at = @At("HEAD"), cancellable = true)
    private void grimoire$positionRightArm(T entity, CallbackInfo ci) {
        if(entity.isHolding(ItemRegistry.ANGELIC_SPEAR)) {
            grimoire$hold(rightArm, leftArm, head, true);
            ci.cancel();
        }
    }

    @Inject(method = "positionLeftArm", at = @At("HEAD"), cancellable = true)
    private void grimoire$positionLeftArm(T entity, CallbackInfo ci) {
        if(entity.isHolding(ItemRegistry.ANGELIC_SPEAR)) {
            grimoire$hold(rightArm, leftArm, head, false);
            ci.cancel();
        }
    }

    private static void grimoire$hold(ModelPart holdingArm, ModelPart otherArm, ModelPart head, boolean rightArmed) {
        ModelPart modelPart = rightArmed ? holdingArm : otherArm;
        ModelPart modelPart2 = rightArmed ? otherArm : holdingArm;
        modelPart.yaw = (rightArmed ? -0.4f : 0.4f) + head.yaw*0.5f;
        modelPart2.yaw = (rightArmed ? 0.7f : -0.7f) + head.yaw*0.5f;
        modelPart.pitch = -1.3f + head.pitch*0.5f;  // Previously -1.5707964f
        modelPart2.pitch = -1.3f + head.pitch*0.5f;        // Previously -1.5f
    }
}
