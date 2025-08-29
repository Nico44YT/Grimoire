package nazario.grimoire.block;

import nazario.grimoire.GrimoireMain;
import nazario.grimoire.block.custom.vanished_fluid.VanishedFluid;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModFluids {
    public static final FlowableFluid FLOWING_VANISHED = (FlowableFluid)register("flowing_vanished_fluid", new VanishedFluid.Flowing());
    public static final FlowableFluid VANISHED = (FlowableFluid)register("vanished_fluid", new VanishedFluid.Still());

    public static void register() {

    }

    private static <T extends Fluid> T register(String name, T value) {
        return Registry.register(Registries.FLUID, GrimoireMain.id(name), value);
    }
}
