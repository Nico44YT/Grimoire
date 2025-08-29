package nazario.grimoire;

import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import nazario.grimoire.block.ModBlocks;
import nazario.grimoire.block.ModFluids;
import nazario.grimoire.components.PlayerRenderingComponent;
import nazario.grimoire.entity.ModEntities;
import nazario.grimoire.item.ModItems;
import nazario.grimoire.misc.ModDamageTypes;
import nazario.grimoire.misc.ModSounds;
import net.fabricmc.api.ModInitializer;
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
    }

    public static Identifier id(String name) {
        return Identifier.of(MOD_ID, name);
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(PlayerEntity.class, PlayerRenderingComponent.KEY, PlayerRenderingComponent::new);
    }
}
