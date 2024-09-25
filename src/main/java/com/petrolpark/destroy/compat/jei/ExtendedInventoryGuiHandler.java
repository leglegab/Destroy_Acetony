package com.petrolpark.destroy.compat.jei;

import java.util.Collection;

import com.petrolpark.destroy.DestroyClient;

import mezz.jei.api.gui.handlers.IGlobalGuiHandler;
import net.minecraft.client.renderer.Rect2i;

public class ExtendedInventoryGuiHandler implements IGlobalGuiHandler {
    
    @Override
    public Collection<Rect2i> getGuiExtraAreas() {
        return DestroyClient.EXTENDED_INVENTORY_HANDLER.getGuiExtraAreas();
    };
};
