package nazario.grimoire.mixin.client;

import net.minecraft.client.particle.NoteParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoteParticle.class)
public class NoteParticleMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void modifyConstant(ClientWorld world, double x, double y, double z, double d, CallbackInfo ci) {
        ((NoteParticle)(Object)this).velocityX = 0f;
        ((NoteParticle)(Object)this).velocityY = 0.2f;
        ((NoteParticle)(Object)this).velocityZ = 0f;
    }
}

@Mixin(NoteParticle.Factory.class)
class NoteParticleFactoryMixin {

    @Inject(
            method = "createParticle(Lnet/minecraft/particle/DefaultParticleType;Lnet/minecraft/client/world/ClientWorld;DDDDDD)Lnet/minecraft/client/particle/Particle;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/NoteParticle;<init>(Lnet/minecraft/client/world/ClientWorld;DDDD)V", shift = At.Shift.AFTER),
            cancellable = true
    )
    private void modifyNoteParticle(
            DefaultParticleType defaultParticleType, ClientWorld clientWorld,
            double d, double e, double f, double g, double h, double i,
            CallbackInfoReturnable<Particle> cir
    ) {
        Particle particle = cir.getReturnValue(); // Get the particle returned from the method.
        if (particle instanceof NoteParticle noteParticle) { // Ensure it's the NoteParticle.
            System.out.println("VelX" + g);
            System.out.println("VelY" + h);
            System.out.println("VelZ" + i);
            noteParticle.velocityX = g; // Modify the velocity values.
            noteParticle.velocityY = h;
            noteParticle.velocityZ = i;
        }
    }


}
