package nazario.grimoire.block;

import nazario.grimoire.registry.BlockRegistry;
import nazario.grimoire.util.LibyVoxelUtil;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class AquariumGlassPaneBlock extends GlassBlock implements Waterloggable {
    public static VoxelShape SHAPE = AquariumGlassPaneBlock.createCuboidShape(0, 0, 0, 1, 16, 16);

    public static final BooleanProperty NORTH_FILLED = BooleanProperty.of("north_filled");
    public static final BooleanProperty SOUTH_FILLED = BooleanProperty.of("south_filled");
    public static final BooleanProperty WEST_FILLED = BooleanProperty.of("west_filled");
    public static final BooleanProperty EAST_FILLED = BooleanProperty.of("east_filled");

    public static final BooleanProperty UP_FILLED = BooleanProperty.of("up_filled");
    public static final BooleanProperty BOTTOM_FILLED = BooleanProperty.of("bottom_filled");

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public AquariumGlassPaneBlock(Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction;
        if(ctx.getPlayer().isSneaking()) {
            direction = ctx.getPlayer().getPitch() < 0 ? Direction.UP : Direction.DOWN;
        } else {
            direction = ctx.getPlayerFacing().rotateYClockwise();
        }

        BlockState state = this.getDefaultState().with(WATERLOGGED, false);

        state = state.with(NORTH_FILLED, false).with(SOUTH_FILLED, false).with(WEST_FILLED, false).with(EAST_FILLED, false).with(UP_FILLED, false).with(BOTTOM_FILLED, false);

        switch(direction) {
            case UP -> {if(ctx.getPlayer().isSneaking()) state = state.with(UP_FILLED, true);}
            case DOWN -> {if(ctx.getPlayer().isSneaking()) state = state.with(BOTTOM_FILLED, true);}
            case NORTH -> state = state.with(NORTH_FILLED, true);
            case SOUTH -> state = state.with(SOUTH_FILLED, true);
            case WEST -> state = state.with(WEST_FILLED, true);
            case EAST -> state = state.with(EAST_FILLED, true);
        }

        return state;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(!player.isCreative()) {
            ItemEntity entity = new ItemEntity(EntityType.ITEM, world);

            int count = 0;

            if(state.get(NORTH_FILLED).booleanValue()) count++;
            if(state.get(SOUTH_FILLED).booleanValue()) count++;
            if(state.get(WEST_FILLED).booleanValue()) count++;
            if(state.get(EAST_FILLED).booleanValue()) count++;
            if(state.get(UP_FILLED).booleanValue()) count++;
            if(state.get(BOTTOM_FILLED).booleanValue()) count++;

            entity.setStack(new ItemStack(BlockRegistry.AQUARIUM_GLASS_PANE, count));
            entity.setPosition(new Vec3d(pos.getX()+.5f, pos.getY()+.5f, pos.getZ()+.5f));

            world.spawnEntity(entity);
        }

        super.onBreak(world, pos, state, player);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return 0;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, NORTH_FILLED, SOUTH_FILLED, WEST_FILLED, EAST_FILLED, UP_FILLED, BOTTOM_FILLED);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED).booleanValue()) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = VoxelShapes.empty();

        if(state.get(NORTH_FILLED).booleanValue()) shape = VoxelShapes.union(shape, LibyVoxelUtil.rotate(SHAPE, 0));
        if(state.get(SOUTH_FILLED).booleanValue()) shape = VoxelShapes.union(shape, LibyVoxelUtil.rotate(SHAPE, 180));
        if(state.get(WEST_FILLED).booleanValue()) shape = VoxelShapes.union(shape, LibyVoxelUtil.rotate(SHAPE, 270));
        if(state.get(EAST_FILLED).booleanValue()) shape = VoxelShapes.union(shape, LibyVoxelUtil.rotate(SHAPE, 90));
        if(state.get(UP_FILLED).booleanValue()) shape = VoxelShapes.union(shape, Block.createCuboidShape(0, 15, 0, 16, 16, 16));
        if(state.get(BOTTOM_FILLED).booleanValue()) shape = VoxelShapes.union(shape, Block.createCuboidShape(0, 0, 0, 16, 1, 16));

        return shape;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED).booleanValue()) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }
}
