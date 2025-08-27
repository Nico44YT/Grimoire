package nazario.grimoire;

import nazario.grimoire.block.ModBlocks;
import nazario.grimoire.entity.ModEntities;
import nazario.grimoire.item.ModItems;
import nazario.grimoire.misc.ModDamageTypes;
import nazario.grimoire.misc.ModSounds;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class GrimoireMain implements ModInitializer {

    public static final String MOD_ID = "grimoire";

    @Override
    public void onInitialize() {
        ModSounds.register();
        ModDamageTypes.register();

        ModItems.register();
        ModBlocks.register();
        ModEntities.register();
    }

    public static Identifier id(String name) {
        return Identifier.of(MOD_ID, name);
    }
}
