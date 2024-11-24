package nazario.grimoire.common.entity;

import nazario.grimoire.registry.EntityTypeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class GlassItemFrameEntity extends ItemFrameEntity {
    public GlassItemFrameEntity(EntityType<? extends ItemFrameEntity> entityType, World world) {
        super(entityType, world);
    }

    public static GlassItemFrameEntity create(World world, BlockPos blockPos, Direction direction) {
        GlassItemFrameEntity entity =  new GlassItemFrameEntity(EntityTypeRegistry.GLASS_ITEM_FRAME, world);
        entity.attachmentPos = blockPos;
        entity.setFacing(direction);

        return entity;
    }

    @Override
    public boolean isInvisible() {
        return !getHeldItemStack().getItem().equals(Items.AIR);
    }
}
