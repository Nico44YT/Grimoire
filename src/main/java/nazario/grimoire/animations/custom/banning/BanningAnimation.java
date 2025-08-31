package nazario.grimoire.animations.custom.banning;

import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import nazario.grimoire.GrimoireMain;
import nazario.grimoire.misc.ModDimensions;
import nazario.grimoire.misc.ModSounds;
import nazario.grimoire.misc.ModVfxEffects;
import nazario.liby.api.animation.v2.LibyAnimation;
import nazario.liby.api.animation.v2.LibyEntityAnimation;
import nazario.liby.api.util.LibyIdentifier;
import nazario.liby.api.util.nbt.LibyNbtCompound;
import nazario.liby.api.util.rendering.particle.LibyParticleUtil;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class BanningAnimation extends LibyEntityAnimation<LivingEntity> {
    private Vec3d initialPos;
    private static final ParticleEmitterInfo SOUL_VFX = new ParticleEmitterInfo(ModVfxEffects.SOUL_RISING);
    private ParticleEmitterInfo playingBanishmentVfx;
    private ParticleEmitterInfo playingSoulVfx;
    private int explosionRadius = 8;

    private Identifier emitter;

    public BanningAnimation(LibyIdentifier identifier, Predicate<LivingEntity> predicate) {
        super(identifier, predicate);
    }

    @Override
    public long getMaxDuration() {
        return 20*50;
    }

    @Override
    public void onStart(World world, LivingEntity livingEntity) {
        livingEntity.getWorld().playSound(null, livingEntity.getBlockPos(), ModSounds.GRIMOIRE_START, SoundCategory.PLAYERS, 1, 1);
        livingEntity.getWorld().playSound(null, livingEntity.getBlockPos(), ModSounds.EVENT_BANISHMENT, SoundCategory.MASTER, 1, 1);

        this.playingSoulVfx = null;
        this.playingBanishmentVfx = null;

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

    }

    @Override
    public void onRestart(World world, LivingEntity livingEntity, LibyAnimation<LivingEntity> libyAnimation) {

    }

    @Override
    public void onTick(World world, LivingEntity livingEntity) {
        if(this.getTicksPassed() > 20*14 && this.playingBanishmentVfx == null && !world.isClient()) {
            this.emitter = GrimoireMain.id(livingEntity.getUuidAsString());
            this.playingBanishmentVfx = new ParticleEmitterInfo(ModVfxEffects.BANISHMENT, this.emitter).position(livingEntity.getPos());
            AAALevel.addParticle(world, 8000, playingBanishmentVfx);
        }

        if (this.getTicksPassed() > 20 * 37.5 && this.getTicksPassed() <= 20 * 40 && !world.isClient()) {
            // Gradual expansion
            explosionRadius++;

            if (explosionRadius % 8 == 0) { // More frequent rings
                int rings = explosionRadius / 4;
                int explosionsPerRing = 16; // Randomize count

                for (int ring = 1; ring <= rings; ring++) {
                    for (int i = 0; i < explosionsPerRing; i++) {
                        Vec3d pos = LibyParticleUtil.drawCircle(
                                livingEntity.getPos(),
                                i,
                                explosionsPerRing,
                                ring * 5f,
                                0,
                                ring * 5f
                        );

                        // Vary explosion power for chaos
                        float power = 15f;

                        createExplosion(world, null, world.getDamageSources().magic(), null,
                                pos.getX(), pos.getY(), pos.getZ(),
                                power, false, World.ExplosionSourceType.NONE, false, Explosion.DestructionType.DESTROY_WITH_DECAY);
                    }
                }
            }
        }

        if(this.getTicksPassed() > 20*47 && this.playingSoulVfx == null) {
            if(world instanceof ServerWorld serverWorld) {
                this.playingSoulVfx = SOUL_VFX.clone().position(livingEntity.getPos().add(0, 0.5, 0));
                AAALevel.addParticle(world, 4000, playingSoulVfx);

                world.getServer().getPlayerManager().getPlayerList().forEach(player -> {
                    player.playSound(ModSounds.EVENT_SOUL_MUSIC, SoundCategory.MASTER, 1, 1);
                });

                RegistryKey<World> registryKey = ModDimensions.AVIRITUM_DIMENSION_KEY;
                ServerWorld dimensionWorld = serverWorld.getServer().getWorld(registryKey);

                livingEntity.teleport(dimensionWorld, 0, 0, 0, null, livingEntity.getYaw(), livingEntity.getPitch());

                dimensionWorld.getServer().getPlayerManager().getPlayerList().forEach(player -> {
                    player.sendMessage(Text.translatable("death.grimoire.bannishment", livingEntity.getName()));
                });
            }
        }
    }

    @Override
    public void render(LivingEntity livingEntity, float v, float v1, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

    }

    @Override
    public void readCustomData(LibyNbtCompound tag) {
        if(tag.isPresent("initial_pos")) this.initialPos = tag.getVec3d("initial_pos");
    }

    @Override
    public void writeCustomData(LibyNbtCompound tag) {
        if(this.initialPos != null) {
            tag.putVec3d("initial_pos", this.initialPos);
        }
    }

    @Override
    public RenderType shouldRender(LivingEntity entity, Frustum frustum, double x, double y, double z) {
        if(this.getTicksPassed() > 20*34) return RenderType.NEVER_RENDER;
        return RenderType.ALWAYS_RENDER;
    }


    @Override
    public <U extends LivingEntity> boolean canEntityTakeDamage(U entity, DamageSource source, float amount) {
        return false;
    }

    @Override
    public <U extends LivingEntity> boolean canEntityMove(U entity, Vec3d movementInput, float slipperiness) {
        entity.setVelocity(0,0 ,0 );
        entity.velocityDirty = true;
        return false;
    }

    public static Explosion createExplosion(World world, @Nullable Entity entity, @Nullable DamageSource damageSource, @Nullable ExplosionBehavior behavior, double x, double y, double z, float power, boolean createFire, World.ExplosionSourceType explosionSourceType, boolean particles, Explosion.DestructionType type) {
        Explosion explosion = new Explosion(world, entity, damageSource, behavior, x, y, z, power, createFire, type);
        explosion.collectBlocksAndDamageEntities();
        explosion.affectWorld(particles);
        return explosion;
    }
}
