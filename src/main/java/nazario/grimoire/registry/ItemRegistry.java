package nazario.grimoire.registry;

import nazario.grimoire.Grimoire;
import nazario.grimoire.common.item.AngelicSpearItem;
import nazario.grimoire.common.item.ZekkensVoiceItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFrameItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

public class ItemRegistry {
    public static final ItemGroup GRIMOIRE_ITEM_GROUP = FabricItemGroupBuilder.build(Grimoire.id("grimoire_tab"), () -> new ItemStack(BlockRegistry.IRON_SCAFFOLDING));

    public static final Item ZEKKENS_VOICE = registerItem("zekkens_voice", new ZekkensVoiceItem(new Item.Settings().group(GRIMOIRE_ITEM_GROUP).maxCount(1)));
    public static final Item ANGELIC_SPEAR = registerItem("angelic_spear", new AngelicSpearItem(new Item.Settings().group(GRIMOIRE_ITEM_GROUP).maxCount(1)));

    public static final Item ANGELIC_METAL_SHARD = registerItem("angelic_metal_shard", new Item(new Item.Settings().group(GRIMOIRE_ITEM_GROUP)));
    public static final Item ANGELIC_METAL_INGOT = registerItem("angelic_metal_ingot", new Item(new Item.Settings().group(GRIMOIRE_ITEM_GROUP)));

    public static final Item GLASS_ITEM_FRAME = registerItem("glass_item_frame", new ItemFrameItem(EntityTypeRegistry.GLASS_ITEM_FRAME, new Item.Settings()));

    public static void register() {
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, Grimoire.id(name), item);
    }
}
