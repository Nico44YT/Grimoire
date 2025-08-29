package nazario.grimoire.client.renderer;

import nazario.grimoire.components.PlayerRenderingComponent;
import nazario.grimoire.item.ModItems;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class OathbreakerPlayerFeatureRenderer<T extends PlayerEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {

    private final HeldItemRenderer heldItemRenderer;

    public OathbreakerPlayerFeatureRenderer(FeatureRendererContext<T, M> context, HeldItemRenderer heldItemRenderer) {
        super(context);
        this.heldItemRenderer = heldItemRenderer;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light,
                       T entity, float limbAngle, float limbDistance, float tickDelta,
                       float animationProgress, float headYaw, float headPitch) {

        PlayerRenderingComponent.KEY.maybeGet(entity).ifPresent(comp -> {
            if(comp.shouldOathbreakerFeatureRender()) {
                Arm mainArm = entity.getMainArm();

                matrices.push();

                // Smoothly interpolate yaw and pitch
                float bodyYaw = MathHelper.lerp(tickDelta, entity.prevBodyYaw, entity.bodyYaw);
                float headYawInterp = MathHelper.lerp(tickDelta, entity.prevHeadYaw, entity.headYaw);
                float pitchInterp = MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch());

                // If you want relative head movement only:
                float yawOffset = headYawInterp - bodyYaw;

                // Position the item
                switch(mainArm) {
                    case LEFT -> matrices.translate(0.6, -0.1, -0.2);
                    case RIGHT -> matrices.translate(-0.6, -0.1, -0.2);
                }

                // Apply pitch (look up/down)
                matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(180 - 10 - pitchInterp));

                // Apply yaw (turn left/right, relative to body)
                switch(mainArm) {
                    case LEFT -> matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180 - 10 - yawOffset));
                    case RIGHT -> matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180 + 10 - yawOffset));
                }

                // Render the item
                this.heldItemRenderer.renderItem(entity, ModItems.OATHBREAKER.getDefaultStack(),
                        ModelTransformationMode.FIXED, false, matrices, vertexConsumers, light);

                matrices.pop();
            }
        });
    }

}
