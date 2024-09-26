package com.petrolpark.destroy.chemistry.legacy.index.group;

import com.petrolpark.destroy.chemistry.legacy.LegacyAtom;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroup;

public abstract class SaturatedCarbonGroup extends LegacyFunctionalGroup<SaturatedCarbonGroup> {

    public final LegacyAtom highDegreeCarbon;
    public final LegacyAtom lowDegreeCarbon;

    public SaturatedCarbonGroup(LegacyAtom highDegreeCarbon, LegacyAtom lowDegreeCarbon) {
        super();
        this.highDegreeCarbon = highDegreeCarbon;
        this.lowDegreeCarbon = lowDegreeCarbon;
    };

};
