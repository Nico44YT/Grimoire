package nazario.grimoire.block;

import nazario.grimoire.util.LibyVoxelUtil;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class ConnectedLogsBlock extends Block implements Waterloggable {

    public static final VoxelShape CORE_SHAPE = Block.createCuboidShape(4, 4, 4, 12, 12, 12);
    public static final VoxelShape SIDE_SHAPE = Block.createCuboidShape(0, 4, 4, 4, 12, 12);
    public static final VoxelShape TOP_SHAPE = Block.createCuboidShape(4, 4, 4, 12, 16, 12);
    public static final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape(4, 0, 4, 12, 8, 12);

    public ConnectedLogsBlock(Settings settings) {
        super(settings);
        this.setDefaultState(super.getDefaultState()
                .with(NORTH_PRESENT, false)
                .with(SOUTH_PRESENT, false)
                .with(WEST_PRESENT, false)
                .with(EAST_PRESENT, false)
                .with(TOP_PRESENT, false)
                .with(BOTTOM_PRESENT, false)
                .with(WATERLOGGED, false)
        );
    }

    public static final BooleanProperty NORTH_PRESENT = BooleanProperty.of("north_present");
    public static final BooleanProperty SOUTH_PRESENT = BooleanProperty.of("south_present");
    public static final BooleanProperty WEST_PRESENT = BooleanProperty.of("west_present");
    public static final BooleanProperty EAST_PRESENT = BooleanProperty.of("east_present");

    public static final BooleanProperty TOP_PRESENT = BooleanProperty.of("top_present");
    public static final BooleanProperty BOTTOM_PRESENT = BooleanProperty.of("bottom_present");

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public static final BooleanProperty[] PROPERTIES = new BooleanProperty[]{NORTH_PRESENT, SOUTH_PRESENT, WEST_PRESENT, EAST_PRESENT, TOP_PRESENT, BOTTOM_PRESENT};

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED).booleanValue()) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        for(int i = 0;i<PROPERTIES.length;i++) {
            Direction dir = getDirectionFromIndex(i);
            BlockPos offsetPos = new BlockPos(pos).offset(dir, 1);
            if(world.getBlockState(offsetPos).getBlock() instanceof ConnectedLogsBlock || world.getBlockState(offsetPos).isSideSolid(world, offsetPos, getDirectionFromIndex(i).getOpposite(), SideShapeType.FULL)) {
                state = state.with(PROPERTIES[i], true);
            } else {
                state = state.with(PROPERTIES[i], false);
            }
        }
        return state;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        world.updateNeighbor(pos, this, pos);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = CORE_SHAPE;

        if(state.get(NORTH_PRESENT).booleanValue()) shape = VoxelShapes.union(shape, LibyVoxelUtil.rotate(SIDE_SHAPE, 90));
        if(state.get(SOUTH_PRESENT).booleanValue()) shape = VoxelShapes.union(shape, LibyVoxelUtil.rotate(SIDE_SHAPE, 270));
        if(state.get(WEST_PRESENT).booleanValue()) shape = VoxelShapes.union(shape, LibyVoxelUtil.rotate(SIDE_SHAPE, 0));
        if(state.get(EAST_PRESENT).booleanValue()) shape = VoxelShapes.union(shape, LibyVoxelUtil.rotate(SIDE_SHAPE, 180));
        if(state.get(TOP_PRESENT).booleanValue()) shape = VoxelShapes.union(shape, TOP_SHAPE);
        if(state.get(BOTTOM_PRESENT).booleanValue()) shape = VoxelShapes.union(shape, BOTTOM_SHAPE);

        return shape;
    }

    public Direction getDirectionFromIndex(int index) {
        return switch(MathHelper.clamp(index, 0, PROPERTIES.length-1)) {
            case 0 -> Direction.NORTH;
            case 1 -> Direction.SOUTH;
            case 2 -> Direction.WEST;
            case 3 -> Direction.EAST;
            case 4 -> Direction.UP;
            case 5 -> Direction.DOWN;
            default -> null;
        };
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        World worldAccess = ctx.getWorld();
        boolean bl = worldAccess.getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER;

        BlockState state = this.getDefaultState();

        for(int i = 0;i<PROPERTIES.length;i++) {
            Direction dir = getDirectionFromIndex(i);
            BlockPos offsetPos = new BlockPos(ctx.getBlockPos()).offset(dir, 1);
            if(worldAccess.getBlockState(offsetPos).getBlock() instanceof ConnectedLogsBlock || worldAccess.getBlockState(offsetPos).isSideSolid(worldAccess, offsetPos, getDirectionFromIndex(i).getOpposite(), SideShapeType.FULL)) {
                state = state.with(PROPERTIES[i], true);
            } else {
                state = state.with(PROPERTIES[i], false);
            }
        }

        return state.with(WATERLOGGED, bl);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED).booleanValue()) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORTH_PRESENT, SOUTH_PRESENT, WEST_PRESENT, EAST_PRESENT, TOP_PRESENT, BOTTOM_PRESENT, WATERLOGGED);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
