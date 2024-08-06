package com.petrolpark.destroy.chemistry.legacy.index.group;

import com.petrolpark.destroy.chemistry.legacy.LegacyAtom;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroup;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroupType;

public class NonTertiaryAmineGroup extends LegacyFunctionalGroup<NonTertiaryAmineGroup> {

    public final LegacyAtom carbon;
    public final LegacyAtom nitrogen;
    public final LegacyAtom hydrogen;

    public NonTertiaryAmineGroup(LegacyAtom carbon, LegacyAtom nitrogen, LegacyAtom hydrogen) {
        this.carbon = carbon;
        this.nitrogen = nitrogen;
        this.hydrogen = hydrogen;
    };

    @Override
    public LegacyFunctionalGroupType<NonTertiaryAmineGroup> getType() {
        return DestroyGroupTypes.NON_TERTIARY_AMINE;
    };
    
};
