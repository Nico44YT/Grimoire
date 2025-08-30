package nazario.grimoire.misc;

import nazario.grimoire.GrimoireMain;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

public class ModDimensions {
    public static final RegistryKey<World> AVIRITUM_DIMENSION_KEY = RegistryKey.of(RegistryKeys.WORLD, GrimoireMain.id("aviritum"));
}
