package com.petrolpark.destroy.chemistry.legacy.index.group;

import com.petrolpark.destroy.chemistry.legacy.LegacyAtom;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroup;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroupType;

public class UnsubstitutedAmideGroup extends LegacyFunctionalGroup<UnsubstitutedAmideGroup> {

    public final LegacyAtom carbon;
    public final LegacyAtom oxygen;
    public final LegacyAtom nitrogen;
    public final LegacyAtom hydrogen1;
    public final LegacyAtom hydrogen2;

    public UnsubstitutedAmideGroup(LegacyAtom carbon, LegacyAtom oxygen, LegacyAtom nitrogen, LegacyAtom hydrogen1, LegacyAtom hydrogen2) {
        super();
        this.carbon = carbon;
        this.oxygen = oxygen;
        this.nitrogen = nitrogen;
        this.hydrogen1 = hydrogen1;
        this.hydrogen2 = hydrogen2;
    };

    @Override
    public LegacyFunctionalGroupType<UnsubstitutedAmideGroup> getType() {
        return DestroyGroupTypes.UNSUBSTITUTED_AMIDE;
    };
    
};
