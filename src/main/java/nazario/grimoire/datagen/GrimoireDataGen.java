package nazario.grimoire.datagen;

import nazario.grimoire.datagen.providers.ModModelProvider;
import nazario.grimoire.datagen.providers.ModSoundProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class GrimoireDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModSoundProvider::new);
        pack.addProvider(ModModelProvider::new);
    }
}
