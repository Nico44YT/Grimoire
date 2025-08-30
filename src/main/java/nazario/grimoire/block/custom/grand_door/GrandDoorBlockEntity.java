package nazario.grimoire.block.custom.grand_door;

import nazario.grimoire.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class GrandDoorBlockEntity extends BlockEntity implements GeoBlockEntity {

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private BlockState prevState;

    public GrandDoorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.GRAND_DOOR_TYPE, pos, state);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenPlay("idle");
    protected static final RawAnimation OPEN_ANIM = RawAnimation.begin().thenPlayAndHold("open");

    private PlayState predicate(AnimationState<GrandDoorBlockEntity> tAnimationState) {
        BlockState state = this.getWorld().getBlockState(this.getPos());

        if(prevState != null && state.getBlock().equals(prevState.getBlock()) && state.get(GrandDoorBlock.OPEN) != prevState.get(GrandDoorBlock.OPEN)) {
            tAnimationState.getController().setAnimation(OPEN_ANIM);
        } else {
            tAnimationState.getController().setAnimation(IDLE_ANIM);
        }

        this.prevState = state;

        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public boolean shouldPlayAnimsWhileGamePaused() {
        return false;
    }

}
