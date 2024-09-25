package com.petrolpark.destroy.block;

import com.petrolpark.destroy.item.DestroyItems;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class MoltenStainlessSteelBlock extends SemiMoltenBlock {

    public MoltenStainlessSteelBlock(Properties properties) {
        super(properties);
    };

    @Override
    public ItemStack pickupBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        return DestroyItems.MOLTEN_STAINLESS_STEEL_BUCKET.asStack();
    };

    @Override
    public Item asItem() {
        return DestroyItems.MOLTEN_STAINLESS_STEEL_BUCKET.get();
    };

    @Override
    public BlockState getSolidifiedBlockState() {
        return DestroyBlocks.STAINLESS_STEEL_BLOCK.getDefaultState();
    };
    
};
