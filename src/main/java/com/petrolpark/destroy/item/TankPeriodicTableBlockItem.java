package com.petrolpark.destroy.item;

import com.petrolpark.destroy.block.TankPeriodicTableBlock;

public class TankPeriodicTableBlockItem extends PeriodicTableBlockItem {

    public TankPeriodicTableBlockItem(TankPeriodicTableBlock block, Properties properties) {
        super(block, properties);
    };

    public int getColor() {
        return ((TankPeriodicTableBlock)getBlock()).color;
    };
    
};
