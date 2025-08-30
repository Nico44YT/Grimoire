package nazario.grimoire.animations.custom.banning;

import mod.chloeprime.aaaparticles.api.client.effekseer.ParticleEmitter;
import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import nazario.grimoire.GrimoireMain;
import nazario.grimoire.item.custom.grimoire_book.GrimoireBookItem;
import nazario.grimoire.misc.ModSounds;
import nazario.grimoire.misc.ModVfxEffects;
import nazario.liby.api.animation.v2.LibyAnimatable;
import nazario.liby.api.animation.v2.LibyAnimation;
import nazario.liby.api.animation.v2.LibyEntityAnimation;
import nazario.liby.api.util.LibyIdentifier;
import nazario.liby.api.util.nbt.LibyNbtCompound;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class LockingAnimation extends LibyEntityAnimation<LivingEntity> {

    private ParticleEmitterInfo playingVfx;
    private Identifier emitter;

    public LockingAnimation(LibyIdentifier identifier, Predicate<LivingEntity> predicate) {
        super(identifier, predicate);
    }

    @Override
    public long getMaxDuration() {
        return 20*60*10;
    }

    @Override
    public void onStart(World world, LivingEntity livingEntity) {
        world.playSound(
                null,
                livingEntity.getBlockPos(),
                ModSounds.GRIMOIRE_CAPTURE,
                SoundCategory.PLAYERS,
                1,
                1);

        emitter = GrimoireMain.id(livingEntity.getUuidAsString());

        syncAnimation(livingEntity);
    }

    @Override
    public void onPause(World world, LivingEntity livingEntity) {

    }

    @Override
    public void onResume(World world, LivingEntity livingEntity) {

    }

    @Override
    public void onStop(World world, LivingEntity livingEntity) {
        world.playSound(null, livingEntity.getBlockPos(), ModSounds.GRIMOIRE_SHATTER, SoundCategory.PLAYERS ,10, 1f);


        if(world instanceof ServerWorld serverWorld) {
            for (ServerPlayerEntity player : serverWorld.getPlayers()) {
                AAALevel.sendTriggerFor(player, ParticleEmitter.Type.WORLD, ModVfxEffects.LOCKING, emitter, new int[]{0});
            }

            syncAnimation(livingEntity);
        }
    }

    @Override
    public void onRestart(World world, LivingEntity livingEntity, LibyAnimation<LivingEntity> libyAnimation) {

    }

    @Override
    public void onTick(World world, LivingEntity livingEntity) {
        if(playingVfx == null) {
            playingVfx = ParticleEmitterInfo.create(livingEntity.getWorld(), ModVfxEffects.LOCKING, emitter).bindOnEntity(livingEntity);
            playingVfx.scale(2.5f*livingEntity.getWidth());
            playingVfx.entitySpaceRelativePosition(0, livingEntity.getHeight()/2f, 0);
            AAALevel.addParticle(livingEntity.getWorld(), playingVfx);
        }
    }

    @Override
    public void render(LivingEntity livingEntity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {

    }

    @Override
    public void readCustomData(LibyNbtCompound tag) {
        this.emitter = tag.getIdentifier("emitter");
    }


    @Override
    public void writeCustomData(LibyNbtCompound tag) {
        tag.putIdentifier("emitter", this.emitter);
    }

    @Override
    public boolean canEntityTakeDamage(LivingEntity entity, DamageSource source, float amount) {
        if(source.getAttacker() instanceof PlayerEntity attackingPlayer) {
            ItemStack mainHandStack = attackingPlayer.getStackInHand(Hand.MAIN_HAND);

            return mainHandStack.getItem() instanceof GrimoireBookItem;
        }
        return false;
    }

    @Override
    public boolean canEntityMove(LivingEntity entity, Vec3d movementInput, float slipperiness) {
        entity.setVelocity(0,0 ,0);
        return false;
    }
}
