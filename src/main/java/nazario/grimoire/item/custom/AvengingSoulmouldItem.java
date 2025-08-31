package nazario.grimoire.item.custom;

import nazario.grimoire.GrimoireMain;
import nazario.grimoire.entity.ModEntities;
import nazario.grimoire.entity.avenging_soulmould.AvengingSoulmouldEntity;
import nazario.grimoire.item.CustomTooltipBackground;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AvengingSoulmouldItem extends Item {
    public AvengingSoulmouldItem(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        Text name = super.getName(stack);
        return Text.literal("").append(name).setStyle(name.getStyle().withFont((GrimoireMain.FONT)));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext ctx) {
        PlayerEntity player = ctx.getPlayer();
        BlockPos pos = ctx.getBlockPos();
        Direction dir = ctx.getHorizontalPlayerFacing();
        if((ctx.getWorld().getBlockState(pos.offset(ctx.getSide())).isAir() && ctx.getWorld().getBlockState(pos.offset(ctx.getSide()).offset(Direction.UP)).isAir() && ctx.getWorld().getBlockState(pos.offset(ctx.getSide()).offset(Direction.UP, 2)).isAir())
                ||
                (ctx.getWorld().getBlockState(pos.offset(ctx.getSide())).getBlock().equals(Blocks.WATER) && ctx.getWorld().getBlockState(pos.offset(ctx.getSide()).offset(Direction.UP)).getBlock().equals(Blocks.WATER) && ctx.getWorld().getBlockState(pos.offset(ctx.getSide()).offset(Direction.UP, 2)).getBlock().equals(Blocks.WATER))
        ) {
            AvengingSoulmouldEntity mould = new AvengingSoulmouldEntity(ModEntities.AVENGING_SOULMOULD, ctx.getWorld());
            mould.refreshPositionAndAngles(pos.offset(ctx.getSide()), 0, 0);
            mould.setDormantDir(ctx.getHorizontalPlayerFacing().getOpposite());
            mould.setDormantPos(pos.offset(ctx.getSide()));
            mould.setActionState(0);

            if(!ctx.getPlayer().getStackInHand(ctx.getHand()).getName().equals(Text.translatable("item.grimoire.avenging_soulmould"))) mould.setCustomName(ctx.getPlayer().getStackInHand(ctx.getHand()).getName());

            assert player != null;
            mould.setOwner(player);
            ctx.getWorld().spawnEntity(mould);
            ctx.getStack().decrement(1);
        }
        return super.useOnBlock(ctx);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(Text.translatable("item.grimoire.avenging_soulmould.lore").setStyle(Style.EMPTY.withFont((GrimoireMain.FONT))));
    }
}
