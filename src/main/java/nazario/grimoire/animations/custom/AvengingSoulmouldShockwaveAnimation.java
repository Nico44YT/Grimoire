package nazario.grimoire.animations.custom;

import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import nazario.grimoire.GrimoireMain;
import nazario.grimoire.entity.avenging_soulmould.AvengingSoulmouldEntity;
import nazario.liby.api.animation.v2.LibyAnimation;
import nazario.liby.api.animation.v2.LibyEntityAnimation;
import nazario.liby.api.util.LibyIdentifier;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class AvengingSoulmouldShockwaveAnimation extends LibyEntityAnimation<AvengingSoulmouldEntity> {

    public static final ParticleEmitterInfo AAA_ANIMATION = new ParticleEmitterInfo(GrimoireMain.id("avenging_shock"));
    private ParticleEmitterInfo playingAnim;

    public AvengingSoulmouldShockwaveAnimation(LibyIdentifier identifier, Predicate<AvengingSoulmouldEntity> predicate) {
        super(identifier, predicate);
    }

    @Override
    public long getMaxDuration() {
        return 27;
    }

    @Override
    public void onStart(World world, AvengingSoulmouldEntity avengingSoulmouldEntity) {

    }

    @Override
    public void onPause(World world, AvengingSoulmouldEntity avengingSoulmouldEntity) {

    }

    @Override
    public void onResume(World world, AvengingSoulmouldEntity avengingSoulmouldEntity) {

    }

    @Override
    public void onStop(World world, AvengingSoulmouldEntity entity) {

        playingAnim = AAA_ANIMATION.clone().position(entity.getPos().add(0, -1, 0));

        AAALevel.addParticle(world,128, playingAnim);

        double radius = 16.0;
        double strength = 2.5;
        Box box = new Box(entity.getPos().getX() - radius, entity.getPos().getY(), entity.getPos().getZ() - radius, entity.getPos().getX() + radius, entity.getPos().getY() + radius, entity.getPos().getZ() + radius);
        for(LivingEntity livingEntity : world.getEntitiesByClass(LivingEntity.class, box, Entity::isLiving)) {
            double dx = livingEntity.getX() - entity.getX();
            double dz = livingEntity.getZ() - entity.getZ();
            double distance = -Math.sqrt(dx * dx + dz * dz);

            if(livingEntity.getUuid().equals(entity.getOwnerUuid())) continue;

            if(livingEntity instanceof TameableEntity tameableEntity && tameableEntity.getOwnerUuid() != null &&
               tameableEntity.getOwnerUuid().equals(entity.getOwnerUuid())
            ) continue;

            if (distance != 0) {
                livingEntity.takeKnockback(strength, dx / distance, dz / distance);

                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 15, 1));
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 20 * 10, 1));
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 20 * 1, 1));
            }
            livingEntity.damage(entity.getDamageSources().magic(), 10.0f);
        }
    }

    @Override
    public void onRestart(World world, AvengingSoulmouldEntity avengingSoulmouldEntity, LibyAnimation<AvengingSoulmouldEntity> libyAnimation) {

    }

    @Override
    public void onTick(World world, AvengingSoulmouldEntity avengingSoulmouldEntity) {

    }

    @Override
    public void render(AvengingSoulmouldEntity avengingSoulmouldEntity, float v, float v1, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

    }

    @Override
    public <U extends LivingEntity> boolean canEntityTakeDamage(U entity, DamageSource source, float amount) {
        entity.setVelocity(0, 0, 0);
        entity.velocityDirty = true;
        return false;
    }
}
