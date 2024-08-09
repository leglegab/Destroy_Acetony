package com.petrolpark.destroy.capability.player;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.petrolpark.destroy.config.DestroyAllConfigs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.items.ItemStackHandler;

@EventBusSubscriber
public class PlayerExtendedInventory implements Container {

    public static final Capability<PlayerExtendedInventory> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    protected final ItemStackHandler extraInventory = new ItemStackHandler();
    protected int extraHotbarSlots;

    public PlayerExtendedInventory() {
        extraInventory.setSize(1);
        extraInventory.setStackInSlot(0, new ItemStack(Items.ROTTEN_FLESH, 13));
    };

    public void setSize(Player player, int size) {
        if (size < extraInventory.getSlots()) {
            for (int slot = size; slot < extraInventory.getSlots(); slot++) {
                player.drop(extraInventory.getStackInSlot(slot), false);
            };
        };
        extraInventory.setSize(size);
    };

    public PlayerExtendedInventory read(CompoundTag tag) {
        extraInventory.deserializeNBT(tag.getCompound("ExtraInventory"));
        extraHotbarSlots = tag.getInt("ExtraHotbarSlots");
        return this;
    };

    public CompoundTag write() {
        CompoundTag tag = new CompoundTag();
        tag.put("ExtraInventory", extraInventory.serializeNBT());
        tag.putInt("ExtraHotbarSlots", extraHotbarSlots);
        return tag;
    };

    @Override
    public void clearContent() {
        for (int slot = 0; slot < extraInventory.getSlots(); slot++) {
            extraInventory.setStackInSlot(slot, ItemStack.EMPTY);
        };
    };

    @Override
    public int getContainerSize() {
        return extraInventory.getSlots();
    };

    @Override
    public boolean isEmpty() {
        for (int slot = 0; slot < extraInventory.getSlots(); slot++) {
            if (!extraInventory.getStackInSlot(slot).isEmpty()) return false;
        };
        return true;
    };

    @Override
    public ItemStack getItem(int slot) {
        return extraInventory.getStackInSlot(slot);
    };

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack stack = extraInventory.getStackInSlot(slot);
        amount = Math.min(amount, stack.getCount());
        ItemStack removed = stack.copyWithCount(amount);
        stack.shrink(amount);
        return removed;
    };

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return extraInventory.extractItem(slot, extraInventory.getStackInSlot(slot).getCount(), false);
    };

    @Override
    public void setItem(int slot, ItemStack stack) {
        extraInventory.setStackInSlot(slot, stack);
    };

    @Override
    public void setChanged() {};

    @Override
    public boolean stillValid(Player player) {
        return player.getCapability(CAPABILITY).map(pe -> pe == this).orElse(false);
    };

    public static void copyOverDeath(Player deadPlayer, Player newPlayer) {
        newPlayer.getCapability(CAPABILITY).ifPresent(newInv -> {
            deadPlayer.getCapability(CAPABILITY).ifPresent(deadInv -> {
                if (newPlayer.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
                    newInv.read(deadInv.write());
                } else if (DestroyAllConfigs.SERVER.substances.keepExtraInventorySizeOnDeath.get()) {
                    newInv.extraInventory.setSize(deadInv.extraInventory.getSlots());
                    newInv.extraHotbarSlots = deadInv.extraHotbarSlots;
                };
            });
        });
    };

    @SubscribeEvent
    public static void onOpenContainer(PlayerContainerEvent.Open event) {
        AbstractContainerMenu menu = event.getContainer();
        Player player = event.getEntity();
        addSlots(player, menu);
    };

    @SubscribeEvent
    public static void onOpenContainerScreen(ScreenEvent.Init event) {
        if (!(event.getScreen() instanceof AbstractContainerScreen screen)) return;
        AbstractContainerMenu menu = screen.getMenu();
        Minecraft mc = Minecraft.getInstance();
        addSlots(mc.player, menu);
        ((DelayedSlotPopulation)menu).populateDelayedSlots(); // Client recieves the stacks to fill early. The mixin stores them and we put them back in their proper place here.
    };

    public static void addSlots(Player player, AbstractContainerMenu menu) {
        player.getCapability(CAPABILITY).ifPresent(ei -> {
            menu.addSlot(new Slot(ei, 0, 0, 0));
        });
    };

    public class ExtendedInventorySlot extends Slot {

        public ExtendedInventorySlot(int slot, int x, int y) {
            super(PlayerExtendedInventory.this, slot, x, y);
        }};

    public static class Provider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

        private PlayerExtendedInventory playerExtendedInventory;
        private final LazyOptional<PlayerExtendedInventory> optional = LazyOptional.of(() -> {
            if (playerExtendedInventory == null) playerExtendedInventory = new PlayerExtendedInventory();
            return playerExtendedInventory;
        });

        @Override
        public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if (cap == CAPABILITY) return optional.cast();
            return LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            return (playerExtendedInventory == null ? new PlayerExtendedInventory() : playerExtendedInventory).write();
        };

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            playerExtendedInventory = new PlayerExtendedInventory();
            playerExtendedInventory.read(nbt);
        };

    };

    public static interface DelayedSlotPopulation {
        public void populateDelayedSlots();
    };


};
