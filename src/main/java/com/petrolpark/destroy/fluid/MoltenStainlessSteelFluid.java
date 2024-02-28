package com.petrolpark.destroy.fluid;

import com.petrolpark.destroy.item.DestroyItems;
import com.simibubi.create.content.fluids.VirtualFluid;

import net.minecraft.world.item.Item;

public class MoltenStainlessSteelFluid extends VirtualFluid {

    public MoltenStainlessSteelFluid(Properties properties) {
        super(properties);
    };

    @Override
    public Item getBucket() {
        return DestroyItems.MOLTEN_STAINLESS_STEEL_BUCKET.get();
    };
    
};
