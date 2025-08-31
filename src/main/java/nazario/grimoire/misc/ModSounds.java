package nazario.grimoire.misc;

import nazario.grimoire.GrimoireMain;
import nazario.liby.api.registry.helper.LibySoundRegistry;
import net.minecraft.sound.SoundEvent;

public class ModSounds {
    private static final LibySoundRegistry REGISTRY = LibySoundRegistry.of(GrimoireMain.MOD_ID);

    public static final SoundEvent GRIMOIRE_SHATTER = REGISTRY.registerSoundEvent("item.grimoire.shatter");
    public static final SoundEvent GRIMOIRE_BANISHMENT = REGISTRY.registerSoundEvent("item.grimoire.banishment");
    public static final SoundEvent GRIMOIRE_CAPTURE = REGISTRY.registerSoundEvent("item.grimoire.capture");
    public static final SoundEvent GRIMOIRE_START = REGISTRY.registerSoundEvent("item.grimoire.start");

    public static final SoundEvent HEARTBEAT = REGISTRY.registerSoundEvent("misc.grimoire.heartbeat");

    public static final SoundEvent EVENT_BANISHMENT = REGISTRY.registerSoundEvent("event.grimoire.banishment");
    public static final SoundEvent EVENT_SOUL_MUSIC = REGISTRY.registerSoundEvent("event.grimoire.soul_music");

    public static final SoundEvent OATHBREAKER_FIRE = REGISTRY.registerSoundEvent("item.grimoire.oathbreaker.fire");
    public static final SoundEvent OATHBREAKER_EXPLOSION = REGISTRY.registerSoundEvent("item.grimoire.oathbreaker.explosion", 64);

    public static final SoundEvent ENTITY_SOULMOULD_ATTACK = REGISTRY.registerSoundEvent("entity.grimoire.avenging_soulmould.attack");
    public static final SoundEvent ENTITY_SOULMOULD_HURT = REGISTRY.registerSoundEvent("entity.grimoire.avenging_soulmould.hurt");
    public static final SoundEvent ENTITY_SOULMOULD_DEATH = REGISTRY.registerSoundEvent("entity.grimoire.avenging_soulmould.death");
    public static final SoundEvent ENTITY_SOULMOULD_AMBIENT = REGISTRY.registerSoundEvent("entity.grimoire.avenging_soulmould.ambient") ;


    public static void register() {

    }
}
