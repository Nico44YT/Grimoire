package nazario.grimoire;

import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import nazario.grimoire.block.ModBlocks;
import nazario.grimoire.block.ModFluids;
import nazario.grimoire.command.GrimoireMainCommand;
import nazario.grimoire.command.GrimoirePocketDimensionCommand;
import nazario.grimoire.components.PlayerRenderingDataComponent;
import nazario.grimoire.components.PlayerRenderingPreferencesComponent;
import nazario.grimoire.data.PocketDimensionDataManager;
import nazario.grimoire.entity.ModEntities;
import nazario.grimoire.item.ModItems;
import nazario.grimoire.misc.ModDamageTypes;
import nazario.grimoire.misc.ModSounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class GrimoireMain implements ModInitializer, EntityComponentInitializer {

    public static final Identifier FONT = id("blackcraft");
    public static final String MOD_ID = "grimoire";

    @Override
    public void onInitialize() {
        ModSounds.register();
        ModDamageTypes.register();

        ModFluids.register();
        ModItems.register();
        ModBlocks.register();
        ModEntities.register();

        CommandRegistrationCallback.EVENT.register(GrimoireMainCommand::new);

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            PocketDimensionDataManager.get().readFromFile(server);
            PocketDimensionDataManager.get().applyToServer(server);
        });

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            PocketDimensionDataManager.get().saveToFile(server);
        });
    }

    public static Identifier id(String name) {
        return Identifier.of(MOD_ID, name);
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(PlayerEntity.class, PlayerRenderingDataComponent.KEY, PlayerRenderingDataComponent::new);
        registry.registerFor(PlayerEntity.class, PlayerRenderingPreferencesComponent.KEY, PlayerRenderingPreferencesComponent::new);
    }
}
