package nazario.grimoire.mixin.client;

import nazario.grimoire.registry.ItemRegistry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemFeatureRenderer.class)
public abstract class HeldItemFeatureRendererMixin<T extends LivingEntity, M extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
    public HeldItemFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }


    //@Inject(method = "renderItem", at = @At("HEAD"), cancellable = true)
    //private void grimoire$twoHanded(LivingEntity entity, ItemStack stack, ModelTransformation.Mode transformationMode, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
    //    if (entity.getMainHandStack().isOf(ItemRegistry.ANGELIC_SPEAR) && entity.getMainArm() != arm) {
    //        matrices.scale(2, 2, 2);
    //    }
    //}
}
