package nazario.grimoire.item.custom.oathbreaker;

import nazario.grimoire.GrimoireMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

@Environment(EnvType.CLIENT)
public class OathbreakerSummonerRenderer extends GeoItemRenderer<OathbreakerSummoner> {
    public OathbreakerSummonerRenderer() {
        super(new Model());

        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    public static class Model extends GeoModel<OathbreakerSummoner> {

        @Override
        public Identifier getModelResource(OathbreakerSummoner grimoireBook) {
            return GrimoireMain.id("geo/item/oathbreaker_skull.geo.json");
        }

        @Override
        public Identifier getTextureResource(OathbreakerSummoner grimoireBook) {
            return GrimoireMain.id("textures/item/model/oathbreaker_skull.png");
        }

        @Override
        public Identifier getAnimationResource(OathbreakerSummoner grimoireBook) {
            return GrimoireMain.id("animations/item/oathbreaker.animation.json");
        }
    }
}
