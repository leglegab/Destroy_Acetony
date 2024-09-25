package com.petrolpark.destroy.item;

import com.petrolpark.destroy.item.directional.IDirectionalOnBelt;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;

import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CircuitPatternItem extends Item implements IDirectionalOnBelt {

    /*
     *  y x0  1  2  3
     *  0  00 01 02 03
     *  1  04 05 06 07
     *  2  08 09 10 11
     *  3  12 13 14 15
     */

    /**
     * The index if the pattern is rotated 90 degrees clockwise
     */
    public static final int[] rotated90 = new int[]{3, 7, 11, 15, 2, 6, 10, 14, 1, 5, 9, 13, 0, 4, 8, 12};
    /**
     * The index if the pattern is flipped over the north-south axis
     */
    public static final int[] flipped = new int[]{3, 2, 1, 0, 7, 6, 5, 4, 11, 10, 9, 8, 15, 14, 13, 12};

    public CircuitPatternItem(Properties properties) {
        super(properties);
    };


    /**
     * Punch a hole in a pattern.
     * @param pattern The pattern to punch
     * @param index The index of the hole to punch
     * @return A new pattern with the punched hole
     */
    public static int punch(int pattern, int index) {
        return pattern | 1 << index;
    };

    /**
     * Punch a hole in a pattern.
     * @param pattern The pattern to punch
     * @param x x-coordinate of the hole to punch
     * @param y y-coordinate of the hole to punch
     * @return A new pattern with the punched hole
     */
    public static int punch(int pattern, int x, int y) {
        return punch(pattern, getIndex(x, y));
    };

    public static boolean isPunched(int pattern, int x, int y) {
        return isPunched(pattern, getIndex(x, y));
    };

    public static boolean isPunched(int pattern, int index) {
        return (pattern & (1 << index)) != 0;
    };

    public static int getIndex(int x, int y) {
        return x + (4 * y);
    };

    public static int getPattern(ItemStack stack) {
        if (!(stack.getItem() instanceof CircuitPatternItem || stack.getItem() instanceof SequencedAssemblyItem) || !stack.getOrCreateTag().contains("Pattern", Tag.TAG_SHORT)) return 0;
        return (int)Short.MAX_VALUE + (int)stack.getOrCreateTag().getShort("Pattern");
    };

    public static void putPattern(ItemStack stack, int pattern) {
        if (stack.getItem() instanceof CircuitPatternItem || stack.getItem() instanceof SequencedAssemblyItem) stack.getOrCreateTag().putShort("Pattern", (short)(pattern - Short.MAX_VALUE));
    };
    
};
