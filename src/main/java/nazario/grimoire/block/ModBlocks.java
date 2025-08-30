package nazario.grimoire.block;

import nazario.grimoire.GrimoireMain;
import nazario.grimoire.block.custom.FleshBlock;
import nazario.grimoire.block.custom.grand_door.GrandDoorBlock;
import nazario.grimoire.block.custom.grand_door.GrandDoorBlockEntity;
import nazario.grimoire.item.ModItems;
import nazario.liby.api.registry.helper.LibyBlockEntityRegistry;
import nazario.liby.api.registry.helper.LibyBlockRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;

public class ModBlocks {
    public static final LibyBlockRegistry REGISTRY = LibyBlockRegistry.of(GrimoireMain.MOD_ID);
    public static final LibyBlockEntityRegistry BLOCK_ENTITY_REGISTRY = LibyBlockEntityRegistry.of(GrimoireMain.MOD_ID);

    public static final Block FLESH_BLOCK = REGISTRY.registerBlock("flesh_block", new FleshBlock(AbstractBlock.Settings.copy(Blocks.HONEY_BLOCK)), new Item.Settings().food(FoodComponents.ROTTEN_FLESH));
    public static final Block VANISHED = REGISTRY.registerBlock("vanished_fluid", new FluidBlock(ModFluids.VANISHED, AbstractBlock.Settings.create().mapColor(MapColor.BLACK).replaceable().noCollision().strength(100.0F).pistonBehavior(PistonBehavior.DESTROY).dropsNothing().liquid().sounds(BlockSoundGroup.INTENTIONALLY_EMPTY)));
    public static final Block DARKNESS = REGISTRY.registerBlock("darkness_block", new Block(AbstractBlock.Settings.copy(Blocks.BEDROCK)), new Item.Settings());

    public static Block GRAND_DOOR = REGISTRY.registerBlock("grand_door", new GrandDoorBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK)), new Item.Settings());
    public static BlockEntityType<GrandDoorBlockEntity> GRAND_DOOR_TYPE = BLOCK_ENTITY_REGISTRY.registerBlockEntityType("grand_door", FabricBlockEntityTypeBuilder.create(GrandDoorBlockEntity::new, ModBlocks.GRAND_DOOR).build());

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(ModItems.GROUP).register(context -> {
            context.addAfter(Items.AIR, FLESH_BLOCK, GRAND_DOOR);
        });
    }
}
