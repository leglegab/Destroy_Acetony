package com.petrolpark.destroy.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import com.simibubi.create.content.logistics.filter.ItemAttribute;

@Mixin(ItemAttribute.class)
public interface ItemAttributeAccessor {
    
    @Invoker(
        value = "register",
        remap = false
    )
    public static ItemAttribute invokeRegister(ItemAttribute attributeType) {
        return null; // Method body is ignored
    };
};
