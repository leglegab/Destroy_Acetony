package com.petrolpark.destroy.chemistry.minecraft;

import com.petrolpark.destroy.chemistry.api.species.SpeciesId;

import net.minecraft.resources.ResourceLocation;

public class Util {
    
    public static final ResourceLocation toRl(SpeciesId speciesId) {
        return new ResourceLocation(speciesId.toString());
    };
};
