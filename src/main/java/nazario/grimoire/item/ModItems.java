package nazario.grimoire.item;

import nazario.grimoire.GrimoireMain;
import nazario.grimoire.item.custom.oathbreaker.OathbreakerSummoner;
import nazario.liby.api.registry.helper.LibyItemRegistry;
import net.minecraft.item.Item;

public class ModItems {
    public static final LibyItemRegistry REGISTRY = LibyItemRegistry.of(GrimoireMain.MOD_ID);

    public static Item OATHBREAKER = REGISTRY.registerItem("oathbreaker", new OathbreakerSummoner(new Item.Settings().maxCount(1)));

    public static void register() {

    }
}
