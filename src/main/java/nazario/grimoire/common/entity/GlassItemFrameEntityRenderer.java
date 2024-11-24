package nazario.grimoire.common.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import net.minecraft.client.util.ModelIdentifier;

public class GlassItemFrameEntityRenderer<T extends GlassItemFrameEntity> extends ItemFrameEntityRenderer<GlassItemFrameEntity> {


    //This is pain...
    public static final ModelIdentifier GLASS_NORMAL_FRAME = new ModelIdentifier("grimoire:block/glass_item_frame");

    public GlassItemFrameEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }
}
