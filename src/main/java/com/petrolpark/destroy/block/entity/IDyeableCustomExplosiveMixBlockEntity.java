package com.petrolpark.destroy.block.entity;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

/**
 * You must call {@link IDyeableCustomExplosiveMixBlockEntity#onPlace} when the block associated with this Block Entity gets placed,
 * and {@link IDyeableCustomExplosiveMixBlockEntity#getFilledItemStack} for pick-block and the drop.
 * It's also good to call {@link IDyeableCustomExplosiveMixBlockEntity#tryDye} in the useOn method of the Block.
 */
public interface IDyeableCustomExplosiveMixBlockEntity extends ICustomExplosiveMixBlockEntity {
    
    public void setColor(int color);

    public int getColor();

    @Override
    public default void onPlace(ItemStack blockItemStack) {
        ICustomExplosiveMixBlockEntity.super.onPlace(blockItemStack);
        if (blockItemStack.getItem() instanceof DyeableLeatherItem dyeableItem) setColor(dyeableItem.getColor(blockItemStack));
    };

    @Override
    public default ItemStack getFilledItemStack(ItemStack emptyItemStack) {
        if (emptyItemStack.getItem() instanceof DyeableLeatherItem dyeableItem) dyeableItem.setColor(emptyItemStack, getColor());
        return ICustomExplosiveMixBlockEntity.super.getFilledItemStack(emptyItemStack);
    };

    public default InteractionResult tryDye(ItemStack dyeStack, HitResult target, Level level, BlockPos pos, Player player) {
        if (!(dyeStack.getItem() instanceof DyeItem dyeItem)) return InteractionResult.PASS;
        ItemStack stack = level.getBlockState(pos).getCloneItemStack(target, level, pos, player);
        if (stack.getItem() instanceof DyeableLeatherItem dyeableItem) {
            setColor(dyeableItem.getColor(DyeableLeatherItem.dyeArmor(stack, List.of(dyeItem))));
            if (!player.isCreative()) dyeStack.shrink(1);
            return InteractionResult.sidedSuccess(level.isClientSide());
        };
        return InteractionResult.PASS;
    };
    
};
