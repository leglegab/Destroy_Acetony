package com.petrolpark.destroy.chemistry.legacy.index.group;

import com.petrolpark.destroy.chemistry.legacy.LegacyAtom;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroup;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroupType;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;

public class NitroGroup extends LegacyFunctionalGroup<NitroGroup> {

    public final LegacyAtom carbon;
    public final LegacyAtom nitrogen;
    public final LegacyAtom firstOxygen;
    public final LegacyAtom secondOxygen;

    public NitroGroup(LegacyAtom carbon, LegacyAtom nitrogen, LegacyAtom firstOxygen, LegacyAtom secondOxygen) {
        this.carbon = carbon;
        this.nitrogen = nitrogen;
        this.firstOxygen = firstOxygen;
        this.secondOxygen = secondOxygen;
    };

    @Override
    public LegacyFunctionalGroupType<? extends NitroGroup> getType() {
        return DestroyGroupTypes.NITRO;
    };
    
};
