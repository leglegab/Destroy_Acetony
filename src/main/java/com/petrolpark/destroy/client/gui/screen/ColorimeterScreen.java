package com.petrolpark.destroy.client.gui.screen;

import com.petrolpark.destroy.block.entity.ColorimeterBlockEntity;
import com.petrolpark.destroy.client.gui.DestroyGuiTextures;
import com.petrolpark.destroy.network.DestroyMessages;
import com.petrolpark.destroy.network.packet.RedstoneQuantityMonitorThresholdChangeC2SPacket;
import com.simibubi.create.foundation.utility.Lang;

public class ColorimeterScreen extends AbstractQuantityObservingScreen {

    protected final ColorimeterBlockEntity colorimeter;

    protected ColorimeterScreen(ColorimeterBlockEntity colorimeter) {
        super(Lang.translateDirect("block.destroy.colorimeter"), DestroyGuiTextures.COLORIMETER);
        this.colorimeter = colorimeter;
    };

    @Override
    protected int getEditBoxY() {
        return 130;
    };

    @Override
    protected float getLowerThreshold() {
        return colorimeter.redstoneMonitor.lowerThreshold;
    };

    @Override
    protected float getUpperThreshold() {
        return colorimeter.redstoneMonitor.upperThreshold;
    };

    @Override
    protected void onThresholdChange(boolean upper, float newValue) {
        DestroyMessages.sendToServer(new RedstoneQuantityMonitorThresholdChangeC2SPacket(upper, newValue, colorimeter.getBlockPos()));
    };

};
