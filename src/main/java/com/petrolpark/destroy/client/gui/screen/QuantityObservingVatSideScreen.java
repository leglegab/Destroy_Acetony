package com.petrolpark.destroy.client.gui.screen;

import java.util.List;

import com.mojang.blaze3d.platform.InputConstants;
import com.petrolpark.destroy.block.entity.VatSideBlockEntity;
import com.petrolpark.destroy.client.gui.DestroyGuiTextures;
import com.petrolpark.destroy.network.DestroyMessages;
import com.petrolpark.destroy.network.packet.VatSideQuantityThresholdChangeC2SPacket;
import com.petrolpark.destroy.util.DestroyLang;
import com.simibubi.create.foundation.gui.AbstractSimiScreen;
import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.gui.widget.IconButton;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

public class QuantityObservingVatSideScreen extends AbstractSimiScreen {

    private DestroyGuiTextures background;

    private EditBox lowerBound;
    private EditBox upperBound;

    private IconButton confirmButton;

    private final VatSideBlockEntity vatSide;

    public QuantityObservingVatSideScreen(VatSideBlockEntity vatSide) {
        super(DestroyLang.translate("tooltip.vat.menu.quantity_observed.title").component());
        background = DestroyGuiTextures.VAT_QUANTITY_OBSERVER;
        this.vatSide = vatSide;
    };

    @Override
    protected void init() {
        setWindowSize(background.width, background.height);
		super.init();
		clearWidgets();

        confirmButton = new IconButton(guiLeft + background.width - 25, guiTop + background.height - 24, AllIcons.I_CONFIRM);
		confirmButton.withCallback(() -> {if (minecraft != null && minecraft.player != null) minecraft.player.closeContainer();}); // It thinks minecraft and player might be null
		addRenderableWidget(confirmButton);

        lowerBound = new EditBox(minecraft.font, guiLeft + 15, guiTop + 35, 70, 10, Component.literal(""+vatSide.redstoneMonitor.lowerThreshold));
        lowerBound.setBordered(false);
        lowerBound.setMaxLength(35);
		lowerBound.setFocused(false);
		lowerBound.mouseClicked(0, 0, 0);
		lowerBound.active = false;
        lowerBound.setTooltip(Tooltip.create(DestroyLang.translate("tooltip.vat.menu.quantity_observed.minimum").component()));

        upperBound = new EditBox(minecraft.font, guiLeft + 171, guiTop + 35, 70, 10, Component.literal(""+vatSide.redstoneMonitor.upperThreshold));
        upperBound.setBordered(false);
        upperBound.setMaxLength(35);
		upperBound.setFocused(false);
		upperBound.mouseClicked(0, 0, 0);
		upperBound.active = false;
        upperBound.setTooltip(Tooltip.create(DestroyLang.translate("tooltip.vat.menu.quantity_observed.maximum").component()));

        addRenderableWidgets(lowerBound, upperBound);
    };

    @Override
    public void tick() {
        super.tick();
        for (EditBox box : List.of(lowerBound, upperBound)) {
            if (getFocused() != box) {
                box.setCursorPosition(box.getValue().length());
                box.setHighlightPos(box.getCursorPosition());

                // Attempt to update the Vat Side with the given number
                boolean upper = box == upperBound;
                float oldValue = upper ? vatSide.redstoneMonitor.upperThreshold : vatSide.redstoneMonitor.lowerThreshold;
                try {
                    float value = Float.valueOf(box.getValue());
                    if (value != oldValue) DestroyMessages.sendToServer(new VatSideQuantityThresholdChangeC2SPacket(upper, value, vatSide.getBlockPos()));
                } catch (NumberFormatException e) {
                    box.setValue(""+oldValue);
                };
            };
        };
    };

    @Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (getFocused() instanceof EditBox && (keyCode == InputConstants.KEY_RETURN || keyCode == InputConstants.KEY_NUMPADENTER)) for (EditBox box : List.of(lowerBound, upperBound)) {
            if (box.isFocused()) {
                box.setFocused(false);
                return true;
            };
        };
		return super.keyPressed(keyCode, scanCode, modifiers);
	};

    @Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (EditBox box : List.of(lowerBound, upperBound)) {
            if (mouseY > guiTop + 27 && mouseY < guiTop + 41 && mouseX > box.getX() - 4 && mouseX < box.getX() + box.getWidth() + 8 && !box.isFocused()) {
                box.setFocused(true);
                box.setHighlightPos(0);
                setFocused(box);
                return true;
            } else {
                box.setFocused(false);
            };
        };
        return super.mouseClicked(mouseX, mouseY, button);
    };

    @Override
    protected void renderWindow(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        background.render(graphics, guiLeft, guiTop);

        graphics.drawString(font, title, guiLeft + 16, guiTop + 4, 0x54214F, false);
    };
    
};
