package nazario.grimoire.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class RedirectItem extends Item {

    Supplier<Item> redirect;

    public RedirectItem(Supplier<Item> redirectItem) {
        super(new Item.Settings());
        this.redirect = redirectItem;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(entity instanceof PlayerEntity player) {
            player.getInventory().setStack(slot, new ItemStack(redirect.get(), stack.getCount()));
        }
    }
}
