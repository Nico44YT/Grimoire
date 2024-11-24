package nazario.grimoire.registry;

import nazario.grimoire.Grimoire;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SoundRegistry {

    public static final SoundEvent ZEKKENS_SHOCKWAVE = registerSoundEvent("shockwave");

    public static void register() {}

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Grimoire.id(name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }
}
