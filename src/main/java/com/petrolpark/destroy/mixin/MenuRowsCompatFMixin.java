package com.petrolpark.destroy.mixin;

import com.petrolpark.destroy.util.ButtonsCompatF;
import com.simibubi.create.infrastructure.gui.OpenCreateMenuButton.MenuRows;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(MenuRows.class)
public abstract class MenuRowsCompatFMixin implements ButtonsCompatF {
    @Final
    @Shadow
    protected List<String> leftButtons, rightButtons;

    @Override
    public List<String> destroyh$getLeftButton() {
        return leftButtons;
    };

    @Override
    public List<String> destroyh$getRightButton() {
        return rightButtons;
    };
}
