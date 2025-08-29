package nazario.grimoire.item;

import nazario.grimoire.GrimoireMain;
import nazario.grimoire.block.ModFluids;
import nazario.grimoire.item.custom.oathbreaker.OathbreakerSummoner;
import nazario.liby.api.registry.helper.LibyItemRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

public class ModItems {
    public static final LibyItemRegistry REGISTRY = LibyItemRegistry.of(GrimoireMain.MOD_ID);
    public static final RegistryKey<ItemGroup> GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, GrimoireMain.id("group"));

    public static final Item OATHBREAKER = REGISTRY.registerItem("oathbreaker", new OathbreakerSummoner(new Item.Settings().maxCount(1)));
    public static final Item VANISHED_BUCKET = REGISTRY.registerItem("vanished_fluid_bucket", new BucketItem(ModFluids.VANISHED, new Item.Settings().maxCount(1)));

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, GROUP.getValue(),
                FabricItemGroup.builder()
                        .icon(OATHBREAKER::getDefaultStack)
                        .displayName(Text.translatable("grimoire.item_group").setStyle(Style.EMPTY.withFont(GrimoireMain.FONT).withColor(TextColor.fromRgb(0xFF1122))))
                        .build());

        ItemGroupEvents.modifyEntriesEvent(GROUP).register(con -> {
            con.addAfter(Items.AIR, OATHBREAKER, VANISHED_BUCKET);
        });
    }
}
