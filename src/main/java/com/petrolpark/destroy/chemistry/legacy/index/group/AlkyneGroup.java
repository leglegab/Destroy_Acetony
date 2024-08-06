package com.petrolpark.destroy.chemistry.legacy.index.group;

import com.petrolpark.destroy.chemistry.legacy.LegacyAtom;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroupType;

public class AlkyneGroup extends SaturatedCarbonGroup {

    public AlkyneGroup(LegacyAtom highDegreeCarbon, LegacyAtom lowDegreeCarbon) {
        super(highDegreeCarbon, lowDegreeCarbon);
    };

    @Override
    public LegacyFunctionalGroupType<? extends SaturatedCarbonGroup> getType() {
        return DestroyGroupTypes.ALKYNE;
    };
    
};
