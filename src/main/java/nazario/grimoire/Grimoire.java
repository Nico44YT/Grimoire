package nazario.grimoire;

import nazario.grimoire.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.util.Identifier;

public class Grimoire implements ModInitializer {

    public static final String MOD_ID = "grimoire";

    @Override
    public void onInitialize() {
        EnchantmentRegistry.register();

        BlockRegistry.register();
        ItemRegistry.register();

        ParticleRegistry.register();

        SoundRegistry.register();
        EntityTypeRegistry.register();

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            PersistentData.init(PersistentData.getServerState(server));
        });
    }

    public static Identifier id(String name) {
        return Identifier.of(MOD_ID, name);
    }
}
