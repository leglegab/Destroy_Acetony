package com.petrolpark.destroy.item.attributes;

import java.util.ArrayList;
import java.util.List;

import com.petrolpark.destroy.item.CircuitPatternItem;
import com.simibubi.create.content.logistics.filter.ItemAttribute;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class CircuitPatternAttribute implements ItemAttribute {

    public final int position;
    public final boolean punched;

    public CircuitPatternAttribute(int position, boolean punched) {
        this.position = position;
        this.punched = punched;
    };

    @Override
    public boolean appliesTo(ItemStack stack) {
        return (stack.getItem() instanceof CircuitPatternItem && CircuitPatternItem.isPunched(CircuitPatternItem.getPattern(stack), position) == punched);
    };

    @Override
    public List<ItemAttribute> listAttributesOf(ItemStack stack) {
        if (!(stack.getItem() instanceof CircuitPatternItem)) return List.of();
        int pattern = CircuitPatternItem.getPattern(stack);
        List<ItemAttribute> attributes = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) attributes.add(new CircuitPatternAttribute(i, CircuitPatternItem.isPunched(pattern, i)));
        return attributes;
    };

    @Override
    public String getTranslationKey() {
        return punched ? "circuit_pattern_punched" : "circuit_pattern_punched.inverted";
    };

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{(position % 4) + 1, (position / 4) + 1};
    };

    @Override
    public String getNBTKey() {
        return "CircuitPositionPunched";
    };

    @Override
    public void writeNBT(CompoundTag nbt) {
        nbt.putInt("Position", position);
        nbt.putBoolean("Punched", punched);
    };

    @Override
    public ItemAttribute readNBT(CompoundTag nbt) {
        return new CircuitPatternAttribute(nbt.getInt("Position"), nbt.getBoolean("Punched"));
    };
    
};
