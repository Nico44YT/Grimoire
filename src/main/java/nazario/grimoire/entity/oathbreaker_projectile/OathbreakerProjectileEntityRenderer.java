package nazario.grimoire.entity.oathbreaker_projectile;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class OathbreakerProjectileEntityRenderer extends EntityRenderer<OathbreakerProjectileEntity> {
    public OathbreakerProjectileEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(OathbreakerProjectileEntity entity) {
        return null;
    }

    @Override
    public boolean shouldRender(OathbreakerProjectileEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }
}
