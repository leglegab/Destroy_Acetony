package com.petrolpark.destroy.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;

public class CombustibleBlockItem extends BlockItem {

    private int burnTime = -1;

    public CombustibleBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    };

    public void setBurnTime(int burnTime) {
		this.burnTime = burnTime;
	};

	@Override
	public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
		return this.burnTime;
	};
    
};
