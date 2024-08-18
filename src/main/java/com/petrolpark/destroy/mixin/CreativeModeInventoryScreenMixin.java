package com.petrolpark.destroy.mixin;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;
import com.petrolpark.destroy.DestroyClient;
import com.petrolpark.destroy.MoveToPetrolparkLibrary;
import com.petrolpark.destroy.entity.player.ExtendedInventory;
import com.petrolpark.destroy.item.creativeModeTab.CustomTab;
import com.petrolpark.destroy.item.creativeModeTab.CustomTab.ITabEntry;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen.ItemPickerMenu;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

@MoveToPetrolparkLibrary
@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin extends EffectRenderingInventoryScreen<ItemPickerMenu> {

    @Shadow
    private Slot destroyItemSlot;
    
    public CreativeModeInventoryScreenMixin(ItemPickerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        throw new AssertionError(); // Should never be called
    };

    /**
     * Don't add Extended Inventory Slots to the Survival Inventory section of the Creative Inventory in the normal way.
     */
    @Redirect(
        method = "Lnet/minecraft/client/gui/screens/inventory/CreativeModeInventoryScreen;selectTab(Lnet/minecraft/world/item/CreativeModeTab;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/core/NonNullList;size()I"
        )
    )
    public int addLimitedSlots(NonNullList<Slot> slots) {
        return 46;
    };


    /**
     * Add Extended Inventory Slots to the Survival Inventory section of the Creative Inventory.
     */
    @Inject(
        method = "Lnet/minecraft/client/gui/screens/inventory/CreativeModeInventoryScreen;selectTab(Lnet/minecraft/world/item/CreativeModeTab;)V",
        at = @At(
            value = "FIELD",
            target = "destroyItemSlot",
            ordinal = 0
        )
    )
    public void inSelectTab(CreativeModeTab tab, CallbackInfo ci) {
        ExtendedInventory inv = ExtendedInventory.get(minecraft.player);
        Int2ObjectMap<Slot> extendedInventorySlots = new Int2ObjectArrayMap<>(); // Map Inventory indices to Slots in the Inventory Menu, as the index of the Slot in the menu is not guaranteed to match the index in the Inventory the Slot encapsulates
        for (Slot slot : minecraft.player.inventoryMenu.slots) {
            if (slot.getSlotIndex() >= inv.getExtraInventoryStartSlotIndex()) extendedInventorySlots.put(slot.getSlotIndex(), slot);
        };
        DestroyClient.EXTENDED_INVENTORY_HANDLER.addSlotsToClientMenu(inv, menu::addSlot, (c, i, x, y) -> new CreativeModeInventoryScreen.SlotWrapper(extendedInventorySlots.get(i), i, x, y));
    };

    @Inject(
        method = "Lnet/minecraft/client/gui/screens/inventory/CreativeModeInventoryScreen;slotClicked",
        at = @At("HEAD")
    )
    protected void inSlotClicked(@Nullable Slot slot, int slotId, int mouseButton, ClickType type, CallbackInfo ci) {
        if (slot == destroyItemSlot && type == ClickType.QUICK_MOVE) {
            ExtendedInventory inv = ExtendedInventory.get(minecraft.player);
            for (Slot inventorySlot : menu.slots) {
                if (inventorySlot.getSlotIndex() >= inv.getExtraInventoryStartSlotIndex()) minecraft.gameMode.handleCreativeModeItemAdd(ItemStack.EMPTY, inventorySlot.index);
            };
        };
    };

    /**
     * Render fancy things in {@link CustomTab Custom Tabs}.
     */
    @Inject(
        method = "Lnet/minecraft/client/gui/screens/inventory/CreativeModeInventoryScreen;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V",
        at = @At("RETURN")
    )
    public void inRender(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (getSelectedTab() instanceof CustomTab tab) {
            int offset = getMenu().getRowIndexForScroll(getScrollOffs());
            PoseStack ms = graphics.pose();
            ms.pushPose();
            ms.translate(getGuiLeft() + 8f, getGuiTop() + 17f, 0f);
            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 9; x++) {
                    ITabEntry entry = tab.renderedEntries.get(9 * (y + offset) + x);
                    if (entry != null) {
                        ms.pushPose();
                        ms.translate(x * 18f, y * 18f, 0f);
                        entry.render(graphics, mouseX, mouseY, partialTick);
                        ms.popPose();
                    };
                };
            };
            ms.popPose();
        };
    };

    @Accessor("selectedTab")
    public static CreativeModeTab getSelectedTab() {
        throw new AssertionError();
    };

    @Accessor("scrollOffs")
    public abstract float getScrollOffs();
};
