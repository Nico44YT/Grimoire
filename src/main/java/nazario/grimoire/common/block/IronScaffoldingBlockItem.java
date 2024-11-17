package nazario.grimoire.common.block;

import nazario.grimoire.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ScaffoldingItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class IronScaffoldingBlockItem extends ScaffoldingItem {

    public IronScaffoldingBlockItem(Settings settings) {
        super(BlockRegistry.IRON_SCAFFOLDING, settings);
    }

    @Nullable
    public ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
        BlockPos blockPos = context.getBlockPos();
        World world = context.getWorld();
        BlockState blockState = world.getBlockState(blockPos);
        Block block = this.getBlock();
        if (!blockState.isOf(block)) {
            return context;
        } else {
            Direction direction;
            if (context.shouldCancelInteraction()) {
                direction = context.hitsInsideBlock() ? context.getSide().getOpposite() : context.getSide();
            } else {
                direction = context.getSide() == Direction.UP ? context.getPlayerFacing() : Direction.UP;
            }

            int i = 0;
            BlockPos.Mutable mutable = blockPos.mutableCopy().move(direction);

            while(i < 128) {
                if (!world.isClient && !world.isInBuildLimit(mutable)) {
                    PlayerEntity playerEntity = context.getPlayer();
                    int j = world.getTopY();
                    if (playerEntity instanceof ServerPlayerEntity && mutable.getY() >= j) {
                        ((ServerPlayerEntity)playerEntity).sendMessageToClient(Text.translatable("build.tooHigh", new Object[]{j - 1}).formatted(Formatting.RED), true);
                    }
                    break;
                }

                blockState = world.getBlockState(mutable);
                if (!blockState.isOf(this.getBlock())) {
                    if (blockState.canReplace(context)) {
                        return ItemPlacementContext.offset(context, mutable, direction);
                    }
                    break;
                }

                mutable.move(direction);
                if (direction.getAxis().isHorizontal()) {
                    ++i;
                }
            }

            return null;
        }
    }
}
