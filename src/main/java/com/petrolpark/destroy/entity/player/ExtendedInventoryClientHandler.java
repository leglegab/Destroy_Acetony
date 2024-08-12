package com.petrolpark.destroy.entity.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.petrolpark.destroy.client.gui.DestroyGuiTextures;
import com.petrolpark.destroy.client.gui.DestroyNineSlices;
import com.petrolpark.destroy.client.key.DestroyKeys;
import com.petrolpark.destroy.config.DestroyAllConfigs;
import com.petrolpark.destroy.config.DestroyClientConfigs;
import com.petrolpark.destroy.config.DestroyClientConfigs.ExtraInventoryClientSettings;
import com.petrolpark.destroy.entity.player.ExtendedInventory.DelayedSlotPopulation;
import com.simibubi.create.foundation.gui.menu.AbstractSimiContainerScreen;
import com.simibubi.create.foundation.utility.Iterate;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.GameType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(Dist.CLIENT)
public class ExtendedInventoryClientHandler {

    protected static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/widgets.png");

    private static final List<KeyMapping> hotbarKeys = new ArrayList<>(17);
    private static boolean keysInitialized = false;

    private ExtraInventoryClientSettings settings = null;
    
    public void tick(ClientTickEvent event) {
        if (event.phase != ClientTickEvent.Phase.START) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        ExtendedInventory inv = ExtendedInventory.get(mc.player);

        // Update survival inventory screen if the layout of the Extended Inventory has changed
        ExtraInventoryClientSettings currentSettings = DestroyAllConfigs.CLIENT.getExtraInventorySettings();
        if (!currentSettings.equals(settings)) {
            settings = currentSettings;
            refreshClientInventoryMenu(inv);
        };

        // Initialize Key Mappings
        if (!keysInitialized) {
            Collections.addAll(hotbarKeys, mc.options.keyHotbarSlots);
            Collections.addAll(hotbarKeys, DestroyKeys.HOTBAR_SLOT_9.keybind, DestroyKeys.HOTBAR_SLOT_10.keybind, DestroyKeys.HOTBAR_SLOT_11.keybind, DestroyKeys.HOTBAR_SLOT_12.keybind, DestroyKeys.HOTBAR_SLOT_13.keybind, DestroyKeys.HOTBAR_SLOT_14.keybind, DestroyKeys.HOTBAR_SLOT_15.keybind, DestroyKeys.HOTBAR_SLOT_16.keybind);
            keysInitialized = true;
        };

        // Allow switching to extended hotbar slots
        if (mc.getOverlay() != null || mc.screen != null || mc.options.keyLoadHotbarActivator.isDown() || mc.options.keySaveHotbarActivator.isDown()) return;
        for (int i = 0; i < inv.getHotbarSize(); i++) {
            if (hotbarKeys.get(i).consumeClick()) {
                int slot = i - DestroyClientConfigs.getLeftSlots(inv.getExtraHotbarSlots());
                if (slot < 0) slot += inv.getHotbarSize();
                inv.selected = inv.getSlotIndex(slot);
            };
        };
    };

    public static void refreshClientInventoryMenu(ExtendedInventory inv) {
        Rect2i screenArea = new Rect2i(0, 0, 176, 166);
        Rect2i leftHotbar = getLeftHotbarLocation(inv, screenArea, 142);
        Rect2i rightHotbar  = getRightHotbarLocation(inv, screenArea, 142);
        Rect2i combinedInventoryHotbar = getCombinedInventoryHotbarLocation(inv, screenArea, 142);
        Rect2i inventory = combinedInventoryHotbar == null ? getInventoryLocation(inv, screenArea, 142) : combinedInventoryHotbar;
        ExtendedInventory.refreshPlayerInventoryMenu(inv.player, DestroyAllConfigs.CLIENT.extraInventoryWidth.get(), inventory.getX() + INVENTORY_PADDING, inventory.getY() + INVENTORY_PADDING, DestroyClientConfigs.getLeftSlots(inv.getExtraHotbarSlots()), leftHotbar.getX() + INVENTORY_PADDING, leftHotbar.getY() + INVENTORY_PADDING, rightHotbar.getX() + INVENTORY_PADDING, rightHotbar.getY() + INVENTORY_HOTBAR_SPACING);
    };

    public static final int INVENTORY_PADDING = 7;
    public static final int INVENTORY_SPACING = 4;
    public static final int INVENTORY_HOTBAR_SPACING = 4;

    public static Rect2i getLeftHotbarLocation(ExtendedInventory inv, Rect2i screenArea, int hotbarY) {
        int slots = DestroyClientConfigs.getLeftSlots(inv.getExtraHotbarSlots());
        if (slots == 0) return null;
        return getHotbarLocation(screenArea, hotbarY, - INVENTORY_SPACING - 2 * INVENTORY_PADDING - slots * 18, slots);
    };

    public static Rect2i getRightHotbarLocation(ExtendedInventory inv, Rect2i screenArea, int hotbarY) {
        int slots = DestroyClientConfigs.getRightSlots(inv.getExtraHotbarSlots());
        if (slots == 0) return null;
        return getHotbarLocation(screenArea, hotbarY, screenArea.getWidth() + INVENTORY_SPACING, slots);
    };

    private static Rect2i getHotbarLocation(Rect2i screenArea, int hotbarY, int xOffset, int slots) {
        return new Rect2i(
            screenArea.getX() + xOffset,
            hotbarY - INVENTORY_PADDING,
            2 * INVENTORY_PADDING + slots * 18,
            2 * INVENTORY_PADDING + 18
        );
    };

    public static Rect2i getCombinedInventoryHotbarLocation(ExtendedInventory inv, Rect2i screenArea, int hotbarY) {
        boolean left = DestroyAllConfigs.CLIENT.extraInventoryLeft.get();
        int inventorySlots = inv.extraItems.size() - inv.getExtraHotbarSlots();
        int inventoryWidth = DestroyAllConfigs.CLIENT.extraInventoryWidth.get();
        int inventoryHeight = inventorySlots / inventoryWidth;
        if (inventorySlots % inventoryWidth > 0) inventoryHeight++;
        int hotbarSlots = left ? DestroyClientConfigs.getLeftSlots(inv.getExtraHotbarSlots()) : DestroyClientConfigs.getRightSlots(inv.getExtraHotbarSlots());
        int width = Math.max(inventoryWidth, hotbarSlots);
        if (hotbarSlots > 0 && inventoryHeight >= 3) {
            return new Rect2i(
                screenArea.getX() + (left ? -INVENTORY_SPACING - 2 * INVENTORY_PADDING - width * 18 : INVENTORY_SPACING + screenArea.getWidth()),
                hotbarY - INVENTORY_HOTBAR_SPACING - 18 * inventoryHeight - INVENTORY_PADDING,
                2 * INVENTORY_PADDING + width * 18,
                18 * (inventoryHeight + 1) + 2 * INVENTORY_PADDING + INVENTORY_HOTBAR_SPACING);
        } else {
            return null;
        }
    };

    public static Rect2i getInventoryLocation(ExtendedInventory inv, Rect2i screenArea, int hotbarY) {
        int inventorySlots = inv.extraItems.size() - inv.getExtraHotbarSlots();
        if (inventorySlots <= 0) return null;
        int inventoryWidth = DestroyAllConfigs.CLIENT.extraInventoryWidth.get();
        int inventoryHeight = inventorySlots / inventoryWidth;
        if (inventorySlots % inventoryWidth > 0) inventoryHeight++;
        return new Rect2i(
            screenArea.getX() + (DestroyAllConfigs.CLIENT.extraInventoryLeft.get() ? - INVENTORY_SPACING - 2 * INVENTORY_PADDING - inventoryWidth * 18: INVENTORY_SPACING + screenArea.getWidth()),
            hotbarY - INVENTORY_HOTBAR_SPACING - INVENTORY_PADDING - 18 * Math.max(3, inventoryHeight),
            2 * INVENTORY_PADDING + inventoryWidth * 18,
            2 * INVENTORY_PADDING + 18 * inventoryHeight
        );
    };

    public static Rect2i getScreenArea(AbstractContainerScreen<?> screen) {
        Rect2i area = new Rect2i(0, 0, screen.getXSize(), screen.getYSize());
        if (screen instanceof AbstractSimiContainerScreen<?> simiScreen) {
            for (Rect2i extraArea : simiScreen.getExtraAreas()) {
                area.setX(Math.min(area.getX(), extraArea.getX()));
                //area.setY(Math.min(area.getY(), extraArea.getY()));
                area.setWidth(Math.max(area.getWidth(), extraArea.getX() - area.getX() + extraArea.getWidth()));
                //area.setHeight(Math.max(area.getHeight(), extraArea.getY() + extraArea.getHeight()));
            };
        };
        return area;
    };

    private AbstractContainerScreen<?> currentScreen = null;
    private Rect2i leftHotbar = null;
    private Rect2i rightHotbar = null;
    private Rect2i combinedInventoryHotbar = null;
    private Rect2i inventory = null;
    private List<Rect2i> extraGuiAreas = Collections.emptyList();

    public void refreshExtraInventoryAreas(ExtendedInventory inv) {
        if (currentScreen == null) return;
        Rect2i screenArea = getScreenArea(currentScreen);
        int hotbarY = findHotbarY(currentScreen);
        leftHotbar = getLeftHotbarLocation(inv, screenArea, hotbarY);
        rightHotbar  = getRightHotbarLocation(inv, screenArea, hotbarY);
        combinedInventoryHotbar = getCombinedInventoryHotbarLocation(inv, screenArea, hotbarY);
        inventory = getInventoryLocation(inv, screenArea, hotbarY);

        extraGuiAreas = new ArrayList<>(3);
        if (combinedInventoryHotbar == null) {
            if (inventory != null) extraGuiAreas.add(offset(inventory, currentScreen.getGuiLeft(), currentScreen.getGuiTop()));
            if (leftHotbar != null) extraGuiAreas.add(offset(leftHotbar, currentScreen.getGuiLeft(), currentScreen.getGuiTop()));
            if (rightHotbar != null) extraGuiAreas.add(offset(rightHotbar, currentScreen.getGuiLeft(), currentScreen.getGuiTop()));
        } else {
            extraGuiAreas.add(offset(combinedInventoryHotbar, currentScreen.getGuiLeft(), currentScreen.getGuiTop()));
            Rect2i renderedHotbar = DestroyAllConfigs.CLIENT.extraInventoryLeft.get() ? rightHotbar : leftHotbar;
            if (renderedHotbar != null) extraGuiAreas.add(offset(renderedHotbar, currentScreen.getGuiLeft(), currentScreen.getGuiTop()));
        };
    };

    public static int findHotbarY(AbstractContainerScreen<?> screen) {
        Minecraft mc = Minecraft.getInstance();
        int hotbarY = screen.height - 22;
        for (Slot slot : screen.getMenu().slots) {
            if (slot.container == mc.player.getInventory() && Inventory.isHotbarSlot(slot.getSlotIndex())) return slot.y;
        };
        return hotbarY;
    };

    public int getLeftmostX() {
        if (DestroyAllConfigs.CLIENT.extraInventoryLeft.get()) {
            if (combinedInventoryHotbar != null) return combinedInventoryHotbar.getX();
            return Math.min(leftHotbar == null ? 0 : leftHotbar.getX(), inventory == null ? 0 : inventory.getX());
        };
        return leftHotbar == null ? 0 : leftHotbar.getX();
    };

    public void onOpenContainerScreen(ScreenEvent.Init.Post event) {
        if (!(event.getScreen() instanceof AbstractContainerScreen screen)) {
            currentScreen = null;
            return;
        };
        AbstractContainerMenu menu = screen.getMenu();

        if (!ExtendedInventory.supportsExtraInventory(menu) && !(menu instanceof InventoryMenu)) {
            currentScreen = null;
            return;
        };
        Minecraft mc = Minecraft.getInstance();

        ExtendedInventory inv = ExtendedInventory.get(mc.player);

        if (screen != currentScreen) {
            currentScreen = screen;
            refreshExtraInventoryAreas(inv);
        };
        if (!(menu instanceof InventoryMenu)) {
            Rect2i inventoryRect = combinedInventoryHotbar == null ? inventory : combinedInventoryHotbar;
            inv.addExtraInventorySlotsToMenu(menu, DestroyAllConfigs.CLIENT.extraInventoryWidth.get(), inventoryRect.getX() + INVENTORY_PADDING, inventoryRect.getY() + INVENTORY_PADDING, DestroyClientConfigs.getLeftSlots(inv.getExtraHotbarSlots()), leftHotbar.getX() + INVENTORY_PADDING, leftHotbar.getY() + INVENTORY_PADDING, rightHotbar.getX() + INVENTORY_PADDING, rightHotbar.getY() + INVENTORY_PADDING);
            ((DelayedSlotPopulation)menu).populateDelayedSlots(); // Client recieves the stacks to fill early. The mixin stores them and we put them back in their proper place here.
        };
    };

    public void renderScreen(ScreenEvent.Render.Pre event) {
        if (!(event.getScreen() instanceof AbstractContainerScreen screen) || screen != currentScreen) return;
        Minecraft mc = Minecraft.getInstance();
        if (!(screen.getMenu() == mc.player.inventoryMenu || ExtendedInventory.supportsExtraInventory(screen.getMenu()))) return;
        ExtendedInventory inv = ExtendedInventory.get(mc.player);
        boolean left = DestroyAllConfigs.CLIENT.extraInventoryLeft.get();
        int leftHotbarSlots = DestroyClientConfigs.getLeftSlots(inv.getExtraHotbarSlots());
        int columns = DestroyAllConfigs.CLIENT.extraInventoryWidth.get();
        GuiGraphics graphics = event.getGuiGraphics();
        PoseStack ms = graphics.pose();

        RenderSystem.enableDepthTest();
        ms.pushPose(); {
            ms.translate(screen.getGuiLeft(), screen.getGuiTop(), 2f);
            ms.pushPose();
            ms.translate(-1f, -1f, 0f);
            if (combinedInventoryHotbar != null) {
                DestroyNineSlices.INVENTORY_BACKGROUND.render(graphics, combinedInventoryHotbar);
                Rect2i renderedHotbar = left ? rightHotbar : leftHotbar;
                if (renderedHotbar != null) DestroyNineSlices.INVENTORY_BACKGROUND.render(graphics, renderedHotbar);
            } else {
                if (inventory != null) DestroyNineSlices.INVENTORY_BACKGROUND.render(graphics, inventory);
                if (leftHotbar != null) DestroyNineSlices.INVENTORY_BACKGROUND.render(graphics, leftHotbar);
                if (rightHotbar != null) DestroyNineSlices.INVENTORY_BACKGROUND.render(graphics, rightHotbar);
            };
            ms.popPose();
            // Render left hotbar slot backgrounds
            if (leftHotbar != null) for (int i = 0; i < leftHotbarSlots; i++) {
                DestroyGuiTextures.INVENTORY_SLOT.render(graphics, leftHotbar.getX() + INVENTORY_PADDING - 1 + i * 18, leftHotbar.getY() + INVENTORY_PADDING - 1);
            };
            // Render right hotbar slot backgrounds
            int j = 0;
            if (rightHotbar != null) for (int i = leftHotbarSlots; i < inv.getExtraHotbarSlots(); i++) {
                DestroyGuiTextures.INVENTORY_SLOT.render(graphics, rightHotbar.getX() + INVENTORY_PADDING - 1 + j * 18, rightHotbar.getY() + INVENTORY_PADDING - 1);
                j++;
            };
            // Render main extra inventory slot backgrounds
            j = 0;
            Rect2i invRect = combinedInventoryHotbar == null ? inventory : combinedInventoryHotbar;
            if (invRect != null) for (int i = inv.getExtraHotbarSlots(); i < inv.extraItems.size(); i++) {
                DestroyGuiTextures.INVENTORY_SLOT.render(graphics, invRect.getX() + INVENTORY_PADDING - 1 + 18 * (j % columns), invRect.getY() + INVENTORY_PADDING - 1 + (j / columns) * 18);
                j++;
            };
        }; ms.popPose();
    };

    public void onCloseScreen(ScreenEvent.Closing event) {
        currentScreen = null;
    };

    public static void renderExtraHotbarBackground(ForgeGui gui, GuiGraphics graphics, float partialTicks, int screenWidth, int screenHeight) {
        Minecraft mc = Minecraft.getInstance();
		if (mc.options.hideGui || mc.gameMode.getPlayerMode() == GameType.SPECTATOR) return;

        PoseStack ms = graphics.pose();
        ExtendedInventory inv = ExtendedInventory.get(mc.player);
        int extraSlots = inv.getExtraHotbarSlots();

		int y = screenHeight - 22;
        
        RenderSystem.enableDepthTest();

        for (boolean right : Iterate.trueAndFalse) {
            int x = screenWidth / 2 - 91;
            int slotCount;
            if (right) {
                slotCount = DestroyClientConfigs.getRightSlots(extraSlots);
                x += 9 * 20;
            } else {
                slotCount = DestroyClientConfigs.getLeftSlots(extraSlots);
                x -= slotCount * 20;
            };

            ms.pushPose();
            DestroyNineSlices.HOTBAR.render(graphics, x, y, 2 + slotCount * 20, 22);
            ms.popPose();
        };

    };

    public static void renderExtraHotbar(ForgeGui gui, GuiGraphics graphics, float partialTicks, int screenWidth, int screenHeight) {
        Minecraft mc = Minecraft.getInstance();
		if (mc.options.hideGui || mc.gameMode.getPlayerMode() == GameType.SPECTATOR) return;

        PoseStack ms = graphics.pose();
        ExtendedInventory inv = ExtendedInventory.get(mc.player);
        int extraSlots = inv.getExtraHotbarSlots();

    
		int y = screenHeight - 21;


		RenderSystem.enableDepthTest();

        int item = 0;
        for (boolean right : Iterate.trueAndFalse) {
            int slotCount;
            int x = screenWidth / 2 - 90;
            if (right) {
                slotCount = DestroyClientConfigs.getRightSlots(extraSlots);
                x += 9 * 20;
            } else {
                slotCount = DestroyClientConfigs.getLeftSlots(extraSlots);
                x -= slotCount * 20;
            };
            if (slotCount == 0) continue;

            ms.pushPose();
            for (int i = 0; i < slotCount; i++) {
                DestroyGuiTextures.HOTBAR_SLOT.render(graphics, x + i * 20, y);
                gui.renderSlot(graphics, 2 + x + i * 20, y + 2, partialTicks, mc.player, inv.extraItems.get(item), 0);
                item++;
            };
            ms.popPose();
        };

        ms.pushPose();

        int selected = inv.getSelectedHotbarIndex();
        int selectedX = screenWidth / 2 - 92;
        if (inv.getSelectedHotbarIndex() >= Inventory.getSelectionSize() + DestroyClientConfigs.getRightSlots(extraSlots)) { // If a left extra slot is selected
            selectedX -= DestroyClientConfigs.getLeftSlots(extraSlots) * 20;
            selected -= (Inventory.getSelectionSize() + DestroyClientConfigs.getRightSlots(extraSlots));
        };
        selectedX += selected * 20;

        graphics.blit(WIDGETS_LOCATION, selectedX, y - 2, 0, 22, 24, 22);
        
        ms.popPose();
    };

    public List<Rect2i> getGuiExtraAreas() {
        if (currentScreen == null) return Collections.emptyList();
        return extraGuiAreas;
    };

    @EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerBelow(VanillaGuiOverlay.HOTBAR.id(), "extra_hotbar_background", ExtendedInventoryClientHandler::renderExtraHotbarBackground);
            event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "extra_hotbar", ExtendedInventoryClientHandler::renderExtraHotbar);
        };
    };

    private static Rect2i offset(Rect2i rect, int x, int y) {
        return new Rect2i(rect.getX() + x, rect.getY() + y, rect.getWidth(), rect.getHeight());
    };


};
