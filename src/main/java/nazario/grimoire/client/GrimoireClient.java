package nazario.grimoire.client;

import nazario.grimoire.GrimoireMain;
import nazario.grimoire.block.ModBlocks;
import nazario.grimoire.block.ModFluids;
import nazario.grimoire.block.custom.grand_door.client.GrandDoorRenderer;
import nazario.grimoire.client.renderer.GrimoireTooltipRenderer;
import nazario.grimoire.entity.ModEntities;
import nazario.grimoire.item.CustomTooltipBackground;
import nazario.grimoire.item.ModItems;
import nazario.liby.api.assetgen.v1.client.LibyAssetRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.registry.Registries;

public class GrimoireClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModEntities.registerClient();

        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.VANISHED, ModFluids.FLOWING_VANISHED, new SimpleFluidRenderHandler(
                GrimoireMain.id("block/vanished_fluid_still"),
                GrimoireMain.id("block/vanished_fluid_flow"),
                0x525252
        ));

        BlockEntityRendererFactories.register(ModBlocks.GRAND_DOOR_TYPE, GrandDoorRenderer::new);

        LibyAssetRegistry ASSET_REGISTRY = LibyAssetRegistry.of(GrimoireMain.MOD_ID);

        ASSET_REGISTRY.registerItemPredicateModel(ModItems.GRIMOIRE, new ModelIdentifier(GrimoireMain.id("grimoire_in_hand"), "inventory"), (mode, stack, leftHand) ->
            switch(mode) {
                case GUI, GROUND, FIXED -> false;
                default -> true;
            }
        );

        Registries.ITEM.forEach(item -> {
            if(item instanceof CustomTooltipBackground) ASSET_REGISTRY.registerTooltipRenderer(item, GrimoireTooltipRenderer::new);
        });
    }
}
