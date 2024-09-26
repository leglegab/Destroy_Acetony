package com.petrolpark.destroy.chemistry.legacy.index.group;

import com.petrolpark.destroy.chemistry.legacy.LegacyAtom;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroup;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroupType;

public class EsterGroup extends LegacyFunctionalGroup<EsterGroup> {

    private LegacyAtom carbonylCarbon;
    private LegacyAtom alcoholCarbon;
    private LegacyAtom carbonylOxygen;
    private LegacyAtom bridgeOxygen;

    public EsterGroup() {
        super();
    };

    public EsterGroup(LegacyAtom carbonylCarbon, LegacyAtom alcoholCarbon, LegacyAtom carbonylOxygen, LegacyAtom bridgeOxygen) {
        super();
        this.carbonylCarbon = carbonylCarbon;
        this.alcoholCarbon = alcoholCarbon;
        this.carbonylOxygen = carbonylOxygen;
        this.bridgeOxygen = bridgeOxygen;
    };

    public LegacyAtom getCarbonylCarbon() {
        return carbonylCarbon;
    };

    public LegacyAtom getAlcoholCarbon() {
        return alcoholCarbon;
    };

    public LegacyAtom getCarbonylOxygen() {
        return carbonylOxygen;
    };

    public LegacyAtom getBridgeOxygen() {
        return bridgeOxygen;
    };

    @Override
    public LegacyFunctionalGroupType<EsterGroup> getType() {
        return DestroyGroupTypes.ESTER;
    };
    
};
