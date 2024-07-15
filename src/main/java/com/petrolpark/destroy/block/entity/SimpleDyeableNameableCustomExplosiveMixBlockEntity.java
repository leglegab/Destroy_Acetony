package com.petrolpark.destroy.block.entity;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.petrolpark.destroy.item.inventory.CustomExplosiveMixInventory;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.items.IItemHandler;

public abstract class SimpleDyeableNameableCustomExplosiveMixBlockEntity extends SmartBlockEntity implements IDyeableCustomExplosiveMixBlockEntity {

    public LazyOptional<IItemHandler> itemCapability;

    protected CustomExplosiveMixInventory inv;
    protected int color;
    protected Component name;

    public SimpleDyeableNameableCustomExplosiveMixBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        color = 0xFFFFFF;
        inv = createInv();
        refreshCapability();
    };

    public abstract CustomExplosiveMixInventory createInv();

    public void refreshCapability() {
        itemCapability = LazyOptional.of(() -> inv);
    };

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {};

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) return itemCapability.cast();
        return LazyOptional.empty();
    };

    @Override
    public void onPlace(ItemStack blockItemStack) {
        IDyeableCustomExplosiveMixBlockEntity.super.onPlace(blockItemStack);
        if (blockItemStack.hasCustomHoverName()) name = blockItemStack.getHoverName();
    };

    @Override
    protected void read(CompoundTag tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        color = tag.getInt("Color");
        if (tag.contains("CustomName", Tag.TAG_STRING)) name = Component.Serializer.fromJson(tag.getString("CustomName"));
        inv = createInv();
        inv.deserializeNBT(tag.getCompound("ExplosiveMix"));
    };

    @Override
    protected void write(CompoundTag tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        tag.putInt("Color", color);
        if (name != null) tag.putString("CustomName", Component.Serializer.toJson(name));
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
    public ItemStack getFilledItemStack(ItemStack emptyItemStack) {
        ItemStack stack = IDyeableCustomExplosiveMixBlockEntity.super.getFilledItemStack(emptyItemStack);
        if (name != null) stack.setHoverName(name);
        return stack;
    };

    @Override
    public int getColor() {
        return color;
    };

    @Override
    public Component getDisplayName() {
        return name != null ? name : getLevel().getBlockState(getBlockPos()).getBlock().getName();
    };
    
};
