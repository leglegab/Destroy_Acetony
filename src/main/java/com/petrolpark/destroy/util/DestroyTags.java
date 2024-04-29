package com.petrolpark.destroy.util;

import com.petrolpark.destroy.Destroy;
import com.simibubi.create.foundation.utility.Lang;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class DestroyTags {

    // Mostly all copied from Create source code

    public enum DestroyItemTags {

        ALCOHOLIC_DRINK,
        BEETROOT_ASHES,


        CHEMICAL_PROTECTION_EYES("chemical_protection/eyes"),
        CHEMICAL_PROTECTION_NOSE("chemical_protection/nose"),
        CHEMICAL_PROTECTION_MOUTH("chemical_protection/mouth"),
        CHEMICAL_PROTECTION_HEAD("chemical_protection/head"),
        CHEMICAL_PROTECTION_CHEST("chemical_protection/chest"),
        CHEMICAL_PROTECTION_LEGS("chemical_protection/legs"),
        CHEMICAL_PROTECTION_FEET("chemical_protection/feet"),
        CONTAMINABLE,

        DESTROY_INGOTS,
        FERTILIZER,
        HEFTY_BEETROOT,
        LIABLE_TO_CHANGE,
        PAPER_PULPABLE,
        PERIODIC_TABLE_BLOCK,

        PLASTIC,
        RIGID_PLASTIC("plastic/rigid"),
        TEXTILE_PLASTIC("plastic/textile"),
        POROUS_PLASTIC("plastic/porous"),
        INERT_PLASTIC("plastic/inert"),
        RUBBER_PLASTIC("plastic/rubber"),

        PRIMARY_EXPLOSIVE("explosive/primary"),
        SCHEMATICANNON_FUEL,
        SECONDARY_EXPLOSIVE("explosive/secondary"),
        OBLITERATION_EXPLOSIVE,  // This tag is only used to display the right Blocks in JEI.

        SPRAY_BOTTLE,
        SYRINGE,
        YEAST,
        ;

        public final TagKey<Item> tag;

        DestroyItemTags() {
            this(null);
        };

        DestroyItemTags(String path) {
			ResourceLocation id = Destroy.asResource(path == null ? Lang.asId(name()) : path);
			tag = ItemTags.create(id);
		};

        @SuppressWarnings("deprecation") // Create does it therefore so can I
        public boolean matches(Item item) {
            return item.builtInRegistryHolder().containsTag(tag);
        };

        public static void init() {};
    };

    public enum DestroyBlockTags {
        BEETROOTS,
        ACID_RAIN_DESTRUCTIBLE,
        ANFO_MINEABLE,
        ACID_RAIN_DIRT_REPLACEABLE;

        public final TagKey<Block> tag;

        DestroyBlockTags() {
            this(null);
        };

        DestroyBlockTags(String path) {
			ResourceLocation id = Destroy.asResource(path == null ? Lang.asId(name()) : path);
			tag = BlockTags.create(id);
		};

        @SuppressWarnings("deprecation") // Create does it therefore so can I
        public boolean matches(Block block) {
            return block.builtInRegistryHolder().containsTag(tag);
        };
    };

    public enum DestroyFluidTags {
        AMPLIFIES_SMOG,
        ACIDIFIES_RAIN,
        DEPLETES_OZONE,
        GREENHOUSE_GAS,
        RADIOACTIVE;

        public final TagKey<Fluid> tag;

        DestroyFluidTags() {
			tag = FluidTags.create(Destroy.asResource(Lang.asId(name())));
        };

        @SuppressWarnings("deprecation") // Create does it therefore so can I
        public boolean matches(Fluid fluid) {
            return fluid.builtInRegistryHolder().is(tag);
        };
    };

    public static void register() {
        DestroyItemTags.init();
    };
}
