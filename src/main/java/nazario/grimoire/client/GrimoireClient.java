package nazario.grimoire.client;

import nazario.grimoire.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;

public class GrimoireClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModEntities.registerClient();
    }
}
