package nazario.grimoire.registry;

import nazario.grimoire.Grimoire;
import nazario.grimoire.common.particles.NoteBoomParticle;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.SonicBoomParticle;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.util.registry.Registry;

public class ParticleRegistry {
    public static final DefaultParticleType SPEAR_PIERCE = FabricParticleTypes.simple();
    public static final DefaultParticleType NOTE_BOOM_PARTICLE = FabricParticleTypes.simple(true);

    public static void register() {
        Registry.register(Registry.PARTICLE_TYPE, Grimoire.id("spear_pierce"), SPEAR_PIERCE);
        Registry.register(Registry.PARTICLE_TYPE, Grimoire.id("note_boom"), NOTE_BOOM_PARTICLE);
    }
}
