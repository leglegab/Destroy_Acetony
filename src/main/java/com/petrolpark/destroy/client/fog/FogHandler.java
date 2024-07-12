package com.petrolpark.destroy.client.fog;

import com.simibubi.create.foundation.utility.Color;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import com.simibubi.create.foundation.utility.animation.LerpedFloat.Chaser;

public class FogHandler {

    protected Color targetColor = new Color(0x00FFFFFF);
    protected Color lastColor = new Color(0x00FFFFFF);
    protected LerpedFloat colorMix = LerpedFloat.linear();

    public void tick() {
        colorMix.tickChaser();
        if (colorMix.getValue() >= 1d) lastColor = targetColor;
    };

    public void setTargetColor(Color color) {
        if (color.equals(targetColor)) return;
        lastColor = Color.mixColors(lastColor, targetColor, colorMix.getValue());
        targetColor = color;
        colorMix.setValue(0d);
        colorMix.chase(1d, 0.2d, Chaser.EXP);
    };

    public Color getColor(float partialTicks) {
        return Color.mixColors(lastColor, targetColor, colorMix.getValue(partialTicks));
    };
};
