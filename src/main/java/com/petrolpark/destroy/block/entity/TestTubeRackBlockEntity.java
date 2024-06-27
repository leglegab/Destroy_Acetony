package com.petrolpark.destroy.block.entity;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.petrolpark.destroy.util.DestroyTags.DestroyItemTags;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.item.ItemHelper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

public class TestTubeRackBlockEntity extends SmartBlockEntity {

    private TestTubeRackInventory inv;
    private LazyOptional<TestTubeRackInventory> lazyItemHandler;

    public TestTubeRackBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        inv = new TestTubeRackInventory();
        lazyItemHandler = LazyOptional.empty();
    };

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        lazyItemHandler = LazyOptional.of(() -> inv);
    };

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) return lazyItemHandler.cast();
        return super.getCapability(cap, side);
    };

    @Override
    public void destroy() {
        super.destroy();
        ItemHelper.dropContents(getLevel(), getBlockPos(), inv);
    };

    @Override
    public void invalidate() {
        super.invalidate();
        lazyItemHandler.invalidate();
    };

    public class TestTubeRackInventory extends ItemStackHandler {

        public TestTubeRackInventory() {
            super(4);
        };

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        };

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return stack.is(DestroyItemTags.TEST_TUBE_RACK_STORABLE.tag);
        };

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            notifyUpdate();
        };
    };
    
};
