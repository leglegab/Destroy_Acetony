package com.petrolpark.destroy.chemistry.legacy.index.group;

import com.petrolpark.destroy.chemistry.legacy.LegacyAtom;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroup;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroupType;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;

public class PrimaryAmineGroup extends LegacyFunctionalGroup<PrimaryAmineGroup> {

    public final LegacyAtom carbon;
    public final LegacyAtom nitrogen;
    public final LegacyAtom firstHydrogen;
    public final LegacyAtom secondHydrogen;

    public PrimaryAmineGroup(LegacyAtom carbon, LegacyAtom nitrogen, LegacyAtom firstHydrogen, LegacyAtom secondHydrogen) {
        this.carbon = carbon;
        this.nitrogen = nitrogen;
        this.firstHydrogen = firstHydrogen;
        this.secondHydrogen = secondHydrogen;
    };

    @Override
    public LegacyFunctionalGroupType<? extends PrimaryAmineGroup> getType() {
        return DestroyGroupTypes.PRIMARY_AMINE;
    };
    
};
