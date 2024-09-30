package com.petrolpark.destroy.chemistry.legacy.index.group;

import com.petrolpark.destroy.chemistry.legacy.LegacyAtom;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroup;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroupType;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;

public class AlkoxideGroup extends LegacyFunctionalGroup<AlkoxideGroup> {

    public final LegacyAtom carbon;
    public final LegacyAtom oxygen;

    public AlkoxideGroup(LegacyAtom carbon, LegacyAtom oxygen) {
        this.carbon = carbon;
        this.oxygen = oxygen;
    };

    @Override
    public LegacyFunctionalGroupType<? extends AlkoxideGroup> getType() {
        return DestroyGroupTypes.ALKOXIDE;
    };
    
};
