package com.petrolpark.destroy.mixin.accessor;

import com.simibubi.create.infrastructure.gui.OpenCreateMenuButton.MenuRows;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@SuppressWarnings("ALL")
@Mixin(MenuRows.class)
public interface MenuRowsCompatFAccessor {

    @Accessor(
            value = "leftButtons",
            remap = false
    )
    public List<String> leftTextKeys();

    @Accessor(
            value = "rightButtons",
            remap = false
    )
    public List<String> rightTextKeys();
};
