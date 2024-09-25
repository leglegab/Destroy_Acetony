package com.petrolpark.destroy.chemistry.legacy.index.group;

import com.petrolpark.destroy.chemistry.legacy.LegacyAtom;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroup;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroupType;

public class CarbonylGroup extends LegacyFunctionalGroup<CarbonylGroup> {

    public final LegacyAtom carbon;
    public final LegacyAtom oxygen;

    /**
     * Whether this Carbonyl is a ketone - if it is not a ketone, it is either an aldehyde (with one R group), formaldehye, or unknown.
     */
    public final boolean isKetone;

    public CarbonylGroup(LegacyAtom carbon, LegacyAtom oxygen, Boolean isKetone) {
        super();
        this.carbon = carbon;
        this.oxygen = oxygen;
        this.isKetone = isKetone;
    };

    @Override
    public LegacyFunctionalGroupType<CarbonylGroup> getType() {
        return DestroyGroupTypes.CARBONYL;
    };
    
};
