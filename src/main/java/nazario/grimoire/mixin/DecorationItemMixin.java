package nazario.grimoire.mixin;

import nazario.grimoire.common.entity.GlassItemFrameEntity;
import nazario.grimoire.registry.EntityTypeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.GlowItemFrameEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DecorationItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;

@Mixin(DecorationItem.class)
public abstract class DecorationItemMixin {

    @Shadow EntityType<? extends AbstractDecorationEntity> entityType;


    /**
     * @author
     * @reason
     */
    @Overwrite
    public ActionResult useOnBlock(ItemUsageContext context) {
        Direction direction = context.getSide();
        BlockPos placePos = context.getBlockPos().offset(direction);

        Vec3d placePosVec = new Vec3d(
                placePos.getX(),
                placePos.getY(),
                placePos.getZ()
        );

        PlayerEntity playerEntity = context.getPlayer();
        ItemStack itemStack = context.getStack();
        if (playerEntity != null && !grimoire$canPlaceOn(playerEntity, direction, itemStack, placePos)) {
            return ActionResult.FAIL;
        } else {
            World world = context.getWorld();
            AbstractDecorationEntity abstractDecorationEntity;
            if (this.entityType == EntityType.PAINTING) {
                Optional<PaintingEntity> optional = PaintingEntity.placePainting(world, placePos, direction);
                if (optional.isEmpty()) {
                    return ActionResult.CONSUME;
                }

                abstractDecorationEntity = (AbstractDecorationEntity)optional.get();
            } else if (this.entityType == EntityType.ITEM_FRAME) {
                abstractDecorationEntity = new ItemFrameEntity(world, placePos, direction);
            } else if (this.entityType == EntityType.GLOW_ITEM_FRAME) {
                abstractDecorationEntity = new GlowItemFrameEntity(world, placePos, direction);
            } else if(this.entityType == EntityTypeRegistry.GLASS_ITEM_FRAME) {
                abstractDecorationEntity = GlassItemFrameEntity.create(world, placePos, direction);
            } else {
                return ActionResult.success(world.isClient);
            }

            NbtCompound nbtCompound = itemStack.getNbt();
            if (nbtCompound != null) {
                EntityType.loadFromEntityNbt(world, playerEntity, abstractDecorationEntity, nbtCompound);
            }

            if (abstractDecorationEntity.canStayAttached()) {
                if (!world.isClient) {
                    abstractDecorationEntity.onPlace();
                    world.emitGameEvent(playerEntity, GameEvent.ENTITY_PLACE, abstractDecorationEntity.getPos());
                    abstractDecorationEntity.setPos(placePosVec.x, placePosVec.y-0.5, placePosVec.z);
                    world.spawnEntity(abstractDecorationEntity);
                }

                itemStack.decrement(1);
                return ActionResult.success(world.isClient);
            } else {
                return ActionResult.CONSUME;
            }
        }
    }

    @Unique
    private static boolean grimoire$canPlaceOn(PlayerEntity player, Direction side, ItemStack stack, BlockPos pos) {
        return player.canPlaceOn(pos, side, stack);
    }
}
