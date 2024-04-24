package com.petrolpark.destroy.block.entity;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.petrolpark.destroy.item.inventory.CustomExplosiveMixInventory;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.items.IItemHandler;

public class SimpleDyeableCustomExplosiveMixBlockEntity extends SmartBlockEntity implements IDyeableCustomExplosiveMixBlockEntity {

    public LazyOptional<IItemHandler> itemCapability;

    protected CustomExplosiveMixInventory inv;
    protected int color;

    public SimpleDyeableCustomExplosiveMixBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
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
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {};

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) return itemCapability.cast();
        return LazyOptional.empty();
    };

    @Override
    protected void read(CompoundTag tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        color = tag.getInt("Color");
        inv = createInv();
        inv.deserializeNBT(tag.getCompound("ExplosiveMix"));
    };

    @Override
    protected void write(CompoundTag tag, boolean clientPacket) {
        super.write(tag, clientPacket);
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
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> reRender());
        notifyUpdate();
    };

    @OnlyIn(Dist.CLIENT)
    public void reRender() {
        SectionPos pos = SectionPos.of(getBlockPos());
        if (getLevel() instanceof ClientLevel level) level.setSectionDirtyWithNeighbors(pos.x(), pos.y(), pos.z());
    };

    @Override
    public int getColor() {
        return color;
    };
    
};
