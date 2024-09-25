package com.petrolpark.destroy.client.model;

import com.petrolpark.destroy.client.model.overrides.UniversalArmorTrimItemOverrides;

import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraftforge.client.model.BakedModelWrapper;

public class UniversalArmorTrimModel extends BakedModelWrapper<BakedModel> {

    private final UniversalArmorTrimItemOverrides trimOverrides;

    public UniversalArmorTrimModel(BakedModel originalModel) {
        super(originalModel);
        trimOverrides = new UniversalArmorTrimItemOverrides(originalModel.getOverrides());
    };

    @Override
    public ItemOverrides getOverrides() {
        return trimOverrides;
    };
    
};
