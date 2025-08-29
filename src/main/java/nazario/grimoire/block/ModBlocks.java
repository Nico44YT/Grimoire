package nazario.grimoire.block;

import nazario.grimoire.GrimoireMain;
import nazario.grimoire.block.custom.FleshBlock;
import nazario.grimoire.item.ModItems;
import nazario.liby.api.registry.helper.LibyBlockEntityRegistry;
import nazario.liby.api.registry.helper.LibyBlockRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

public class ModBlocks {
    public static final LibyBlockRegistry REGISTRY = LibyBlockRegistry.of(GrimoireMain.MOD_ID);
    public static final LibyBlockEntityRegistry BLOCK_ENTITY_REGISTRY = LibyBlockEntityRegistry.of(GrimoireMain.MOD_ID);

    public static final Block FLESH_BLOCK = REGISTRY.registerBlock("flesh_block", new FleshBlock(AbstractBlock.Settings.copy(Blocks.HONEY_BLOCK)), new Item.Settings().food(FoodComponents.ROTTEN_FLESH));
    public static final Block VANISHED = REGISTRY.registerBlock("vanished_fluid", new FluidBlock(ModFluids.VANISHED, AbstractBlock.Settings.create().mapColor(MapColor.BLACK).replaceable().noCollision().strength(100.0F).pistonBehavior(PistonBehavior.DESTROY).dropsNothing().liquid().sounds(BlockSoundGroup.INTENTIONALLY_EMPTY)));

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(ModItems.GROUP).register(context -> {
            context.add(FLESH_BLOCK);
        });
    }
}
