package nazario.grimoire.datagen.providers;

import nazario.grimoire.GrimoireMain;
import nazario.grimoire.misc.ModSounds;
import nazario.liby.api.assetgen.v1.datagen.LibySoundProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModSoundProvider extends LibySoundProvider {
    public ModSoundProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(GrimoireMain.MOD_ID, output);

        registerSound(ModSounds.GRIMOIRE_SHATTER, null, false, LibySoundData.create("grimoire:grimoire_book/grimoire_shatter"));
        registerSound(ModSounds.GRIMOIRE_BANISHMENT, null, false, LibySoundData.create("grimoire:grimoire_book/grimoire_banishment"));
        registerSound(ModSounds.GRIMOIRE_CAPTURE, null, false, LibySoundData.create("grimoire:grimoire_book/grimoire_capture"));
        registerSound(ModSounds.GRIMOIRE_START, null, false, LibySoundData.create("grimoire:grimoire_book/grimoire_start"));

        registerSound(ModSounds.HEARTBEAT, null, false, LibySoundData.create("grimoire:misc/heartbeat"));

        registerSound(ModSounds.EVENT_BANISHMENT, null, false, LibySoundData.create("grimoire:new_banishment_reverb"));
        registerSound(ModSounds.EVENT_SOUL_MUSIC, null, false, LibySoundData.create("grimoire:soul_music"));

        registerSound(ModSounds.OATHBREAKER_FIRE, null, false, LibySoundData.create("grimoire:oathbreaker/fire"));
        registerSound(ModSounds.OATHBREAKER_EXPLOSION, null, false, LibySoundData.create("grimoire:oathbreaker/explosion"));
    }
}
