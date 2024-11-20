package nazario.grimoire.client;

import nazario.grimoire.Grimoire;
import nazario.grimoire.common.entity.PlayerRemainsEntityModel;
import nazario.grimoire.common.entity.PlayerRemainsEntityRenderer;
import nazario.grimoire.common.particles.SpearPierceParticle;
import nazario.grimoire.registry.BlockRegistry;
import nazario.grimoire.registry.EntityTypeRegistry;
import nazario.grimoire.registry.ParticleRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.screen.PlayerScreenHandler;

public class GrimoireClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.MOSS_CARPET_VINES, RenderLayer.getCutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.AQUARIUM_GLASS_PANE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.IRON_SCAFFOLDING, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.ACACIA_CONNECTED_LOG_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BIRCH_CONNECTED_LOG_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.CRIMSON_CONNECTED_LOG_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.DARK_OAK_CONNECTED_LOG_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.JUNGLE_CONNECTED_LOG_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.MANGROVE_CONNECTED_LOG_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.OAK_CONNECTED_LOG_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.SPRUCE_CONNECTED_LOG_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.WARPED_CONNECTED_LOG_BLOCK, RenderLayer.getCutout());


        EntityModelLayerRegistry.registerModelLayer(EntityTypeRegistry.PLAYER_REMAINS_MODEL_LAYER, PlayerRemainsEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityTypeRegistry.PLAYER_REMAINS_TYPE, PlayerRemainsEntityRenderer::new);

        //ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((((spriteAtlasTexture, registry) -> {
        //    registry.register(Grimoire.id("particle/spear_pierce"));
        //})));

        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.SPEAR_PIERCE, SpearPierceParticle.Factory::new);
    }
}
