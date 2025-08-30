package nazario.grimoire.block.custom.grand_door.client;

import nazario.grimoire.GrimoireMain;
import nazario.grimoire.block.custom.grand_door.GrandDoorBlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class GrandDoorRenderer extends GeoBlockRenderer<GrandDoorBlockEntity> {
    public GrandDoorRenderer(BlockEntityRendererFactory.Context context) {
        super(new Model());
    }

    public static class Model extends GeoModel<GrandDoorBlockEntity> {
        @Override
        public Identifier getModelResource(GrandDoorBlockEntity grandDoorBlockEntity) {
            return GrimoireMain.id("geo/block/grand_door.geo.json");
        }

        @Override
        public Identifier getTextureResource(GrandDoorBlockEntity grandDoorBlockEntity) {
            return GrimoireMain.id("textures/block/grand_door.png");
        }

        @Override
        public Identifier getAnimationResource(GrandDoorBlockEntity grandDoorBlockEntity) {
            return GrimoireMain.id("animations/block/grand_door.animation.json");
        }
    }
}
