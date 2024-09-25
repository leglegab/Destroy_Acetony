package com.petrolpark.destroy.chemistry.legacy.index.group;

import com.petrolpark.destroy.chemistry.legacy.LegacyAtom;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroup;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroupType;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;

public class NonTertiaryBoraneGroup extends LegacyFunctionalGroup<NonTertiaryBoraneGroup> {

    public final LegacyAtom carbon;
    public final LegacyAtom boron;
    public final LegacyAtom hydrogen;

    public NonTertiaryBoraneGroup(LegacyAtom carbon, LegacyAtom boron, LegacyAtom hydrogen) {
        this.carbon = carbon;
        this.boron = boron;
        this.hydrogen = hydrogen;
    };

    @Override
    public LegacyFunctionalGroupType<NonTertiaryBoraneGroup> getType() {
        return DestroyGroupTypes.NON_TERTIARY_BORANE;
    };
    
};
