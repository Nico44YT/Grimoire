package nazario.grimoire.registry;

import nazario.grimoire.Grimoire;
import nazario.grimoire.common.block.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

public class BlockRegistry {

    public static Block MOSS_CARPET_VINES = registerBlock("moss_carpet_vines", new MossCarpetBlock(AbstractBlock.Settings.copy(Blocks.MOSS_CARPET).noCollision().breakInstantly()));
    public static Block AQUARIUM_GLASS_PANE = registerBlock("aquarium_glass_pane", new AquariumGlassPaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS)));
    public static Block IRON_SCAFFOLDING = registerBlock("iron_scaffolding", new IronScaffoldingBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.IRON_GRAY).requiresTool().strength(3.0f, 2.0f).sounds(BlockSoundGroup.METAL)));

    public static Block ACACIA_CONNECTED_LOG_BLOCK = registerBlock("acacia_connected_log", new ConnectedLogsBlock(AbstractBlock.Settings.copy(Blocks.ACACIA_PLANKS)), new FabricItemSettings().group(ItemRegistry.GRIMOIRE_ITEM_GROUP));
    public static Block BIRCH_CONNECTED_LOG_BLOCK = registerBlock("birch_connected_log", new ConnectedLogsBlock(AbstractBlock.Settings.copy(Blocks.BIRCH_PLANKS)), new FabricItemSettings().group(ItemRegistry.GRIMOIRE_ITEM_GROUP));
    public static Block CRIMSON_CONNECTED_LOG_BLOCK = registerBlock("crimson_connected_log", new ConnectedLogsBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_PLANKS)), new FabricItemSettings().group(ItemRegistry.GRIMOIRE_ITEM_GROUP));
    public static Block DARK_OAK_CONNECTED_LOG_BLOCK = registerBlock("dark_oak_connected_log", new ConnectedLogsBlock(AbstractBlock.Settings.copy(Blocks.DARK_OAK_PLANKS)), new FabricItemSettings().group(ItemRegistry.GRIMOIRE_ITEM_GROUP));
    public static Block JUNGLE_CONNECTED_LOG_BLOCK = registerBlock("jungle_connected_log", new ConnectedLogsBlock(AbstractBlock.Settings.copy(Blocks.JUNGLE_PLANKS)), new FabricItemSettings().group(ItemRegistry.GRIMOIRE_ITEM_GROUP));
    public static Block MANGROVE_CONNECTED_LOG_BLOCK = registerBlock("mangrove_connected_log", new ConnectedLogsBlock(AbstractBlock.Settings.copy(Blocks.MANGROVE_PLANKS)), new FabricItemSettings().group(ItemRegistry.GRIMOIRE_ITEM_GROUP));
    public static Block OAK_CONNECTED_LOG_BLOCK = registerBlock("oak_connected_log", new ConnectedLogsBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)), new FabricItemSettings().group(ItemRegistry.GRIMOIRE_ITEM_GROUP));
    public static Block SPRUCE_CONNECTED_LOG_BLOCK = registerBlock("spruce_connected_log", new ConnectedLogsBlock(AbstractBlock.Settings.copy(Blocks.SPRUCE_PLANKS)), new FabricItemSettings().group(ItemRegistry.GRIMOIRE_ITEM_GROUP));
    public static Block WARPED_CONNECTED_LOG_BLOCK = registerBlock("warped_connected_log", new ConnectedLogsBlock(AbstractBlock.Settings.copy(Blocks.WARPED_PLANKS)), new FabricItemSettings().group(ItemRegistry.GRIMOIRE_ITEM_GROUP));

    public static void register() {
        Registry.register(
                Registry.ITEM,
                Grimoire.id("aquarium_glass_pane"),
                new AquariumGlassPaneBlockItem(new FabricItemSettings().group(ItemRegistry.GRIMOIRE_ITEM_GROUP)));
        Registry.register(
                Registry.ITEM,
                Grimoire.id("iron_scaffolding"),
                new IronScaffoldingBlockItem(new FabricItemSettings().group(ItemRegistry.GRIMOIRE_ITEM_GROUP)));
        Registry.register(
                Registry.ITEM,
                Grimoire.id("moss_carpet_vines"),
                new MossCarpetBlockItem(MOSS_CARPET_VINES, new FabricItemSettings().group(ItemRegistry.GRIMOIRE_ITEM_GROUP))
        );
    };

    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registry.BLOCK, Grimoire.id(name), block);
    }

    private static Block registerBlock(String name, Block block, FabricItemSettings itemSettings) {
        Registry.register(
                Registry.ITEM,
                Grimoire.id(name),
                new BlockItem(block, itemSettings));

        return Registry.register(Registry.BLOCK, Grimoire.id(name), block);
    }
}
