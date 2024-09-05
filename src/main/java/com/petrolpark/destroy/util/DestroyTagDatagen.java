package com.petrolpark.destroy.util;

import com.petrolpark.compat.CompatMods;
import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.item.DestroyItems;
import com.petrolpark.destroy.util.DestroyTags.DestroyBlockTags;
import com.petrolpark.destroy.util.DestroyTags.DestroyItemTags;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags.AllItemTags;
import com.simibubi.create.foundation.data.TagGen.CreateTagsProvider;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class DestroyTagDatagen {

    public static void addGenerators() {
        Destroy.REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, DestroyTagDatagen::genItemTags);
        Destroy.REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, DestroyTagDatagen::genBlockTags);
    };

    @SuppressWarnings("deprecation")
    private static void genItemTags(RegistrateTagsProvider<Item> provIn) {
        CreateTagsProvider<Item> prov = new CreateTagsProvider<>(provIn, Item::builtInRegistryHolder);

        // Create Tags
        prov.tag(AllItemTags.UPRIGHT_ON_BELT.tag)
            .addTag(DestroyItemTags.SYRINGES.tag);

        // Curios Tags
        prov.tag(TagKey.create(Registries.ITEM, CompatMods.CURIOS.asResource("head")))
            .add(DestroyItems.LABORATORY_GOGGLES.get(), DestroyItems.GOLD_LABORATORY_GOGGLES.get(), DestroyItems.PAPER_MASK.get());

        // Destroy Tags
        prov.tag(DestroyItemTags.CHEMICAL_PROTECTION_EYES.tag)
            .add(AllItems.GOGGLES.get());
        prov.tag(DestroyItemTags.EYES.tag)
            .add(Items.SPIDER_EYE, Items.ENDER_EYE);
        prov.tag(DestroyItemTags.FERTILIZERS.tag)
            .add(Items.ROTTEN_FLESH, Items.ROOTED_DIRT, Items.BONE_MEAL);
        prov.tag(DestroyItemTags.SCHEMATICANNON_FUELS.tag)
            .add(Items.GUNPOWDER)
            .addOptional(CompatMods.BIG_CANNONS.asResource("packed_gunpowder"))
            .addTag(DestroyItemTags.PRIMARY_EXPLOSIVES.tag)
            .addTag(DestroyItemTags.SECONDARY_EXPLOSIVES.tag);
        prov.tag(DestroyItemTags.TEST_TUBE_RACK_STORABLE.tag)
            .addTag(DestroyItemTags.SYRINGES.tag);

        // Minecraft Tags
        prov.tag(ItemTags.TRIM_MATERIALS)
            .add(AllItems.ANDESITE_ALLOY.get(), AllItems.BRASS_INGOT.get(), AllItems.CHROMATIC_COMPOUND.get(), AllItems.SHADOW_STEEL.get(), AllItems.ZINC_INGOT.get())
            .add(DestroyItems.FLUORITE.get(), DestroyItems.NICKEL_INGOT.get(), DestroyItems.CHROMIUM_INGOT.get(), DestroyItems.LEAD_INGOT.get(), DestroyItems.PALLADIUM_INGOT.get(), DestroyItems.PLATINUM_INGOT.get(), DestroyItems.RHODIUM_INGOT.get(), DestroyItems.STAINLESS_STEEL_INGOT.get(), DestroyItems.PURE_GOLD_INGOT.get());
    };

    @SuppressWarnings("deprecation")
    private static void genBlockTags(RegistrateTagsProvider<Block> provIn) {
        CreateTagsProvider<Block> prov = new CreateTagsProvider<>(provIn, Block::builtInRegistryHolder);

        // Destroy Tags
        prov.tag(DestroyBlockTags.BEETROOTS.tag)
            .add(Blocks.BEETROOTS);

    };
};
