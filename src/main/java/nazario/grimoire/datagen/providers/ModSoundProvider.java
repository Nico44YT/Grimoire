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

        registerSound(ModSounds.ENTITY_SOULMOULD_AMBIENT, null, false, LibySoundData.create("grimoire:soulmould/soulmould_ambient_1"), LibySoundData.create("grimoire:soulmould/soulmould_ambient_2"));
        registerSound(ModSounds.ENTITY_SOULMOULD_ATTACK, null, false, LibySoundData.create("grimoire:soulmould/soulmould_attack_1"), LibySoundData.create("grimoire:soulmould/soulmould_attack_2"), LibySoundData.create("grimoire:soulmould/soulmould_attack_3"));
        registerSound(ModSounds.ENTITY_SOULMOULD_DEATH, null, false, LibySoundData.create("grimoire:soulmould/soulmould_death_1"));
        registerSound(ModSounds.ENTITY_SOULMOULD_HURT, null, false, LibySoundData.create("grimoire:soulmould/soulmould_damage_1"));
    }
}
