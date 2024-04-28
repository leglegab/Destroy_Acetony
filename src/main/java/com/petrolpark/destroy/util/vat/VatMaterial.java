package com.petrolpark.destroy.util.vat;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Iterator;

import com.petrolpark.destroy.block.DestroyBlocks;

import net.minecraft.world.level.block.Block;

/**
 * Information about a Block from which a Vat can be construced.
 * @param maxPressure The maxmimum pressure (in pascals) this Material can withstand before the Vat explodes
 * @param thermalConductivity The thermal conductivity (in watts per block-side-length-kelvin) of this Material
 * @param transparent Whether Blocks of this Material are permeable to sunlight and UV
 * @param builtin Whether this Vat Material must exist and was not added by a datapack
 */
public record VatMaterial(float maxPressure, float thermalConductivity, boolean transparent, boolean builtIn) {

    public static final Map<Block, VatMaterial> BLOCK_MATERIALS = new HashMap<>();

    public static final VatMaterial UNBREAKABLE = new VatMaterial(Float.MAX_VALUE, 0f, false, true);
    public static final VatMaterial GLASS = new VatMaterial(100000f, 15f, true, false);

    /**
     * Whether the given Block can be used to construct a Vat.
     * @param block
     */
    public static boolean isValid(Block block) {
        return BLOCK_MATERIALS.containsKey(block);
    };

    public static Optional<VatMaterial> getMaterial(Block block) {
        return Optional.ofNullable(BLOCK_MATERIALS.get(block));
    };

    public static void clearDatapackMaterials() {
        for (Iterator<Entry<Block, VatMaterial>> iterator = BLOCK_MATERIALS.entrySet().iterator(); iterator.hasNext();) {
            if (!iterator.next().getValue().builtIn()) iterator.remove();
        };
    };

    public static void registerDestroyVatMaterials() {

        BLOCK_MATERIALS.put(DestroyBlocks.VAT_CONTROLLER.get(), UNBREAKABLE);
    };
};
