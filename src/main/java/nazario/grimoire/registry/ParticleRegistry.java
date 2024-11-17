package nazario.grimoire.registry;

import nazario.grimoire.Grimoire;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.registry.Registry;

public class ParticleRegistry {
    public static final DefaultParticleType SPEAR_PIERCE = FabricParticleTypes.simple();

    public static void register() {
        Registry.register(Registry.PARTICLE_TYPE, Grimoire.id("spear_pierce"), SPEAR_PIERCE);
    }
}
