package com.petrolpark.destroy.item.renderer;

import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;

import net.minecraft.client.resources.model.BakedModel;

public class UniversalTrimWrapperModel extends CustomRenderedItemModel {

    public UniversalTrimWrapperModel(BakedModel originalModel) {
        super(originalModel);
        originalModel.getOverrides();
    };
    
};
