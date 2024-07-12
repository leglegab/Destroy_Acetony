package com.petrolpark.destroy.block;

import javax.annotation.Nullable;

import com.petrolpark.destroy.item.IMixtureStorageItem;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * A Block which has a unique interaction with {@link IMixtureStorageItem IMixtureStorageItems} beyond just being a simple tank of Fluid.
 */
public interface ISpecialMixtureContainerBlock {
    
    /**
     * Get a Fluid Handler between the Item and which Mixtures can be transferred.
     * @param item
     * @param context
     * @param rightClick {@code true} for a right-click (usually to empty the Item into the Block), {@code false} for left-click (usually to fill the Item from the Block)
     * @return An {@link IFluidHandler}, or {@code null}
     */
    @Nullable
    public IFluidHandler getTankForMixtureStorageItems(IMixtureStorageItem item, Level level, BlockPos pos, BlockState state, Direction face, Player player, InteractionHand hand, ItemStack stack, boolean rightClick);
};
