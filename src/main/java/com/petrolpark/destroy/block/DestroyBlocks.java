package com.petrolpark.destroy.block;

import static com.petrolpark.destroy.Destroy.REGISTRATE;
import static com.simibubi.create.AllTags.forgeItemTag;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;

import com.petrolpark.destroy.block.display.PollutometerDisplaySource;
import com.petrolpark.destroy.block.entity.BubbleCapBlockEntity;
import com.petrolpark.destroy.block.entity.CentrifugeBlockEntity;
import com.petrolpark.destroy.block.entity.VatControllerBlockEntity;
import com.petrolpark.destroy.block.model.CopycatBlockModel;
import com.petrolpark.destroy.block.spriteshifts.DestroySpriteShifts;
import com.petrolpark.destroy.entity.PrimedBomb;
import com.petrolpark.destroy.item.CoaxialGearBlockItem;
import com.petrolpark.destroy.item.ColossalCogwheelBlockItem;
import com.petrolpark.destroy.item.CombustibleBlockItem;
import com.petrolpark.destroy.item.DestroyItems;
import com.petrolpark.destroy.item.PeriodicTableBlockItem;
import com.petrolpark.destroy.item.PumpjackBlockItem;
import com.petrolpark.destroy.item.RedstoneProgrammerBlockItem;
import com.petrolpark.destroy.sound.DestroySoundTypes;
import com.petrolpark.destroy.util.DestroyTags.DestroyBlockTags;
import com.petrolpark.destroy.util.DestroyTags.DestroyItemTags;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.AllTags;
import com.simibubi.create.AllTags.AllBlockTags;
import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.content.kinetics.simpleRelays.CogwheelBlockItem;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours;
import com.simibubi.create.foundation.block.connected.SimpleCTBehaviour;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.Tags;

public class DestroyBlocks {

    // BLOCK ENTITIES

    public static final BlockEntry<AgingBarrelBlock> AGING_BARREL = REGISTRATE.block("aging_barrel", AgingBarrelBlock::new)
        .initialProperties(SharedProperties::stone)
        .properties(p -> p
            .mapColor(MapColor.COLOR_BROWN)
            .noOcclusion()
        ).transform(TagGen.axeOnly())
        .item()
        .tag(DestroyItemTags.LIABLE_TO_CHANGE.tag)
        .transform(customItemModel())
        .register();

    public static final BlockEntry<BubbleCapBlock> BUBBLE_CAP = REGISTRATE.block("bubble_cap", BubbleCapBlock::new)
        .initialProperties(SharedProperties::copperMetal)
        .properties(p -> p
            .mapColor(MapColor.COLOR_ORANGE)
            .noOcclusion()
        ).onRegister(AllDisplayBehaviours.assignDataBehaviour(BubbleCapBlockEntity.DISPLAY_SOURCE, "bubble_cap"))
        .transform(TagGen.pickaxeOnly())
        .item()
        .transform(customItemModel())
        .register();

    public static final BlockEntry<CoaxialGearBlock> COAXIAL_GEAR = REGISTRATE.block("coaxial_gear", CoaxialGearBlock::new)
        .initialProperties(AllBlocks.COGWHEEL)
        .properties(p -> p
            .sound(SoundType.WOOD)
		    .mapColor(MapColor.DIRT)
            .noOcclusion()
        ).onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
        .transform(TagGen.axeOrPickaxe())
        .item(CoaxialGearBlockItem::new)
        .build()
        .register();

    public static final BlockEntry<CentrifugeBlock> CENTRIFUGE = REGISTRATE.block("centrifuge", CentrifugeBlock::new)
        .initialProperties(SharedProperties::copperMetal)
        .properties(p -> p
            .mapColor(MapColor.COLOR_ORANGE)
            .noOcclusion()
        ).onRegister(AllDisplayBehaviours.assignDataBehaviour(CentrifugeBlockEntity.INPUT_DISPLAY_SOURCE, "centrifuge_input"))
        .onRegister(AllDisplayBehaviours.assignDataBehaviour(CentrifugeBlockEntity.DENSE_OUTPUT_DISPLAY_SOURCE, "centrifuge_dense_output"))
        .onRegister(AllDisplayBehaviours.assignDataBehaviour(CentrifugeBlockEntity.LIGHT_OUTPIT_DISPLAY_SOURCE, "centrifuge_light_output"))
        .blockstate((c,p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c,p)))
        .transform(TagGen.pickaxeOnly())
        .transform(BlockStressDefaults.setImpact(5.0))
        .item()
        .transform(customItemModel())
        .register();

    public static final BlockEntry<ChainedCogwheelBlock> CHAINED_COGWHEEL = REGISTRATE.block("chained_cogwheel", ChainedCogwheelBlock::small)
        .initialProperties(AllBlocks.COGWHEEL)
        .properties(p -> p
            .noOcclusion()
        ).register();

    public static final BlockEntry<ChainedCogwheelBlock> CHAINED_LARGE_COGWHEEL = REGISTRATE.block("chained_large_cogwheel", ChainedCogwheelBlock::large)
        .initialProperties(CHAINED_COGWHEEL)
        .properties(p -> p
            .noOcclusion()
        ).register();

    public static final BlockEntry<ColorimeterBlock> COLORIMETER = REGISTRATE.block("colorimeter", ColorimeterBlock::new)
        .initialProperties(() -> Blocks.OBSERVER)
        .item()
        .build()
        .register();

    public static final BlockEntry<ColossalCogwheelBlock> COLOSSAL_COGWHEEL = REGISTRATE.block("colossal_cogwheel", ColossalCogwheelBlock::new)
        .initialProperties(AllBlocks.LARGE_WATER_WHEEL)
        .properties(p -> p
            .noOcclusion()
        ).item(ColossalCogwheelBlockItem::new)
        .transform(customItemModel())
        .register();

    public static final BlockEntry<CoolerBlock> COOLER = REGISTRATE.block("cooler", CoolerBlock::new)
        .initialProperties(SharedProperties::stone)
        .properties(p -> p
            .mapColor(MapColor.COLOR_GRAY)
            .noOcclusion()
            .sound(DestroySoundTypes.COOLER)
        ).transform(TagGen.pickaxeOnly())
        .item()
        .transform(customItemModel())
        .register();

    public static final BlockEntry<DoubleCardanShaftBlock> DOUBLE_CARDAN_SHAFT = REGISTRATE.block("double_cardan_shaft", DoubleCardanShaftBlock::new)
        .initialProperties(AllBlocks.SHAFT)
        .properties(p -> p
            .mapColor(MapColor.METAL)
            .noOcclusion()
        ).transform(TagGen.pickaxeOnly())
        .item()
        .transform(customItemModel())
        .register();

    public static final BlockEntry<DifferentialBlock> DIFFERENTIAL = REGISTRATE.block("differential", DifferentialBlock::new)
        .initialProperties(AllBlocks.LARGE_COGWHEEL)
        .properties(p -> p
            .noOcclusion()
            .sound(SoundType.WOOD)
		    .mapColor(MapColor.DIRT)
        ).transform(TagGen.axeOrPickaxe())
        .item(CogwheelBlockItem::new)
        .transform(customItemModel())
        .register();

    public static final BlockEntry<DummyDifferentialBlock> DUMMY_DIFFERENTIAL = REGISTRATE.block("dummy_differential", DummyDifferentialBlock::new)
        .initialProperties(DIFFERENTIAL)
        .register();

    public static final BlockEntry<DynamoBlock> DYNAMO = REGISTRATE.block("dynamo", DynamoBlock::new)
        .initialProperties(SharedProperties::softMetal)
        .properties(p -> p
            .mapColor(MapColor.GOLD)
            .noOcclusion()
        ).transform(TagGen.pickaxeOnly())
        .transform(BlockStressDefaults.setImpact(6.0))
        .item(AssemblyOperatorBlockItem::new)
        .tag(DestroyItemTags.LIABLE_TO_CHANGE.tag)
        .transform(customItemModel())
        .register();

    public static final BlockEntry<ExtrusionDieBlock> EXTRUSION_DIE = REGISTRATE.block("extrusion_die", ExtrusionDieBlock::new)
        .initialProperties(SharedProperties::softMetal)
        .properties(p -> p
            .noCollission()
        ).item()
        .transform(customItemModel())
        .register();

    public static final BlockEntry<KeypunchBlock> KEYPUNCH = REGISTRATE.block("keypunch", KeypunchBlock::new)
        .initialProperties(SharedProperties::softMetal)
        .properties(p -> p
            .noOcclusion()
        ).item(AssemblyOperatorBlockItem::new)
        .transform(customItemModel())
        .register();

    public static final BlockEntry<LongShaftBlock> LONG_SHAFT = REGISTRATE.block("long_shaft", LongShaftBlock::new)
        .initialProperties(AllBlocks.SHAFT)
        .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
        .register();

    public static final BlockEntry<RedstoneProgrammerBlock> REDSTONE_PROGRAMMER = REGISTRATE.block("redstone_programmer", RedstoneProgrammerBlock::new)
        .initialProperties(SharedProperties::wooden)
        .properties(p -> p
            .noOcclusion()
            .noLootTable() // Handled in RedstoneProgrammerBlock class
        ).item(RedstoneProgrammerBlockItem::new)
        .build()
        .register();

    public static final BlockEntry<PlanetaryGearsetBlock> PLANETARY_GEARSET = REGISTRATE.block("planetary_gearset", PlanetaryGearsetBlock::new)
        .initialProperties(AllBlocks.LARGE_COGWHEEL)
        .properties(p -> p
            .noOcclusion()
            .sound(SoundType.WOOD)
		    .mapColor(MapColor.DIRT)
        ).transform(TagGen.axeOrPickaxe())
        .item(CogwheelBlockItem::new)
        .transform(customItemModel())
        .register();

    public static final BlockEntry<PollutometerBlock> POLLUTOMETER = REGISTRATE.block("pollutometer", PollutometerBlock::new)
        .initialProperties(SharedProperties::stone)
        .properties(p -> p
            .mapColor(MapColor.NONE)
            .noOcclusion()
        ).onRegister(AllDisplayBehaviours.assignDataBehaviour(new PollutometerDisplaySource(), "pollutometer"))
        .transform(TagGen.pickaxeOnly())
        .item()
        .transform(customItemModel())
        .register();

    public static final BlockEntry<PumpjackBlock> PUMPJACK = REGISTRATE.block("pumpjack", PumpjackBlock::new)
        .initialProperties(SharedProperties::copperMetal)
        .properties(p -> p
            .mapColor(MapColor.COLOR_ORANGE)
            .noOcclusion()
            .isSuffocating((state, level, pos) -> false)
        ).transform(TagGen.pickaxeOnly())
        .transform(BlockStressDefaults.setImpact(8.0))
        .item(PumpjackBlockItem::new)
        .transform(customItemModel())
        .register();

    public static final BlockEntry<PumpjackCamBlock> PUMPJACK_CAM = REGISTRATE.block("pumpjack_cam", PumpjackCamBlock::new)
        .initialProperties(SharedProperties::copperMetal)
        .properties(p -> p
            .mapColor(MapColor.COLOR_ORANGE)
            .noOcclusion()
            .isSuffocating((state, level, pos) -> false)
        ).transform(TagGen.pickaxeOnly())
        .blockstate(BlockStateGen.horizontalAxisBlockProvider(false))
        .register();

    public static final BlockEntry<PumpjackStructuralBlock> PUMPJACK_STRUCTURAL = REGISTRATE.block("pumpjack_structure", PumpjackStructuralBlock::new)
        .initialProperties(SharedProperties::copperMetal)
        .properties(p -> p
            .mapColor(MapColor.COLOR_ORANGE)
            .noOcclusion()
            .isSuffocating((state, level, pos) -> false)
        ).transform(TagGen.pickaxeOnly())
        .blockstate((c, p) -> p.getVariantBuilder(c.get())
            .forAllStatesExcept(BlockStateGen.mapToAir(p), PumpjackStructuralBlock.FACING)
        ).register();

    public static final BlockEntry<SandCastleBlock> SAND_CASTLE = REGISTRATE.block("sand_castle", SandCastleBlock::new)
        .initialProperties(() -> Blocks.POPPY)
        .properties(p -> p
            .mapColor(MapColor.SAND)
            .noOcclusion()
            .noLootTable()
            .instabreak()
            .sound(SoundType.SAND)
        ).register();

    public static final BlockEntry<TreeTapBlock> TREE_TAP = REGISTRATE.block("tree_tap", TreeTapBlock::new)
        .initialProperties(AllBlocks.DEPLOYER)
        .item()
        .build()
        .register();

    public static final BlockEntry<VatControllerBlock> VAT_CONTROLLER = REGISTRATE.block("vat_controller", VatControllerBlock::new)
        .initialProperties(SharedProperties::copperMetal)
        .properties(p -> p
            .noOcclusion()
        ).onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(AllSpriteShifts.COPPER_CASING)))
        .onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, AllSpriteShifts.COPPER_CASING,
			(s, f) -> f != s.getValue(VatControllerBlock.FACING)))
        ).onRegister(AllDisplayBehaviours.assignDataBehaviour(VatControllerBlockEntity.ALL_DISPLAY_SOURCE, "vat_controller_all_contents"))
        .onRegister(AllDisplayBehaviours.assignDataBehaviour(VatControllerBlockEntity.SOLUTION_DISPLAY_SOURCE, "vat_controller_solution_contents"))
        .onRegister(AllDisplayBehaviours.assignDataBehaviour(VatControllerBlockEntity.GAS_DISPLAY_SOURCE, "vat_controller_gas_contents"))
        .item()
        .build()
        .register();

    public static final BlockEntry<VatSideBlock> VAT_SIDE = REGISTRATE.block("vat_side", VatSideBlock::new)
        .transform(BuilderTransformers.copycat())
        .onRegister(CreateRegistrate.blockModel(() -> CopycatBlockModel::new))
        .onRegister(AllDisplayBehaviours.assignDataBehaviour(VatControllerBlockEntity.ALL_DISPLAY_SOURCE, "vat_side_all_contents"))
        .onRegister(AllDisplayBehaviours.assignDataBehaviour(VatControllerBlockEntity.SOLUTION_DISPLAY_SOURCE, "vat_side_solution_contents"))
        .onRegister(AllDisplayBehaviours.assignDataBehaviour(VatControllerBlockEntity.GAS_DISPLAY_SOURCE, "vat_side_gas_contents"))
        .register();

    public static final BlockEntry<UrineCauldronBlock> URINE_CAULDRON = REGISTRATE.block("urine_cauldron", p -> new UrineCauldronBlock(p, DestroyCauldronInteractions.URINE))
        .initialProperties(() -> Blocks.WATER_CAULDRON)
        .tag(BlockTags.CAULDRONS)
        .register();

    public static final BlockEntry<BlacklightBlock> BLACKLIGHT = REGISTRATE.block("blacklight", BlacklightBlock::new)
        .initialProperties(() -> Blocks.LANTERN)
        .properties(p -> p
            .mapColor(MapColor.COLOR_PURPLE)
            .sound(SoundType.GLASS)
            .forceSolidOn()
        ).item()
        .build()
        .register();

    // EXPLOSIVES

    public static final BlockEntry<PrimeableBombBlock> ANFO_BLOCK = REGISTRATE.block("anfo_block", p -> new PrimeableBombBlock(p, PrimedBomb.Anfo::new))
        .initialProperties(() -> Blocks.TNT)
        .properties(p -> p
            .mapColor(MapColor.COLOR_PINK)
        ).item()
        .tag(DestroyItemTags.LIABLE_TO_CHANGE.tag)
        .build()
        .register();

    public static final BlockEntry<PrimeableBombBlock> CORDITE = REGISTRATE.block("cordite", p -> new PrimeableBombBlock(p, PrimedBomb.Cordite::new))
        .initialProperties(() -> Blocks.TNT)
        .properties(p -> p
            .mapColor(MapColor.COLOR_ORANGE)
        ).item()
        .tag(DestroyItemTags.LIABLE_TO_CHANGE.tag)
        .build()
        .register();

    public static final BlockEntry<DynamiteBlock> DYNAMITE_BLOCK = REGISTRATE.block("dynamite_block", DynamiteBlock::new)
        .initialProperties(() -> Blocks.TNT)
        .properties(p -> p
            .mapColor(MapColor.COLOR_MAGENTA)
        ).item()
        .build()
        .register();

    public static final BlockEntry<PrimeableBombBlock> NITROCELLULOSE_BLOCK = REGISTRATE.block("nitrocellulose_block", p -> new PrimeableBombBlock(p, PrimedBomb.Nitrocellulose::new))
        .initialProperties(() -> Blocks.TNT)
        .properties(p -> p
            .mapColor(MapColor.COLOR_LIGHT_GREEN)
        ).item()
        .tag(DestroyItemTags.LIABLE_TO_CHANGE.tag)
        .tag(DestroyItemTags.OBLITERATION_EXPLOSIVE.tag)
        .build()
        .register();

    public static final BlockEntry<PrimeableBombBlock> PICRIC_ACID_BLOCK = REGISTRATE.block("picric_acid_block", (p) -> new PrimeableBombBlock(p, PrimedBomb.PicricAcid::new))
        .initialProperties(() -> Blocks.TNT)
        .properties(p -> p
            .mapColor(MapColor.COLOR_YELLOW)
        ).item()
        .tag(DestroyItemTags.LIABLE_TO_CHANGE.tag)
        .build()
        .register();

    // STORAGE BLOCKS

    public static final BlockEntry<Block> FLUORITE_BLOCK = REGISTRATE.block("fluorite_block", Block::new)
        .initialProperties(() -> Blocks.IRON_BLOCK)
        .properties(p -> p
            .mapColor(MapColor.COLOR_PURPLE)
            .requiresCorrectToolForDrops()
            .strength(6f, 6f)
        ).transform(TagGen.pickaxeOnly())
        .tag(BlockTags.NEEDS_IRON_TOOL)
        .tag(Tags.Blocks.STORAGE_BLOCKS)
        .tag(BlockTags.BEACON_BASE_BLOCKS)
        .transform(TagGen.tagBlockAndItem("storage_blocks/fluorite"))
        .tag(Tags.Items.STORAGE_BLOCKS)
        .build()
        .register();

    public static final BlockEntry<Block> RAW_NICKEL_BLOCK = REGISTRATE.block("raw_nickel_block", Block::new)
        .initialProperties(() -> Blocks.RAW_IRON_BLOCK)
        .properties(p -> p
            .mapColor(MapColor.SAND)
            .requiresCorrectToolForDrops()
            .strength(5f, 6f)
        ).transform(TagGen.pickaxeOnly())
        .tag(BlockTags.NEEDS_STONE_TOOL)
        .tag(Tags.Blocks.STORAGE_BLOCKS)
        .tag(BlockTags.BEACON_BASE_BLOCKS)
        .transform(TagGen.tagBlockAndItem("storage_blocks/raw_nickel"))
        .tag(Tags.Items.STORAGE_BLOCKS)
        .build()
        .register();

    public static final BlockEntry<Block> CHROMIUM_BLOCK = REGISTRATE.block("chromium_block", Block::new)
        .initialProperties(() -> Blocks.IRON_BLOCK)
        .properties(p -> p
            .requiresCorrectToolForDrops()
            .strength(5f, 6f)
        ).transform(TagGen.pickaxeOnly())
        .tag(BlockTags.NEEDS_STONE_TOOL)
        .tag(Tags.Blocks.STORAGE_BLOCKS)
        .tag(BlockTags.BEACON_BASE_BLOCKS)
        .transform(TagGen.tagBlockAndItem("storage_blocks/chromium"))
        .tag(Tags.Items.STORAGE_BLOCKS)
        .build()
        .register();

    public static final BlockEntry<Block> NICKEL_BLOCK = REGISTRATE.block("nickel_block", Block::new)
        .initialProperties(() -> Blocks.IRON_BLOCK)
        .properties(p -> p
            .mapColor(MapColor.SAND)
            .requiresCorrectToolForDrops()
            .strength(5f, 6f)
        ).transform(TagGen.pickaxeOnly())
        .tag(BlockTags.NEEDS_STONE_TOOL)
        .tag(Tags.Blocks.STORAGE_BLOCKS)
        .tag(BlockTags.BEACON_BASE_BLOCKS)
        .transform(TagGen.tagBlockAndItem("storage_blocks/nickel"))
        .tag(Tags.Items.STORAGE_BLOCKS)
        .build()
        .register();

    public static final BlockEntry<Block> PALLADIUM_BLOCK = REGISTRATE.block("palladium_block", Block::new)
        .initialProperties(() -> Blocks.NETHERITE_BLOCK)
        .properties(p -> p
            .mapColor(MapColor.DIRT)
            .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
            .requiresCorrectToolForDrops()
            .strength(6f, 6f)
        ).transform(TagGen.pickaxeOnly())
        .tag(BlockTags.NEEDS_DIAMOND_TOOL)
        .tag(Tags.Blocks.STORAGE_BLOCKS)
        .tag(BlockTags.BEACON_BASE_BLOCKS)
        .transform(TagGen.tagBlockAndItem("storage_blocks/palladium"))
        .tag(Tags.Items.STORAGE_BLOCKS)
        .build()
        .register();

    public static final BlockEntry<Block> PLATINUM_BLOCK = REGISTRATE.block("platinum_block", Block::new)
        .initialProperties(() -> Blocks.DIAMOND_BLOCK)
        .properties(p -> p
            .requiresCorrectToolForDrops()
            .instrument(NoteBlockInstrument.BELL)
            .strength(6f, 6f)
        ).transform(TagGen.pickaxeOnly())
        .tag(BlockTags.NEEDS_IRON_TOOL)
        .tag(Tags.Blocks.STORAGE_BLOCKS)
        .tag(BlockTags.BEACON_BASE_BLOCKS)
        .transform(TagGen.tagBlockAndItem("storage_blocks/platinum"))
        .tag(Tags.Items.STORAGE_BLOCKS)
        .build()
        .register();

    public static final BlockEntry<Block> RHODIUM_BLOCK = REGISTRATE.block("rhodium_block", Block::new)
        .initialProperties(() -> Blocks.NETHERITE_BLOCK)
        .properties(p -> p
            .mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)
            .instrument(NoteBlockInstrument.BELL)
            .requiresCorrectToolForDrops()
            .strength(6f, 6f)
        ).transform(TagGen.pickaxeOnly())
        .tag(BlockTags.NEEDS_DIAMOND_TOOL)
        .tag(Tags.Blocks.STORAGE_BLOCKS)
        .tag(BlockTags.BEACON_BASE_BLOCKS)
        .transform(TagGen.tagBlockAndItem("storage_blocks/rhodium"))
        .tag(Tags.Items.STORAGE_BLOCKS)
        .build()
        .register();

    public static final BlockEntry<Block> LEAD_BLOCK = REGISTRATE.block("lead_block", Block::new)
        .initialProperties(() -> Blocks.IRON_BLOCK)
        .properties(p -> p
            .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
            .requiresCorrectToolForDrops()
            .strength(7f, 6f)
        ).transform(TagGen.pickaxeOnly())
        .tag(BlockTags.NEEDS_STONE_TOOL)
        .tag(Tags.Blocks.STORAGE_BLOCKS)
        .tag(BlockTags.BEACON_BASE_BLOCKS)
        .transform(TagGen.tagBlockAndItem("storage_blocks/lead"))
        .tag(Tags.Items.STORAGE_BLOCKS)
        .build()
        .register();

    public static final BlockEntry<Block> STAINLESS_STEEL_BLOCK = REGISTRATE.block("stainless_steel_block", Block::new)
        .initialProperties(() -> Blocks.IRON_BLOCK)
        .properties(p -> p
            .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
            .strength(7f, 8f)
        ).onRegister(CreateRegistrate.connectedTextures(() -> new SimpleCTBehaviour(DestroySpriteShifts.STAINLESS_STEEL_BLOCK)))
        .transform(TagGen.pickaxeOnly())
        .tag(BlockTags.NEEDS_STONE_TOOL)
        .tag(Tags.Blocks.STORAGE_BLOCKS)
        .tag(BlockTags.BEACON_BASE_BLOCKS)
        .tag(AllBlockTags.MOVABLE_EMPTY_COLLIDER.tag)
        .transform(TagGen.tagBlockAndItem("storage_blocks/stainless_steel"))
        .tag(Tags.Items.STORAGE_BLOCKS)
        .build()
        .register();

    // public static final BlockEntry<Block> ZIRCONIUM_BLOCK = REGISTRATE.block("zirconium_block", Block::new)
    //     .initialProperties(() -> Blocks.NETHERITE_BLOCK)
    //     .properties(p -> p
    //         .mapColor(MapColor.STONE)
    //         .requiresCorrectToolForDrops()
    //         .strength(6f, 6f)
    //     ).transform(TagGen.pickaxeOnly())
    //     .tag(BlockTags.NEEDS_DIAMOND_TOOL)
    //     .tag(Tags.Blocks.STORAGE_BLOCKS)
    //     .tag(BlockTags.BEACON_BASE_BLOCKS)
    //     .transform(TagGen.tagBlockAndItem("storage_blocks/zirconium"))
    //     .tag(DestroyItemTags.LIABLE_TO_CHANGE.tag)
    //     .tag(Tags.Items.STORAGE_BLOCKS)
    //     .build()
    //     .register();

    // ORES

    public static final BlockEntry<Block> FLUORITE_ORE = REGISTRATE.block("fluorite_ore", Block::new)
        .initialProperties(() -> Blocks.GOLD_ORE)
        .properties(p -> p
            .mapColor(MapColor.COLOR_PURPLE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(3f, 3f)
        ).transform(TagGen.pickaxeOnly())
        .loot((lt, b) -> lt.add(b, RegistrateBlockLootTables.createSilkTouchDispatchTable(b, lt.applyExplosionDecay(b, LootItem.lootTableItem(DestroyItems.FLUORITE.get()).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))))))
        .tag(BlockTags.NEEDS_IRON_TOOL)
        .tag(Tags.Blocks.ORES)
        .transform(TagGen.tagBlockAndItem("ores/fluorite", "ores_in_ground/stone"))
        .tag(Tags.Items.ORES)
        .build()
        .register();

    public static final BlockEntry<Block> DEEPSLATE_FLUORITE_ORE = REGISTRATE.block("deepslate_fluorite_ore", Block::new)
        .initialProperties(() -> Blocks.DEEPSLATE_GOLD_ORE)
        .properties(p -> p
            .mapColor(MapColor.COLOR_PURPLE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .sound(SoundType.DEEPSLATE)
            .strength(4.5f, 3f)
        ).transform(TagGen.pickaxeOnly())
        .loot((lt, b) -> lt.add(b, RegistrateBlockLootTables.createSilkTouchDispatchTable(b, lt.applyExplosionDecay(b, LootItem.lootTableItem(DestroyItems.FLUORITE.get()).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))))))
        .tag(BlockTags.NEEDS_IRON_TOOL)
        .tag(Tags.Blocks.ORES)
        .transform(TagGen.tagBlockAndItem("ores/fluorite", "ores_in_ground/deepslate"))
        .tag(Tags.Items.ORES)
        .build()
        .register();

    public static final BlockEntry<Block> END_FLUORITE_ORE = REGISTRATE.block("end_fluorite_ore", Block::new)
        .initialProperties(() -> Blocks.END_STONE)
        .properties(p -> p
            .mapColor(MapColor.COLOR_PURPLE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(4f, 9f)
        ).transform(TagGen.pickaxeOnly())
        .loot((lt, b) -> lt.add(b, RegistrateBlockLootTables.createSilkTouchDispatchTable(b, lt.applyExplosionDecay(b, LootItem.lootTableItem(DestroyItems.FLUORITE.get()).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))))))
        .tag(BlockTags.NEEDS_IRON_TOOL)
        .tag(Tags.Blocks.ORES)
        .transform(TagGen.tagBlockAndItem("ores/fluorite", "ores_in_ground/end_stone"))
        .tag(Tags.Items.ORES)
        .build()
        .register();

    public static final BlockEntry<Block> NICKEL_ORE = REGISTRATE.block("nickel_ore", Block::new)
        .initialProperties(() -> Blocks.GOLD_ORE)
        .properties(p -> p
            .mapColor(MapColor.SAND)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(3f, 3f)
        ).transform(TagGen.pickaxeOnly())
        .loot((lt, b) -> lt.add(b, RegistrateBlockLootTables.createSilkTouchDispatchTable(b, lt.applyExplosionDecay(b, LootItem.lootTableItem(DestroyItems.RAW_NICKEL.get()).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))))))
        .tag(BlockTags.NEEDS_IRON_TOOL)
        .tag(Tags.Blocks.ORES)
        .transform(TagGen.tagBlockAndItem("ores/nickel", "ores_in_ground/stone"))
        .tag(Tags.Items.ORES)
        .build()
        .register();

    public static final BlockEntry<Block> DEEPSLATE_NICKEL_ORE = REGISTRATE.block("deepslate_nickel_ore", Block::new)
        .initialProperties(() -> Blocks.DEEPSLATE_GOLD_ORE)
        .properties(p -> p
            .mapColor(MapColor.SAND)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .sound(SoundType.DEEPSLATE)
            .strength(4.5f, 3f)
        ).transform(TagGen.pickaxeOnly())
        .loot((lt, b) -> lt.add(b, RegistrateBlockLootTables.createSilkTouchDispatchTable(b, lt.applyExplosionDecay(b, LootItem.lootTableItem(DestroyItems.RAW_NICKEL.get()).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))))))
        .tag(BlockTags.NEEDS_IRON_TOOL)
        .tag(Tags.Blocks.ORES)
        .transform(TagGen.tagBlockAndItem("ores/nickel", "ores_in_ground/deepslate"))
        .tag(Tags.Items.ORES)
        .build()
        .register();

    public static final BlockEntry<Block> NETHER_CROCOITE_BLOCK = REGISTRATE.block("nether_crocoite_block", Block::new)
        .initialProperties(() -> Blocks.NETHER_QUARTZ_ORE)
        .properties(p -> p
            .mapColor(MapColor.COLOR_ORANGE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .sound(SoundType.NETHERRACK)
            .requiresCorrectToolForDrops()
        ).onRegister(CreateRegistrate.connectedTextures(() -> new SimpleCTBehaviour(DestroySpriteShifts.NETHER_CROCOITE_BLOCK)))
        .transform(TagGen.pickaxeOnly())
        .loot((lt, b) -> lt.add(b, RegistrateBlockLootTables.createSilkTouchDispatchTable(b, lt.applyExplosionDecay(b, LootItem.lootTableItem(DestroyItems.NETHER_CROCOITE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 5.0f))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))))))
        .tag(BlockTags.NEEDS_IRON_TOOL)
        .tag(Tags.Blocks.ORES)
        .item()
        .tag(Tags.Items.ORES)
        .build()
        .register();

    // CROPS

    @SuppressWarnings("removal")
    public static final BlockEntry<MagicBeetrootShootsBlock>

    MAGIC_BEETROOT_SHOOTS = REGISTRATE.block("magic_beetroot_shoots", MagicBeetrootShootsBlock::new)
        .addLayer(() -> RenderType::cutout)
        .initialProperties(() -> Blocks.BEETROOTS)
        .register();

    public static final BlockEntry<FullyGrownCropBlock>

    GOLDEN_CARROTS = REGISTRATE.block("golden_carrots", p -> new FullyGrownCropBlock(p, () -> Items.GOLDEN_CARROT))
        .initialProperties(() -> Blocks.CARROTS)
        .tag(BlockTags.CROPS)
        .register();

    public static final BlockEntry<HeftyBeetrootBlock> 

    HEFTY_BEETROOT = REGISTRATE.block("hefty_beetroot", p -> new HeftyBeetrootBlock(p, DestroyItems.HEFTY_BEETROOT))
        .initialProperties(() -> Blocks.BEETROOTS)
        .tag(DestroyBlockTags.BEETROOTS.tag)
        .register(),

    COAL_INFUSED_BEETROOT = REGISTRATE.block("coal_infused_beetroot", p -> new HeftyBeetrootBlock(p, DestroyItems.COAL_INFUSED_BEETROOT))
        .initialProperties(HEFTY_BEETROOT)
        .tag(DestroyBlockTags.BEETROOTS.tag)
        .register(),

    COPPER_INFUSED_BEETROOT = REGISTRATE.block("copper_infused_beetroot", p -> new HeftyBeetrootBlock(p, DestroyItems.COPPER_INFUSED_BEETROOT))
        .initialProperties(HEFTY_BEETROOT)
        .tag(DestroyBlockTags.BEETROOTS.tag)
        .register(),

    DIAMOND_INFUSED_BEETROOT = REGISTRATE.block("diamond_infused_beetroot", p -> new HeftyBeetrootBlock(p, DestroyItems.DIAMOND_INFUSED_BEETROOT))
        .initialProperties(HEFTY_BEETROOT)
        .tag(DestroyBlockTags.BEETROOTS.tag)
        .register(),

    EMERALD_INFUSED_BEETROOT = REGISTRATE.block("emerald_infused_beetroot", p -> new HeftyBeetrootBlock(p, DestroyItems.EMERALD_INFUSED_BEETROOT))
        .initialProperties(HEFTY_BEETROOT)
        .tag(DestroyBlockTags.BEETROOTS.tag)
        .register(),

    FLUORITE_INFUSED_BEETROOT = REGISTRATE.block("fluorite_infused_beetroot", p -> new HeftyBeetrootBlock(p, DestroyItems.FLUORITE_INFUSED_BEETROOT))
        .initialProperties(HEFTY_BEETROOT)
        .tag(DestroyBlockTags.BEETROOTS.tag)
        .register(),

    GOLD_INFUSED_BEETROOT = REGISTRATE.block("gold_infused_beetroot", p -> new HeftyBeetrootBlock(p, DestroyItems.GOLD_INFUSED_BEETROOT))
        .initialProperties(HEFTY_BEETROOT)
        .tag(DestroyBlockTags.BEETROOTS.tag)
        .register(),

    IRON_INFUSED_BEETROOT = REGISTRATE.block("iron_infused_beetroot", p -> new HeftyBeetrootBlock(p, DestroyItems.IRON_INFUSED_BEETROOT))
        .initialProperties(HEFTY_BEETROOT)
        .tag(DestroyBlockTags.BEETROOTS.tag)
        .register(),

    LAPIS_INFUSED_BEETROOT = REGISTRATE.block("lapis_infused_beetroot", p -> new HeftyBeetrootBlock(p, DestroyItems.LAPIS_INFUSED_BEETROOT))
        .initialProperties(HEFTY_BEETROOT)
        .tag(DestroyBlockTags.BEETROOTS.tag)
        .register(),

    NICKEL_INFUSED_BEETROOT = REGISTRATE.block("nickel_infused_beetroot", p -> new HeftyBeetrootBlock(p, DestroyItems.NICKEL_INFUSED_BEETROOT))
        .initialProperties(HEFTY_BEETROOT)
        .tag(DestroyBlockTags.BEETROOTS.tag)
        .register(),

    REDSTONE_INFUSED_BEETROOT = REGISTRATE.block("redstone_infused_beetroot", p -> new HeftyBeetrootBlock(p, DestroyItems.REDSTONE_INFUSED_BEETROOT))
        .initialProperties(HEFTY_BEETROOT)
        .tag(DestroyBlockTags.BEETROOTS.tag)
        .register(),

    ZINC_INFUSED_BEETROOT = REGISTRATE.block("zinc_infused_beetroot", p -> new HeftyBeetrootBlock(p, DestroyItems.ZINC_INFUSED_BEETROOT))
        .initialProperties(HEFTY_BEETROOT)
        .tag(DestroyBlockTags.BEETROOTS.tag)
        .register();

    // Periodic Table blocks

    public static final BlockEntry<PeriodicTableBlock>

    CHROMIUM_PERIODIC_TABLE_BLOCK = REGISTRATE.block("chromium_periodic_table_block", PeriodicTableBlock::new)
        .initialProperties(CHROMIUM_BLOCK)
        .tag(Tags.Blocks.STORAGE_BLOCKS, AllTags.forgeBlockTag("storage_blocks/chromium"))
        .item(PeriodicTableBlockItem::new)
        .tag(Tags.Items.STORAGE_BLOCKS, forgeItemTag("storage_blocks/chromium"))
        .build()
        .register(),

    IRON_PERIODIC_TABLE_BLOCK = REGISTRATE.block("iron_periodic_table_block", PeriodicTableBlock::new)
        .initialProperties(() -> Blocks.IRON_BLOCK)
        .tag(Tags.Blocks.STORAGE_BLOCKS, Tags.Blocks.STORAGE_BLOCKS_IRON)
        .item(PeriodicTableBlockItem::new)
        .tag(Tags.Items.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS_IRON)
        .build()
        .register(),

    NICKEL_PERIODIC_TABLE_BLOCK = REGISTRATE.block("nickel_periodic_table_block", PeriodicTableBlock::new)
        .initialProperties(NICKEL_BLOCK)
        .tag(Tags.Blocks.STORAGE_BLOCKS, AllTags.forgeBlockTag("storage_blocks/nickel"))
        .item(PeriodicTableBlockItem::new)
        .tag(Tags.Items.STORAGE_BLOCKS, forgeItemTag("storage_blocks/nickel"))
        .build()
        .register(),

    COPPER_PERIODIC_TABLE_BLOCK = REGISTRATE.block("copper_periodic_table_block", PeriodicTableBlock::new)
        .initialProperties(() -> Blocks.COPPER_BLOCK)
        .tag(Tags.Blocks.STORAGE_BLOCKS, Tags.Blocks.STORAGE_BLOCKS_COPPER)
        .item(PeriodicTableBlockItem::new)
        .tag(Tags.Items.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS_COPPER)
        .build()
        .register(),

    ZINC_PERIODIC_TABLE_BLOCK = REGISTRATE.block("zinc_periodic_table_block", PeriodicTableBlock::new)
        .initialProperties(AllBlocks.ZINC_BLOCK)
        .tag(Tags.Blocks.STORAGE_BLOCKS, AllTags.forgeBlockTag("storage_blocks/zinc"))
        .item(PeriodicTableBlockItem::new)
        .tag(Tags.Items.STORAGE_BLOCKS, forgeItemTag("storage_blocks/zinc"))
        .build()
        .register(),

    RHODIUM_PERIODIC_TABLE_BLOCK = REGISTRATE.block("rhodium_periodic_table_block", PeriodicTableBlock::new)
        .initialProperties(RHODIUM_BLOCK)
        .tag(Tags.Blocks.STORAGE_BLOCKS, AllTags.forgeBlockTag("storage_blocks/rhodium"))
        .item(PeriodicTableBlockItem::new)
        .tag(Tags.Items.STORAGE_BLOCKS, forgeItemTag("storage_blocks/rhodium"))
        .build()
        .register(),

    PALLADIUM_PERIODIC_TABLE_BLOCK = REGISTRATE.block("palladium_periodic_table_block", PeriodicTableBlock::new)
        .initialProperties(PALLADIUM_BLOCK)
        .tag(Tags.Blocks.STORAGE_BLOCKS, AllTags.forgeBlockTag("storage_blocks/palladium"))
        .item(PeriodicTableBlockItem::new)
        .tag(Tags.Items.STORAGE_BLOCKS, forgeItemTag("storage_blocks/palladium"))
        .build()
        .register(),

    PLATINUM_PERIODIC_TABLE_BLOCK = REGISTRATE.block("platinum_periodic_table_block", PeriodicTableBlock::new)
        .initialProperties(CHROMIUM_BLOCK)
        .tag(Tags.Blocks.STORAGE_BLOCKS, AllTags.forgeBlockTag("storage_blocks/platinum"))
        .item(PeriodicTableBlockItem::new)
        .tag(Tags.Items.STORAGE_BLOCKS, forgeItemTag("storage_blocks/platinum"))
        .build()
        .register(),

    GOLD_PERIODIC_TABLE_BLOCK = REGISTRATE.block("gold_periodic_table_block", PeriodicTableBlock::new)
        .initialProperties(() -> Blocks.GOLD_BLOCK)
        .tag(Tags.Blocks.STORAGE_BLOCKS, Tags.Blocks.STORAGE_BLOCKS_GOLD)
        .item(PeriodicTableBlockItem::new)
        .tag(Tags.Items.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS_GOLD)
        .build()
        .register(),

    LEAD_PERIODIC_TABLE_BLOCK = REGISTRATE.block("lead_periodic_table_block", PeriodicTableBlock::new)
        .initialProperties(CHROMIUM_BLOCK)
        .tag(Tags.Blocks.STORAGE_BLOCKS, AllTags.forgeBlockTag("storage_blocks/lead"))
        .item(PeriodicTableBlockItem::new)
        .tag(Tags.Items.STORAGE_BLOCKS, forgeItemTag("storage_blocks/lead"))
        .build()
        .register();

    // FOOD

    public static final BlockEntry<Block> MASHED_POTATO_BLOCK = REGISTRATE.block("mashed_potato_block", Block::new)
        .initialProperties(() -> Blocks.CLAY)
        .properties(p -> p
            .mapColor(MapColor.COLOR_YELLOW)
            .sound(SoundType.SLIME_BLOCK)
            .strength(0.2f)
        ).tag(BlockTags.MINEABLE_WITH_SHOVEL)
        .item()
        .build()
        .register();

    public static final BlockEntry<RotatedPillarBlock> RAW_FRIES_BLOCK = REGISTRATE.block("raw_fries_block", RotatedPillarBlock::new)
        .initialProperties(() -> Blocks.CLAY)
        .properties(p -> p
            .mapColor(MapColor.COLOR_YELLOW)
            .sound(SoundType.SLIME_BLOCK)
            .strength(0.2f)
        ).loot((lt, b) -> lt.add(b, RegistrateBlockLootTables.createSilkTouchDispatchTable(b, LootItem.lootTableItem(DestroyItems.RAW_FRIES).apply(SetItemCountFunction.setCount(ConstantValue.exactly(5f))))))
        .tag(BlockTags.MINEABLE_WITH_SHOVEL)
        .item()
        .build()
        .register();

    // UNCATEGORISED

    public static final BlockEntry<FlippableRotatedPillarBlock>
    
    PLYWOOD = REGISTRATE.block("plywood", FlippableRotatedPillarBlock::new)
        .properties(p -> p
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.BASS)
            .strength(4.0f, 6.0f)
        ).tag(BlockTags.MINEABLE_WITH_AXE, BlockTags.PLANKS)
        .item()
        .tag(ItemTags.PLANKS)
        .build()
        .register(),

    UNVARNISHED_PLYWOOD = REGISTRATE.block("unvarnished_plywood", FlippableRotatedPillarBlock::new)
        .properties(p -> p
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.BASS)
            .strength(3.0f, 5.0f)
            .ignitedByLava()
        ).tag(BlockTags.MINEABLE_WITH_AXE, BlockTags.PLANKS)
        .item(CombustibleBlockItem::new)
        .tag(ItemTags.PLANKS)
        .onRegister(i -> i.setBurnTime(20000))
        .build()
        .register();

    public static final BlockEntry<MoltenStainlessSteelBlock> MOLTEN_STAINLESS_STEEL = REGISTRATE.block("molten_stainless_steel", MoltenStainlessSteelBlock::new)
        .properties(p -> p
            .mapColor(MapColor.COLOR_ORANGE)
            .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
            .lightLevel(state -> 15)
            .noLootTable()
            .dynamicShape()
        ).register();

    public static final BlockEntry<StainlessSteelRodsBlock> STAINLESS_STEEL_RODS = REGISTRATE.block("stainless_steel_rods_block", StainlessSteelRodsBlock::new)
        .initialProperties(STAINLESS_STEEL_BLOCK)
        .properties(p -> p
            .mapColor(state -> state.getValue(StainlessSteelRodsBlock.MOLTEN) ? MapColor.COLOR_ORANGE : MapColor.METAL)
            .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
            .lightLevel(state -> state.getValue(StainlessSteelRodsBlock.MOLTEN) ? 15 : 0)
        ).item()
        .build()
        .register();

    public static final BlockEntry<Block> CORDITE_BLOCK = REGISTRATE.block("cordite_block", Block::new)
        .initialProperties(() -> Blocks.CLAY)
        .properties(p -> p
            .mapColor(MapColor.COLOR_ORANGE)
            .sound(SoundType.SLIME_BLOCK)
            .strength(0.2f)
        ).tag(BlockTags.MINEABLE_WITH_SHOVEL)
        .tag(BlockTags.MINEABLE_WITH_HOE)
        .item()
        .build()
        .register();

    public static final BlockEntry<RotatedPillarBlock> EXTRUDED_CORDITE_BLOCK = REGISTRATE.block("extruded_cordite_block", RotatedPillarBlock::new)
        .initialProperties(() -> Blocks.CLAY)
        .properties(p -> p
            .mapColor(MapColor.COLOR_ORANGE)
            .sound(SoundType.SLIME_BLOCK)
            .strength(0.2f)
        ).loot((lt, b) -> lt.add(b, RegistrateBlockLootTables.createSilkTouchDispatchTable(b, LootItem.lootTableItem(DestroyItems.CORDITE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(5f))))))
        .tag(BlockTags.MINEABLE_WITH_SHOVEL)
        .tag(BlockTags.MINEABLE_WITH_HOE)
        .item()
        .build()
        .register();

    public static void register() {};
}