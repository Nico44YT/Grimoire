package nazario.grimoire.client;

import nazario.grimoire.GrimoireMain;
import nazario.grimoire.block.ModFluids;
import nazario.grimoire.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;

public class GrimoireClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModEntities.registerClient();

        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.VANISHED, ModFluids.FLOWING_VANISHED, new SimpleFluidRenderHandler(
                GrimoireMain.id("block/vanished_fluid_still"),
                GrimoireMain.id("block/vanished_fluid_flow"),
                0x525252
        ));
    }
}
