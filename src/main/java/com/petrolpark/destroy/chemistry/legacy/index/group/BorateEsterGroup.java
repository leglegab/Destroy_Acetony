package com.petrolpark.destroy.chemistry.legacy.index.group;

import com.petrolpark.destroy.chemistry.legacy.LegacyAtom;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroup;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroupType;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;

public class BorateEsterGroup extends LegacyFunctionalGroup<BorateEsterGroup> {

    public final LegacyAtom carbon;
    public final LegacyAtom oxygen;
    public final LegacyAtom boron;

    public BorateEsterGroup(LegacyAtom carbon, LegacyAtom oxygen, LegacyAtom boron) {
        this.carbon = carbon;
        this.oxygen = oxygen;
        this.boron = boron;
    };

    @Override
    public LegacyFunctionalGroupType<? extends BorateEsterGroup> getType() {
        return DestroyGroupTypes.BORATE_ESTER;
    };
    
};
