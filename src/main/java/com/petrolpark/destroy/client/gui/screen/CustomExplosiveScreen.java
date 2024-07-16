package com.petrolpark.destroy.client.gui.screen;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import com.petrolpark.destroy.block.entity.ICustomExplosiveMixBlockEntity;
import com.petrolpark.destroy.client.gui.DestroyGuiTextures;
import com.petrolpark.destroy.client.gui.menu.CustomExplosiveMenu;
import com.petrolpark.destroy.item.tooltip.ExplosivePropertiesTooltip;
import com.petrolpark.destroy.world.explosion.ExplosiveProperties;
import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.gui.menu.AbstractSimiContainerScreen;
import com.simibubi.create.foundation.gui.widget.IconButton;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class CustomExplosiveScreen extends AbstractSimiContainerScreen<CustomExplosiveMenu> {

    private final DestroyGuiTextures background;

    private final ICustomExplosiveMixBlockEntity be;
    private ExplosiveProperties explosiveProperties = null;

    private IconButton confirmButton;

    public CustomExplosiveScreen(CustomExplosiveMenu container, Inventory inv, Component title) {
        super(container, inv, title);
        be = container.contentHolder;
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
    protected void containerTick() {
        super.containerTick();
        explosiveProperties = be.getExplosiveInventory().getExplosiveProperties() 
            .withConditions(be.getApplicableExplosionConditions());
    };

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        PoseStack ms = graphics.pose();
        ms.pushPose();
        ms.translate(leftPos, topPos, 0d);

        background.render(graphics, 0, 0);
        renderPlayerInventory(graphics, 0, background.height + 4);
        if (explosiveProperties != null) {
            ms.pushPose();
            ms.translate(10, 22, 0);
            ExplosivePropertiesTooltip.renderProperties(explosiveProperties, font, graphics, mouseX - getGuiLeft() - 10, mouseY - getGuiTop() - 22);
            ms.popPose();
        };

        graphics.drawString(font, title, 7, 4, 0xA6142B, false);

        ms.popPose();
    };

    @Override
    protected void renderForeground(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.renderForeground(graphics, mouseX, mouseY, partialTicks);
        List<Component> tooltip = ExplosivePropertiesTooltip.getSelected(explosiveProperties, mouseX - getGuiLeft() - 10, mouseY - getGuiTop() - 22).getTooltip(explosiveProperties);
        if (!tooltip.isEmpty()) graphics.renderComponentTooltip(font, tooltip, mouseX, mouseY);
    };
    
};
