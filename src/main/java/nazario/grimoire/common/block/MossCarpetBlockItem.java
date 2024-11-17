package nazario.grimoire.common.block;

import nazario.grimoire.common.MixinBlockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MossCarpetBlockItem extends BlockItem {
    public MossCarpetBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        //World world = context.getWorld();
        //BlockPos pos = context.getBlockPos();
        //BlockState state = context.getWorld().getBlockState(pos);
//
        //if(state.getBlock() instanceof SlabBlock slabBlock) {
        //    if(state.get(SlabBlock.TYPE).equals(SlabType.BOTTOM) && !state.get(MixinBlockProperties.HAS_MOSS_VINES)) {
        //        world.setBlockState(pos, state.with(MixinBlockProperties.HAS_MOSS_VINES, true));
//
        //        return ActionResult.success(true);
        //    }
        //}
        return super.useOnBlock(context);
    }
}
