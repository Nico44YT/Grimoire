package nazario.grimoire.item.custom.grimoire_book.client;

import nazario.grimoire.GrimoireMain;
import nazario.grimoire.item.custom.grimoire_book.GrimoireBookItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GrimoireBookItemRenderer extends GeoItemRenderer<GrimoireBookItem> {
    public GrimoireBookItemRenderer() {
        super(new Model());
    }

    public static class Model extends GeoModel<GrimoireBookItem> {

        @Override
        public Identifier getModelResource(GrimoireBookItem grimoireBook) {
            return GrimoireMain.id("geo/item/grimoire.geo.json");
        }

        @Override
        public Identifier getTextureResource(GrimoireBookItem grimoireBook) {
            return GrimoireMain.id("textures/item/model/grimoire.png");
        }

        @Override
        public Identifier getAnimationResource(GrimoireBookItem grimoireBook) {
            return null;
        }
    }
}