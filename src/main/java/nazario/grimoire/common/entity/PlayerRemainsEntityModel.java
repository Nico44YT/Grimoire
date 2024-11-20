package nazario.grimoire.common.entity;

import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class PlayerRemainsEntityModel<T extends PlayerRemainsEntity> extends EntityModel<PlayerRemainsEntity> {

    @Override
    public void setAngles(PlayerRemainsEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {

    }

    public static TexturedModelData getTexturedModelData() {
        return TexturedModelData.of(null, 8, 8);
    }
}
