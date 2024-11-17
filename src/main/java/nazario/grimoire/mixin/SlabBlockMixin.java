package nazario.grimoire.mixin;

import nazario.grimoire.common.MixinBlockProperties;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.state.StateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlabBlock.class)
public class SlabBlockMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    public void grimoire$init(AbstractBlock.Settings settings, CallbackInfo ci) {
        ((SlabBlock)(Object)this).setDefaultState(((SlabBlock)(Object)this).getDefaultState().with(MixinBlockProperties.HAS_MOSS_VINES, false));
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    public void grimoire$appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(MixinBlockProperties.HAS_MOSS_VINES);
    }
}


