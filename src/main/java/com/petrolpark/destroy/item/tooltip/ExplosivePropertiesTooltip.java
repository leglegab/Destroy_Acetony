package com.petrolpark.destroy.item.tooltip;

import java.util.Map.Entry;

import com.mojang.blaze3d.vertex.PoseStack;
import com.petrolpark.destroy.client.gui.DestroyGuiTextures;
import com.petrolpark.destroy.world.explosion.ExplosiveProperties;
import com.petrolpark.destroy.world.explosion.ExplosiveProperties.ExplosiveProperty;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;

public class ExplosivePropertiesTooltip extends DestroyTooltipComponent<ExplosivePropertiesTooltip, ExplosivePropertiesTooltip.ClientExplosivePropertiesTooltip>  {

    private final ExplosiveProperties properties;

    public ExplosivePropertiesTooltip(ExplosiveProperties properties) {
        this.properties = properties;
    };
    
    @Override
    public ClientExplosivePropertiesTooltip getClientTooltipComponent() {
        return new ClientExplosivePropertiesTooltip();
    };

    public class ClientExplosivePropertiesTooltip implements ClientTooltipComponent {

        @Override
        public int getHeight() {
            return 77;
        };

        @Override
        public int getWidth(Font pFont) {
            return 76;
        };

        @Override
        public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
            PoseStack ms = guiGraphics.pose();
            ms.pushPose();
            ms.translate(x, y, 250f);
            renderProperties(properties, font, guiGraphics);
            ms.popPose();
        };

    };

    public static void renderProperties(ExplosiveProperties properties, Font font, GuiGraphics graphics) {
        DestroyGuiTextures.CUSTOM_EXPLOSIVE_CHART.render(graphics, 0, 0);
        int y = 3;
        for (Entry<ExplosiveProperty, Float> entry : properties.entrySet()) {
            for (int i = 0; i < Math.abs(entry.getValue()); i++) {
                DestroyGuiTextures.CUSTOM_EXPLOSIVE_BAR.render(graphics, 37 + (8 + 3 * i) * (int)(Math.signum(entry.getValue())), y + 3);
            };
            graphics.drawString(font, entry.getKey().getSymbol(), 34, y + 1, 0xFFFFFF);
            y += 15;
        };
    };
};
