package nazario.grimoire.entity.avenging_soulmould.client;

import nazario.grimoire.GrimoireMain;
import nazario.grimoire.entity.avenging_soulmould.AvengingSoulmouldEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class AvengingSoulmouldEntityRenderer extends GeoEntityRenderer<AvengingSoulmouldEntity> {
    public AvengingSoulmouldEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new Model());

        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public void render(AvengingSoulmouldEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.push();

        // Apply your scale transformation here
        float scale = 1.25f; // example: half-size
        poseStack.scale(scale, scale, scale);

        // Continue rendering normally
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

        poseStack.pop();
    }

    @Override
    public RenderLayer getRenderType(AvengingSoulmouldEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return RenderLayer.getEntityTranslucent(texture, true);
    }

    public static class Model extends GeoModel<AvengingSoulmouldEntity> {
        @Override
        public Identifier getModelResource(AvengingSoulmouldEntity object) {
            return new Identifier(GrimoireMain.MOD_ID, "geo/entity/avenging_soulmould.geo.json");
        }

        @Override
        public Identifier getTextureResource(AvengingSoulmouldEntity object) {
            return new Identifier(GrimoireMain.MOD_ID, "textures/entity/avenging_soulmould.png");
        }

        @Override
        public Identifier getAnimationResource(AvengingSoulmouldEntity animatable) {
            return new Identifier(GrimoireMain.MOD_ID, "animations/entity/avenging_soulmould.animation.json");
        }

    }

}
