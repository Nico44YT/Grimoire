package nazario.grimoire.mixin;

import nazario.grimoire.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public class AxeMixin {

    @Inject(method = "useOnBlock", at = @At("HEAD"))
    public void grimoire$useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity playerEntity = context.getPlayer();
        BlockState blockState = world.getBlockState(blockPos);

        if(blockState.getBlock().equals(Blocks.MOSSY_COBBLESTONE)) grimoire$spawn(playerEntity, context.getHand(), world, blockPos, blockState, Blocks.COBBLESTONE);
        if(blockState.getBlock().equals(Blocks.MOSSY_COBBLESTONE_SLAB)) grimoire$spawn(playerEntity, context.getHand(), world, blockPos, blockState, Blocks.COBBLESTONE_SLAB);
        if(blockState.getBlock().equals(Blocks.MOSSY_COBBLESTONE_STAIRS)) grimoire$spawn(playerEntity, context.getHand(), world, blockPos, blockState, Blocks.COBBLESTONE_STAIRS);
        if(blockState.getBlock().equals(Blocks.MOSSY_COBBLESTONE_STAIRS)) grimoire$spawn(playerEntity, context.getHand(), world, blockPos, blockState, Blocks.COBBLESTONE_WALL);

        if(blockState.getBlock().equals(Blocks.MOSSY_STONE_BRICKS)) grimoire$spawn(playerEntity, context.getHand(), world, blockPos, blockState, Blocks.STONE_BRICKS);
        if(blockState.getBlock().equals(Blocks.MOSSY_STONE_BRICK_SLAB)) grimoire$spawn(playerEntity, context.getHand(), world, blockPos, blockState, Blocks.STONE_BRICK_SLAB);
        if(blockState.getBlock().equals(Blocks.MOSSY_STONE_BRICK_STAIRS)) grimoire$spawn(playerEntity, context.getHand(), world, blockPos, blockState, Blocks.STONE_BRICK_STAIRS);
        if(blockState.getBlock().equals(Blocks.MOSSY_STONE_BRICK_WALL)) grimoire$spawn(playerEntity, context.getHand(), world, blockPos, blockState, Blocks.STONE_BRICK_WALL);
    }

    private void grimoire$spawn(PlayerEntity player, Hand hand, World world, BlockPos blockPos, BlockState state, Block block) {
        world.setBlockState(blockPos, block.getStateWithProperties(state));

        ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, world);

        itemEntity.setStack(new ItemStack(BlockRegistry.MOSS_CARPET_VINES));
        itemEntity.setPosition(new Vec3d(blockPos.getX() + .5f, blockPos.getY() + .5f, blockPos.getZ() + .5f));

        world.spawnEntity(itemEntity);
        player.swingHand(hand);
    }
}
