package com.petrolpark.destroy.mixin;

import java.util.List;

import com.petrolpark.destroy.util.Buttons;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.simibubi.create.infrastructure.gui.OpenCreateMenuButton.MenuRows;

@Mixin(MenuRows.class)
public abstract class MenuRowsMixin implements Buttons {
    @Final
    @Shadow
    protected List<String> leftTextKeys, rightTextKeys;

    @Override
    public List<String> destroyh$getLeftTextKeys() {
        return leftTextKeys;
    }

    @Override
    public List<String> destroyh$getRightTextKeys() {
        return rightTextKeys;
    }
}
