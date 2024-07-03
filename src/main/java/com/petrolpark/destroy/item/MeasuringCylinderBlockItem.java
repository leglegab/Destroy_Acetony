package com.petrolpark.destroy.item;

import com.petrolpark.destroy.block.IPickUpPutDownBlock;
import com.petrolpark.destroy.block.MeasuringCylinderBlock;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;

public class MeasuringCylinderBlockItem extends BlockItem {

    public MeasuringCylinderBlockItem(MeasuringCylinderBlock block, Properties properties) {
        super(block, properties);
    };

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        return IPickUpPutDownBlock.removeItemFromInventory(context, super.place(context));
    };
    
};
