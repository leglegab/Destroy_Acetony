package com.petrolpark.destroy.client.gui.screen;

import com.petrolpark.destroy.block.entity.VatSideBlockEntity;
import com.petrolpark.destroy.client.gui.DestroyGuiTextures;
import com.petrolpark.destroy.network.DestroyMessages;
import com.petrolpark.destroy.network.packet.RedstoneQuantityMonitorThresholdChangeC2SPacket;
import com.petrolpark.destroy.util.DestroyLang;

public class RedstoneMonitorVatSideScreen extends AbstractQuantityObservingScreen {

    private final VatSideBlockEntity vatSide;

    public RedstoneMonitorVatSideScreen(VatSideBlockEntity vatSide) {
        super(DestroyLang.translate("tooltip.vat.menu.quantity_observed.title").component(), DestroyGuiTextures.VAT_QUANTITY_OBSERVER);
        this.vatSide = vatSide;
    };

    
    @Override
    protected int getEditBoxY() {
        return 35;
    };

    @Override
    protected float getLowerThreshold() {
        return vatSide.redstoneMonitor.lowerThreshold;
    };

    @Override
    protected float getUpperThreshold() {
        return vatSide.redstoneMonitor.upperThreshold;
    };

    @Override
    protected void onThresholdChange(boolean upper, float newValue) {
        DestroyMessages.sendToServer(new RedstoneQuantityMonitorThresholdChangeC2SPacket(upper, newValue, vatSide.getBlockPos()));
    };

};
