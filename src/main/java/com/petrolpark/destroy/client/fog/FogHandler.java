package com.petrolpark.destroy.client.fog;

import com.simibubi.create.foundation.utility.Color;

public class FogHandler {

    protected static final int FADE_DURATION = 20;

    protected int fade;
    protected Color lastColor;
    protected Color currentColor;
    protected Color nextTickColor;
    protected Color targetColor;

    public void tick() {
        if (fade > 0) {
            fade--;
            currentColor = Color.mixColors(targetColor, lastColor, (float)fade / FADE_DURATION);
            calculateNextTickColor();
        } else {
            lastColor = currentColor = nextTickColor = targetColor;
        };
    };

    public void setColor(Color color) {
        if (color.equals(targetColor)) return;
        fade = FADE_DURATION;
        targetColor = color;
        lastColor = currentColor;
        calculateNextTickColor();
    };

    protected void calculateNextTickColor() {
        nextTickColor = Color.mixColors(targetColor, lastColor, fade == 0 ? 0f : (float)(fade - 1) / FADE_DURATION);
    };

    public int getColor(float partialTicks) {
        return Color.mixColors(currentColor, nextTickColor, partialTicks).getRGB();
    };
};
