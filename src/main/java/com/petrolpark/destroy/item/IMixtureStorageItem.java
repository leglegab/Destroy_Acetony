package com.petrolpark.destroy.item;

import java.util.Optional;

import javax.annotation.Nullable;

import com.petrolpark.destroy.block.ISpecialMixtureContainerBlock;
import com.petrolpark.destroy.block.VatControllerBlock;
import com.petrolpark.destroy.block.VatSideBlock;
import com.petrolpark.destroy.block.entity.DestroyBlockEntityTypes;
import com.petrolpark.destroy.block.entity.VatControllerBlockEntity;
import com.petrolpark.destroy.block.entity.VatSideBlockEntity;
import com.petrolpark.destroy.block.entity.VatControllerBlockEntity.VatTankWrapper;
import com.petrolpark.destroy.block.entity.behaviour.fluidTankBehaviour.GeniusFluidTankBehaviour.GeniusFluidTank;
import com.petrolpark.destroy.fluid.MixtureFluid;
import com.simibubi.create.foundation.utility.Iterate;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

/**
 * An Item which can store Mixtures (I feel like you could have figured that one out yourself).
 * This inteface mainly provides a bunch of convenience methods common to most Items which can deal with Fluids.
 */
public interface IMixtureStorageItem {
    
    /**
     * Get the capacity this ItemStack should have. This can and will be called before and after the {@link net.minecraftforge.common.extensions.IForgeItem#initCapabilities Fluid Capability is initialized}.
     * @param stack
     */
    public int getCapacity(ItemStack stack);

    /**
     * Fill a stack of this Item from an external source. This is usually triggered by right-clicking with the Item on a Block.
     * @param itemStack The {@link IMixtureStorageItem} Stack we are trying to fill
     * @param itemTank The {@link ItemMixtureTank} owned by that ItemStack, already getted for convenience' sake
     * @param otherTank The tank we are trying to fill from, usually something capable of handling mixtures like a {@link GeniusFluidTank}
     */
    public default InteractionResult tryFill(ItemStack stack, ItemMixtureTank itemTank, IFluidHandler otherTank) {
        if (otherTank == null) return InteractionResult.PASS;
        for (boolean simulate : Iterate.trueAndFalse) {
            FluidStack drained = otherTank.drain(itemTank.getRemainingSpace(), simulate ? FluidAction.SIMULATE : FluidAction.EXECUTE);
            if (drained.isEmpty()) return InteractionResult.FAIL;
            if (!simulate) itemTank.fill(drained, FluidAction.EXECUTE);
        };
        return InteractionResult.SUCCESS;
    };

    /**
     * Empty this Item into an external source. This is usually triggered by left-clicking with the Item on a Block.
     * @param itemStack The {@link IMixtureStorageItem} Stack we are trying to empty
     * @param itemTank The {@link ItemMixtureTank} owned by that ItemStack, already getted for convenience' sake
     * @param otherTank The tank into which we are trying to empty, usually something capable of handling mixtures like a {@link GeniusFluidTank}
     * @param infiniteFluid Whether to actually empty this Item, or to keep it (typically because we are in Creative)
     */
    public default InteractionResult tryEmpty(ItemStack stack, ItemMixtureTank itemTank, IFluidHandler otherTank, boolean infiniteFluid) {
        if (otherTank == null) return InteractionResult.PASS;
        FluidStack fs = getContents(stack).orElse(FluidStack.EMPTY).copy();
        if (fs.isEmpty()) return InteractionResult.FAIL;
        for (boolean simulate : Iterate.trueAndFalse) {
            int filled = otherTank.fill(fs, simulate ? FluidAction.SIMULATE : FluidAction.EXECUTE);
            if (filled == 0) return InteractionResult.FAIL;
            if (!simulate && !infiniteFluid) itemTank.drain(filled, FluidAction.EXECUTE);
        };
        return InteractionResult.SUCCESS;
    };

    /**
     * Find and select an {@link IFluidHandler} when clicking on a Block. We can then use this tank to empty or fill.
     * @param context
     * @param rightClick {@code true} for a right-click and {@code false} for a left-click
     */
    @Nullable
    public default IFluidHandler getTank(UseOnContext context, boolean rightClick) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        IFluidHandler fluidHandler;
        if (state.getBlock() instanceof ISpecialMixtureContainerBlock specialBlock) fluidHandler = specialBlock.getTankForMixtureStorageItems(this, context, rightClick);
        VatControllerBlockEntity vatController = null;
        if (state.getBlock() instanceof VatControllerBlock) {
            vatController = level.getBlockEntity(pos, DestroyBlockEntityTypes.VAT_CONTROLLER.get()).orElse(null);
        } else if (state.getBlock() instanceof VatSideBlock) {
            vatController = level.getBlockEntity(pos, DestroyBlockEntityTypes.VAT_SIDE.get()).map(VatSideBlockEntity::getController).orElse(null);
        };
        if (vatController != null && vatController.getVatOptional().isPresent()) fluidHandler = selectVatTank(context, vatController, rightClick);
        else {
            BlockEntity be = level.getBlockEntity(pos);
            if (be == null) return null;
            LazyOptional<IFluidHandler> fluidCap = be.getCapability(ForgeCapabilities.FLUID_HANDLER, context.getClickedFace());
            if (!fluidCap.isPresent()) return null;
            fluidHandler = fluidCap.resolve().get(); 
        };
        return fluidHandler;
    };

    /**
     * Sub-method of {@link IMixtureStorageItem#getTank(UseOnContext, boolean)} for selecting how to insert/extract Fluids from the Vat, if we've found one.
     */
    @Nullable
    public default VatTankWrapper selectVatTank(UseOnContext context, VatControllerBlockEntity vatController, boolean rightClick) {
        return new SinglePhaseVatExtraction(vatController, false);
    };

    /**
     * The typical behaviour for right-clicking with a Mixture container on a Block (emptying the Item into the Block).
     * @param context
     */
    public static InteractionResult defaultUseOn(IMixtureStorageItem item, UseOnContext context) {
        return item.tryEmpty(context.getItemInHand(), (ItemMixtureTank)context.getItemInHand().getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve().get(), item.getTank(context, true), context.getPlayer().isCreative());
    };

    /**
     * The typical behaviour for left-clicking with a Mixture container on a Block (filling the Item from the Block).
     */
    public static InteractionResult defaultAttack(IMixtureStorageItem item, UseOnContext context) {
        return item.tryFill(context.getItemInHand(), (ItemMixtureTank)context.getItemInHand().getCapability(ForgeCapabilities.FLUID_HANDLER).resolve().get(), item.getTank(context, false));
    };

    public default boolean isEmpty(ItemStack stack)  {
        return getContents(stack).map(FluidStack::isEmpty).orElse(true);  
    };

    public default int getColor(ItemStack stack) {
        return getContents(stack).map(MixtureFluid::getTintColor).orElse(0xFFFFFFFF);
    };

    public default Optional<FluidStack> getContents(ItemStack itemStack) {
        return itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).map(tanks -> tanks.drain(getCapacity(itemStack), FluidAction.SIMULATE));
    };

    public default void setContents(ItemStack itemStack, FluidStack fluidStack) {
        itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(tanks -> tanks.fill(fluidStack, FluidAction.EXECUTE));
    };

    public static class SinglePhaseVatExtraction extends com.petrolpark.destroy.block.entity.VatControllerBlockEntity.VatTankWrapper {

        public final boolean gas;

        public SinglePhaseVatExtraction(VatControllerBlockEntity vatController, boolean gas) {
            super(() -> vatController, vatController::addFluid);
            this.gas = gas;
        };

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            return gas ? drainGasTank(resource, action) : drainLiquidTank(resource, action);
        };

        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            return gas ? drainGasTank(maxDrain, action) : drainLiquidTank(maxDrain, action);
        };

    };  
};
