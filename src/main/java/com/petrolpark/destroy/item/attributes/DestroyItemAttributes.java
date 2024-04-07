package com.petrolpark.destroy.item.attributes;

import com.simibubi.create.content.logistics.filter.ItemAttribute;

public abstract class DestroyItemAttributes implements ItemAttribute {
    
    public static final ItemAttribute

    isCircuitPatternPunched = ItemAttribute.register(new CircuitPatternAttribute(0, true));

    public static void register() {};
};
