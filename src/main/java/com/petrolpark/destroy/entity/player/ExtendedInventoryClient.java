package com.petrolpark.destroy.entity.player;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.petrolpark.destroy.client.gui.DestroyGuiTextures;
import com.petrolpark.destroy.client.gui.DestroyNineSlices;
import com.petrolpark.destroy.config.DestroyAllConfigs;
import com.petrolpark.destroy.entity.player.ExtendedInventory.DelayedSlotPopulation;
import com.petrolpark.destroy.network.DestroyMessages;
import com.petrolpark.destroy.network.packet.ChangeExtraInventorySideC2SPacket;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
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
public class ExtendedInventoryClient {

    protected static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/widgets.png");
    
    @SubscribeEvent
    public static void tick(ClientTickEvent event) {
        if (event.phase != ClientTickEvent.Phase.START) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        ExtendedInventory inv = ExtendedInventory.get(mc.player);
        boolean left = DestroyAllConfigs.CLIENT.extraInventoryLeft.get();
        if (inv.extraHotbarLeft != left) {
            DestroyMessages.sendToServer(new ChangeExtraInventorySideC2SPacket(left));
            inv.extraHotbarLeft = left;
        };
    };

    @SubscribeEvent
    public static void onOpenContainerScreen(ScreenEvent.Init event) {
        if (!(event.getScreen() instanceof AbstractContainerScreen screen)) return;
        AbstractContainerMenu menu = screen.getMenu();
        Minecraft mc = Minecraft.getInstance();

        ExtendedInventory inv = ExtendedInventory.get(mc.player);
        int invWidth = DestroyAllConfigs.CLIENT.extraInventoryWidth.get();

        if (!(menu instanceof InventoryMenu)) inv.addExtraInventorySlotsToMenu(menu, invWidth, -invWidth * 18, 0, -inv.extraHotbarSlots * 18, 64);
        ((DelayedSlotPopulation)menu).populateDelayedSlots(); // Client recieves the stacks to fill early. The mixin stores them and we put them back in their proper place here.
    };

    @SubscribeEvent
    public static void renderScreen(ScreenEvent.Render event) {
        DestroyNineSlices.INVENTORY_BACKGROUND.render(event.getGuiGraphics(), 0, 0, 40, 40);
    };

    public static void renderExtraHotbarBackground(ForgeGui gui, GuiGraphics graphics, float partialTicks, int screenWidth, int screenHeight) {
        Minecraft mc = Minecraft.getInstance();
		if (mc.options.hideGui || mc.gameMode.getPlayerMode() == GameType.SPECTATOR) return;

        PoseStack ms = graphics.pose();
        ExtendedInventory inv = ExtendedInventory.get(mc.player);
        int extraSlots = inv.getExtraHotbarSlots();

        int x = screenWidth / 2 - 91;
		int y = screenHeight - 22;

        if (inv.extraHotbarLeft) {
            x -= inv.getExtraHotbarSlots() * 20;
        } else {
            x += 9 * 20;
        };

        RenderSystem.enableDepthTest();
        ms.pushPose();
        DestroyNineSlices.HOTBAR.render(graphics, x, y, 2 + extraSlots * 20, 22);
        ms.popPose();
    };

    public static void renderExtraHotbar(ForgeGui gui, GuiGraphics graphics, float partialTicks, int screenWidth, int screenHeight) {
        Minecraft mc = Minecraft.getInstance();
		if (mc.options.hideGui || mc.gameMode.getPlayerMode() == GameType.SPECTATOR) return;

        PoseStack ms = graphics.pose();
        ExtendedInventory inv = ExtendedInventory.get(mc.player);
        int extraSlots = inv.getExtraHotbarSlots();

        int x = screenWidth / 2 - 90;
		int y = screenHeight - 21;

        if (inv.extraHotbarLeft) {
            x -= inv.getExtraHotbarSlots() * 20;
        } else {
            x += 9 * 20;
        };

		RenderSystem.enableDepthTest();
        ms.pushPose();
        for (int i = 0; i < extraSlots; i++) {
            DestroyGuiTextures.HOTBAR_SLOT.render(graphics, x + i * 20, y);
            gui.renderSlot(graphics, 2 + x + i * 20, y + 2, partialTicks, mc.player, inv.extraItems.get(i), 0);
        };
        if (!inv.extraHotbarLeft) {
            ms.translate(-180f, 0f, 0f);
        };
        graphics.blit(WIDGETS_LOCATION, 20 * inv.getSelectedHotbarIndex() + x - 2, y - 2, 0, 22, 24, 22);
        
        ms.popPose();
    };

    @EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerBelow(VanillaGuiOverlay.HOTBAR.id(), "extra_hotbar_background", ExtendedInventoryClient::renderExtraHotbarBackground);
            event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "extra_hotbar", ExtendedInventoryClient::renderExtraHotbar);
        };
    };


};
