package com.petrolpark.destroy.compat.createbigcannons.item;

import java.util.List;
import javax.annotation.Nullable;

import com.petrolpark.destroy.item.DyeableCustomExplosiveMixBlockItem;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import rbasamoyai.createbigcannons.base.CBCTooltip;
import rbasamoyai.createbigcannons.index.CBCBlocks;

public class CustomExplosiveMixChargeBlockItem extends DyeableCustomExplosiveMixBlockItem {

    public CustomExplosiveMixChargeBlockItem(Block block, Properties properties) {
        super(block, properties);
    };

    @Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
		super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
		CBCTooltip.appendMuzzleVelocityText(stack, level, tooltipComponents, isAdvanced, CBCBlocks.POWDER_CHARGE.get());
		CBCTooltip.appendPropellantStressText(stack, level, tooltipComponents, isAdvanced, CBCBlocks.POWDER_CHARGE.get());
        //TODO list contents of charge
	};
    
};
