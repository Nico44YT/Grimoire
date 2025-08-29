package nazario.grimoire.misc;

import nazario.grimoire.GrimoireMain;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModTags {
    public static class FluidTags {
        public static final TagKey<Fluid> VANISHED = TagKey.of(RegistryKeys.FLUID, GrimoireMain.id("vanished_fluid"));
    }
}
