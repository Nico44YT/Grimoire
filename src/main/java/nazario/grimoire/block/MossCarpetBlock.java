package nazario.grimoire.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarpetBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public class MossCarpetBlock extends CarpetBlock {
    public MossCarpetBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient && player.getStackInHand(hand).getItem().equals(Items.BONE_MEAL)) {
            player.getStackInHand(hand).decrement(1);

            world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, pos, 0);

            for(int i = 0;i<10;i++) {
                if (world.random.nextFloat() < 0.4f) {
                    // Attempt to spread in a random direction up to 2 blocks away
                    int range = 2;
                    BlockPos spreadPos = pos.add(
                            world.random.nextInt(2 * range + 1) - range,  // Random x offset from -range to +range
                            0,
                            world.random.nextInt(2 * range + 1) - range   // Random z offset from -range to +range
                    );

                    // Check if the target position is air before placing the new block
                    if (world.isAir(spreadPos) && world.canSetBlock(spreadPos)) {
                        world.setBlockState(spreadPos, state);
                    }
                }
            }

            return ActionResult.SUCCESS;
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return true;
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return 0;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }
}
