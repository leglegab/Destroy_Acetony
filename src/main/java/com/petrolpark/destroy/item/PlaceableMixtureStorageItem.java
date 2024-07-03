package com.petrolpark.destroy.item;

import org.jetbrains.annotations.Nullable;

import com.petrolpark.destroy.block.IPickUpPutDownBlock;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public abstract class PlaceableMixtureStorageItem extends BlockItem implements IMixtureStorageItem {

    public PlaceableMixtureStorageItem(Block block, Properties properties) {
        super(block, properties);
    };

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        return IPickUpPutDownBlock.removeItemFromInventory(context, super.place(context));
    };

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ItemMixtureTank(stack, fs  -> {});
    };
    
};
