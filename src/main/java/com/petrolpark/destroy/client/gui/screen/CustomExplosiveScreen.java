package com.petrolpark.destroy.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.petrolpark.destroy.client.gui.DestroyGuiTextures;
import com.petrolpark.destroy.client.gui.menu.CustomExplosiveMenu;
import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.gui.menu.AbstractSimiContainerScreen;
import com.simibubi.create.foundation.gui.widget.IconButton;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class CustomExplosiveScreen extends AbstractSimiContainerScreen<CustomExplosiveMenu> {

    private final DestroyGuiTextures background;

    private IconButton confirmButton;

    public CustomExplosiveScreen(CustomExplosiveMenu container, Inventory inv, Component title) {
        super(container, inv, title);
        background = DestroyGuiTextures.CUSTOM_EXPLOSIVE_BACKGROUND;
    };

    @Override
    protected void init() {
        setWindowSize(background.width, background.height + 112);
        super.init();
        clearWidgets();

        confirmButton = new IconButton(getGuiLeft() + background.width - 36, getGuiTop() + background.height - 24, AllIcons.I_CONFIRM);
		confirmButton.withCallback(() -> {if (minecraft != null && minecraft.player != null) minecraft.player.closeContainer();}); // It thinks minecraft and player might be null
		addRenderableWidget(confirmButton);
    };

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        PoseStack ms = graphics.pose();
        ms.pushPose();
        ms.translate(leftPos, topPos, 0d);

        background.render(graphics, 0, 0);
        renderPlayerInventory(graphics, 0, background.height + 4);

        graphics.drawString(font, title, 7, 4, 0xA6142B, false);

        ms.popPose();
    };
    
};
