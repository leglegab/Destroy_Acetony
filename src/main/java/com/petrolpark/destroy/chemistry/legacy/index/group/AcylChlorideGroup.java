package com.petrolpark.destroy.chemistry.legacy.index.group;

import com.petrolpark.destroy.chemistry.legacy.LegacyAtom;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroup;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroupType;

public class AcylChlorideGroup extends LegacyFunctionalGroup<AcylChlorideGroup> {

    private LegacyAtom carbon;
    private LegacyAtom oxygen;
    private LegacyAtom chlorine;

    public AcylChlorideGroup(LegacyAtom carbon, LegacyAtom oxygen, LegacyAtom chlorine) {
        super();
        this.carbon = carbon;
        this.oxygen = oxygen;
        this.chlorine = chlorine;
    };

    public LegacyAtom getCarbon() {
        return carbon;
    };

    public LegacyAtom getOxygen() {
        return oxygen;
    };

    public LegacyAtom getChlorine() {
        return chlorine;
    };

    @Override
    public LegacyFunctionalGroupType<AcylChlorideGroup> getType() {
        return DestroyGroupTypes.ACYL_CHLORIDE;
    };
    
};
