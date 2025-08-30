package nazario.grimoire.item.custom.skull_key;

import nazario.grimoire.GrimoireMain;
import nazario.grimoire.block.ModBlocks;
import nazario.grimoire.block.custom.grand_door.GrandDoorBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SkullKeyItem extends Item {
    public SkullKeyItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();

        if(world.getBlockState(pos).getBlock().equals(ModBlocks.GRAND_DOOR)) {
            world.setBlockState(pos, world.getBlockState(pos).with(GrandDoorBlock.OPEN, true));
        }

        return super.useOnBlock(context);

    }

    @Override
    public Text getName(ItemStack stack) {
        Text name = super.getName(stack);
        return Text.literal("").append(name).setStyle(name.getStyle().withFont((GrimoireMain.FONT)));
    }
}
