package com.petrolpark.destroy.compat.createbigcannons.block.entity;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.petrolpark.destroy.block.entity.IDyeableCustomExplosiveMixBlockEntity;
import com.petrolpark.destroy.item.inventory.CustomExplosiveMixInventory;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.items.IItemHandler;
import rbasamoyai.createbigcannons.munitions.big_cannon.FuzedBlockEntity;

public class CustomExplosiveMixShellBlockEntity extends FuzedBlockEntity implements IDyeableCustomExplosiveMixBlockEntity {

    public LazyOptional<IItemHandler> itemCapability;

    protected CustomExplosiveMixInventory inv;
    protected int color;

    public CustomExplosiveMixShellBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        color = 0xFFFFFF;
        inv = createInv();
        refreshCapability();
    };

    public CustomExplosiveMixInventory createInv() {
        return new CustomExplosiveMixInventory(16);
    };

    public void refreshCapability() {
        itemCapability = LazyOptional.of(() -> inv);
    };

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) return itemCapability.cast();
        return LazyOptional.empty();
    };

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        color = tag.getInt("Color");
        inv = createInv();
        inv.deserializeNBT(tag.getCompound("ExplosiveMix"));
    };

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("Color", color);
        tag.put("ExplosiveMix", inv.serializeNBT());
    };

    @Override
    public CustomExplosiveMixInventory getExplosiveInventory() {
        return inv;
    };

    @Override
    public void setExplosiveInventory(CustomExplosiveMixInventory inv) {
        this.inv = inv;
        refreshCapability();
        notifyUpdate();
    };

    @Override
    public void setColor(int color) {
        this.color = color;
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> IDyeableCustomExplosiveMixBlockEntity.reRender(getLevel(), getBlockPos()));
        notifyUpdate();
    };

    @Override
    public int getColor() {
        return color;
    };

    public ItemStack getFuze() {
        return getItem(0);
    };
    
};
