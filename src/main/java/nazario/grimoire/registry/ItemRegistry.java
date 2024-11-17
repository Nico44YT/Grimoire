package nazario.grimoire.registry;

import nazario.grimoire.Grimoire;
import nazario.grimoire.common.item.AngelicSpearItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

public class ItemRegistry {
    public static final ItemGroup GRIMOIRE_ITEM_GROUP = FabricItemGroupBuilder.build(Grimoire.id("grimoire_tab"), () -> new ItemStack(BlockRegistry.IRON_SCAFFOLDING));


    //public static final Item NICO_PLUSH = registerItem("nico_plush", new Item(new Item.Settings().group(GRIMOIRE_ITEM_GROUP)));

    public static final Item ANGELIC_METAL_SHARD = registerItem("angelic_metal_shard", new Item(new Item.Settings().group(GRIMOIRE_ITEM_GROUP)));
    public static final Item ANGELIC_METAL_INGOT = registerItem("angelic_metal_ingot", new Item(new Item.Settings().group(GRIMOIRE_ITEM_GROUP)));
    public static final Item ANGELIC_SPEAR = registerItem("angelic_spear", new AngelicSpearItem(new Item.Settings().group(GRIMOIRE_ITEM_GROUP).maxCount(1)));

    public static void register() {

    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, Grimoire.id(name), item);
    }
}
