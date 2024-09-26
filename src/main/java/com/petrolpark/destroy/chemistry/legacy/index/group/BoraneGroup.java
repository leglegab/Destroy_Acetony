package com.petrolpark.destroy.chemistry.legacy.index.group;

import com.petrolpark.destroy.chemistry.legacy.LegacyAtom;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroup;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroupType;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;

public class BoraneGroup extends LegacyFunctionalGroup<BoraneGroup> {

    public final LegacyAtom carbon;
    public final LegacyAtom boron;

    public BoraneGroup(LegacyAtom carbon, LegacyAtom boron) {
        this.carbon = carbon;
        this.boron = boron;
    };

    @Override
    public LegacyFunctionalGroupType<? extends BoraneGroup> getType() {
        return DestroyGroupTypes.BORANE;
    };
    
};
