package com.petrolpark.destroy.item;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import com.petrolpark.destroy.block.ISpecialMixtureContainerBlock;
import com.petrolpark.destroy.block.entity.VatControllerBlockEntity;
import com.petrolpark.destroy.block.entity.VatControllerBlockEntity.VatTankWrapper;
import com.petrolpark.destroy.block.entity.behaviour.fluidTankBehaviour.GeniusFluidTankBehaviour.GeniusFluidTank;
import com.petrolpark.destroy.chemistry.ClientMixture;
import com.petrolpark.destroy.chemistry.ReadOnlyMixture;
import com.petrolpark.destroy.config.DestroyAllConfigs;
import com.petrolpark.destroy.fluid.MixtureFluid;
import com.simibubi.create.foundation.utility.Iterate;
import com.simibubi.create.foundation.utility.Lang;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
    public default IFluidHandler getTank(Level level, BlockPos pos, BlockState state, @Nullable Direction face, Player player, InteractionHand hand, ItemStack stack, boolean rightClick) {
        IFluidHandler fluidHandler;
        if (state.getBlock() instanceof ISpecialMixtureContainerBlock specialBlock) fluidHandler = specialBlock.getTankForMixtureStorageItems(this, level, pos, state, face, player, hand, stack, rightClick);
        else {
            BlockEntity be = level.getBlockEntity(pos);
            if (be == null) return null;
            LazyOptional<IFluidHandler> fluidCap = be.getCapability(ForgeCapabilities.FLUID_HANDLER, face);
            if (!fluidCap.isPresent()) return null;
            fluidHandler = fluidCap.resolve().get(); 
        };
        return fluidHandler;
    };

    /**
     * Sub-method of {@link IMixtureStorageItem#getTank(UseOnContext, boolean)} for selecting how to insert/extract Fluids from the Vat, if we've found one.
     */
    @Nullable
    public default VatTankWrapper selectVatTank(Level level, BlockPos pos, BlockState state, Direction face, Player player, InteractionHand hand, ItemStack stack, boolean rightClick, VatControllerBlockEntity vatController) {
        return new SinglePhaseVatExtraction(vatController, false);
    };

    /**
     * The typical behaviour for right-clicking with a Mixture container on a Block (emptying the Item into the Block).
     * @param context
     */
    public static InteractionResult defaultUseOn(IMixtureStorageItem item, UseOnContext context) {
        InteractionResult result = item.tryEmpty(context.getItemInHand(), (ItemMixtureTank)context.getItemInHand().getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve().get(), item.getTank(context.getLevel(), context.getClickedPos(), context.getLevel().getBlockState(context.getClickedPos()), context.getClickedFace(), context.getPlayer(), context.getHand(), context.getItemInHand(), true), context.getPlayer().isCreative());
        if (result == InteractionResult.SUCCESS) context.getLevel().playSound(null, context.getClickedPos(), SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS);
        return result;
    };

    /**
     * The typical behaviour for left-clicking with a Mixture container on a Block (filling the Item from the Block).
     */
    public static InteractionResult defaultAttack(IMixtureStorageItem item, Level level, BlockPos pos, BlockState state, Direction face, Player player, InteractionHand hand, ItemStack stack) {
        InteractionResult result = item.tryFill(stack, (ItemMixtureTank)stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve().get(), item.getTank(level, pos, state, face, player, hand, stack, false));
        if (result == InteractionResult.SUCCESS) level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS);
        return result;
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

    public default Component getNameWithFluid(ItemStack stack) {
        FluidStack contents = getContents(stack).orElse(FluidStack.EMPTY);
        if (contents.isEmpty()) return Component.translatable(stack.getDescriptionId());
        return Component.translatable(stack.getDescriptionId() + ".filled", contents.getDisplayName());
    };

    static final DecimalFormat df = new DecimalFormat();

    static class DF {
        static {
            df.setMinimumFractionDigits(1);
            df.setMaximumFractionDigits(1);
        };
    };

    public default void addContentsDescription(ItemStack stack, List<Component> tooltip) {
        getContents(stack).ifPresent(fluidStack -> {

            if (fluidStack.isEmpty()) return;

            float temperature = 289f;

            tooltip.add(Component.literal(""));
        
            CompoundTag mixtureTag = fluidStack.getOrCreateTag().getCompound("Mixture");
            if (!mixtureTag.isEmpty()) { // If this is a Mixture
                ReadOnlyMixture mixture = ReadOnlyMixture.readNBT(ClientMixture::new, mixtureTag);

                boolean iupac = DestroyAllConfigs.CLIENT.chemistry.iupacNames.get();
                temperature = mixture.getTemperature();
                tooltip.addAll(mixture.getContentsTooltip(iupac, false, false, fluidStack.getAmount(), df).stream().map(c -> c.copy()).toList());
            };

            tooltip.add(2, Component.literal(" "+fluidStack.getAmount()).withStyle(ChatFormatting.GRAY).append(Lang.translateDirect("generic.unit.millibuckets")).append(" "+DestroyAllConfigs.CLIENT.chemistry.temperatureUnit.get().of(temperature, df)));
        });
    };

    public static class SinglePhaseVatExtraction extends VatTankWrapper {

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
