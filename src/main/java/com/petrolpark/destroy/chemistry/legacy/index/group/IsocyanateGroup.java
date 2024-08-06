package com.petrolpark.destroy.chemistry.legacy.index.group;

import com.petrolpark.destroy.chemistry.legacy.LegacyAtom;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroup;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroupType;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;

public class IsocyanateGroup extends LegacyFunctionalGroup<IsocyanateGroup> {

    public final LegacyAtom nonFunctionalCarbon;
    public final LegacyAtom nitrogen;
    public final LegacyAtom functionalCarbon;
    public final LegacyAtom oxygen;

    public IsocyanateGroup(LegacyAtom nonFunctionalCarbon, LegacyAtom nitrogen, LegacyAtom functionalCarbon, LegacyAtom oxygen) {
        this.nonFunctionalCarbon = nonFunctionalCarbon;
        this.nitrogen = nitrogen;
        this.functionalCarbon = functionalCarbon;
        this.oxygen = oxygen;
    };

    @Override
    public LegacyFunctionalGroupType<? extends IsocyanateGroup> getType() {
        return DestroyGroupTypes.ISOCYANATE;
    };
    
};
