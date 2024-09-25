package com.petrolpark.destroy.item.tooltip;

import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public abstract class DestroyTooltipComponent <T extends DestroyTooltipComponent<?,?>, C extends ClientTooltipComponent> implements TooltipComponent {
    
    public abstract C getClientTooltipComponent();
};
