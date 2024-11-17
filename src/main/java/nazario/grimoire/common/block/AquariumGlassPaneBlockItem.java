package nazario.grimoire.common.block;

import nazario.grimoire.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class AquariumGlassPaneBlockItem extends BlockItem {
    public AquariumGlassPaneBlockItem(Settings settings) {
        super(BlockRegistry.AQUARIUM_GLASS_PANE, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Direction placementDirection = context.getSide();
        BlockPos offsetPos = context.getBlockPos().offset(placementDirection, 1);

        World world = context.getWorld();
        Vec3d hitPos = context.getHitPos();

        hitPos = hitPos.add(-offsetPos.getX(), -offsetPos.getY(), -offsetPos.getZ());

        //if (hitPos.getX() > 1 || hitPos.getY() > 1 || hitPos.getZ() > 1) {
        //    return customPlace(hitPos, world, offsetPos.offset(placementDirection, -1), context);
        //}

        return customPlace(hitPos, world, offsetPos, context);
    }

    private ActionResult customPlace(Vec3d hitPos, World world, BlockPos offsetPos, ItemUsageContext context) {

        BlockState state = world.getBlockState(offsetPos);

        if (!(state.getBlock() instanceof AquariumGlassPaneBlock)) {
            state = BlockRegistry.AQUARIUM_GLASS_PANE.getDefaultState()
                    .with(AquariumGlassPaneBlock.WATERLOGGED, false)
                    .with(AquariumGlassPaneBlock.NORTH_FILLED, false)
                    .with(AquariumGlassPaneBlock.SOUTH_FILLED, false)
                    .with(AquariumGlassPaneBlock.WEST_FILLED, false)
                    .with(AquariumGlassPaneBlock.EAST_FILLED, false)
                    .with(AquariumGlassPaneBlock.UP_FILLED, false)
                    .with(AquariumGlassPaneBlock.BOTTOM_FILLED, false);
        }

        Vec3d bottomPos = new Vec3d(0.5, -0.3, 0.5); //0

        Vec3d northPos = new Vec3d(0, hitPos.y, 0.5); //1
        Vec3d southPos = new Vec3d(1, hitPos.y, 0.5); //2

        Vec3d westPos = new Vec3d(0.5, hitPos.y, 1); //3
        Vec3d eastPos = new Vec3d(0.5, hitPos.y, 0); //4

        Vec3d topPos = new Vec3d(0.5, 1.3, 0.5); // 5

        double shortestDistance = Double.MAX_VALUE;
        int index = 0;
        Vec3d[] positions = new Vec3d[]{bottomPos, northPos, southPos, westPos, eastPos, topPos};

        for (int i = 0; i < positions.length; i++) {
            if (positions[i].distanceTo(hitPos) < shortestDistance) {
                index = i;
                shortestDistance = positions[i].distanceTo(hitPos);
            }
        }

        BooleanProperty property = switch (index) {
            case 0 -> AquariumGlassPaneBlock.BOTTOM_FILLED;
            case 1 -> AquariumGlassPaneBlock.NORTH_FILLED;
            case 2 -> AquariumGlassPaneBlock.SOUTH_FILLED;
            case 3 -> AquariumGlassPaneBlock.WEST_FILLED;
            case 4 -> AquariumGlassPaneBlock.EAST_FILLED;
            case 5 -> AquariumGlassPaneBlock.UP_FILLED;
            default -> null;
        };

        if(property != null) {
            if(world.getBlockState(offsetPos).getBlock() instanceof AquariumGlassPaneBlock) if(world.getBlockState(offsetPos).get(property)) return ActionResult.FAIL;
        }
        if(!world.getBlockState(offsetPos).isAir()) {
            if(!(world.getBlockState(offsetPos).getBlock() instanceof AquariumGlassPaneBlock)) return ActionResult.FAIL;
        }

        world.setBlockState(offsetPos, state.with(property, true));

        BlockSoundGroup blockSoundGroup = state.getSoundGroup();
        world.playSound(context.getPlayer(), offsetPos, this.getPlaceSound(state), SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0f) / 2.0f, blockSoundGroup.getPitch() * 0.8f);
        world.emitGameEvent(GameEvent.BLOCK_PLACE, offsetPos, GameEvent.Emitter.of(context.getPlayer(), state));
        if (context.getPlayer() == null || !context.getPlayer().getAbilities().creativeMode) {
            context.getStack().decrement(1);
        }
        return ActionResult.success(context.getWorld().isClient);
    }

    @Override
    public ActionResult place(ItemPlacementContext context) {
        return useOnBlock(context);
    }

    @Override
    protected boolean canPlace(ItemPlacementContext context, BlockState state) {
        return super.canPlace(context, state);
    }
}
    /*
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        Direction placementDirection = context.getSide();
        Direction direction = context.getPlayerFacing().rotateYClockwise();

        BlockPos offsetPos = pos.offset(placementDirection, 1);

        if(!context.getPlayer().isSneaking()) {
            if(context.getWorld().getBlockState(new BlockPos(offsetPos).offset(placementDirection, -1)).getBlock() instanceof AquariumGlassPaneBlock) {
                offsetPos = offsetPos.offset(placementDirection, -1);
                if(context.getWorld().getBlockState(offsetPos).getBlock() instanceof AquariumGlassPaneBlock) {
                    return place(direction, context.getWorld(), offsetPos, context);
                }
            }
            if(context.getWorld().getBlockState(offsetPos).getBlock() instanceof AquariumGlassPaneBlock) {
               return place(direction, context.getWorld(), offsetPos, context);
            }
        } else {

        }

        return super.useOnBlock(context);
    }

    private ActionResult place(Direction direction, World world, BlockPos offsetPos, ItemUsageContext context) {
        BlockState state = world.getBlockState(offsetPos);

        switch(direction) {
            case DOWN, UP -> {}
            case NORTH -> {
                if(state.get(AquariumGlassPaneBlock.NORTH_FILLED)) return super.useOnBlock(context);
                state = state.with(AquariumGlassPaneBlock.NORTH_FILLED, true);
            }
            case SOUTH -> {
                if(state.get(AquariumGlassPaneBlock.SOUTH_FILLED)) return super.useOnBlock(context);
                state = state.with(AquariumGlassPaneBlock.SOUTH_FILLED, true);
            }
            case WEST -> {
                if(state.get(AquariumGlassPaneBlock.WEST_FILLED)) return super.useOnBlock(context);
                state = state.with(AquariumGlassPaneBlock.WEST_FILLED, true);
            }
            case EAST -> {
                if(state.get(AquariumGlassPaneBlock.EAST_FILLED)) return super.useOnBlock(context);
                state = state.with(AquariumGlassPaneBlock.EAST_FILLED, true);
            }
        }

        context.getWorld().setBlockState(offsetPos, state);

        BlockSoundGroup blockSoundGroup = state.getSoundGroup();
        context.getWorld().playSound(context.getPlayer(), offsetPos, this.getPlaceSound(state), SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0f) / 2.0f, blockSoundGroup.getPitch() * 0.8f);
        context.getWorld().emitGameEvent(GameEvent.BLOCK_PLACE, offsetPos, GameEvent.Emitter.of(context.getPlayer(), state));
        if (context.getPlayer() == null || !context.getPlayer().getAbilities().creativeMode) {
            context.getStack().decrement(1);
        }
        return ActionResult.success(context.getWorld().isClient);
    }
     */