package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyElement;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacySpecies;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;

public class BoraneElectrophilicHydroboration extends ElectrophilicAddition {

    public BoraneElectrophilicHydroboration(boolean isForAlkynes) {
        super(Destroy.MOD_ID, "borane_hydroboration", isForAlkynes);
    };

    @Override
    public LegacyMolecularStructure getLowDegreeGroup() {
        return LegacyMolecularStructure.atom(LegacyElement.BORON)
            .addAtom(LegacyElement.HYDROGEN)
            .addAtom(LegacyElement.HYDROGEN);
    };

    @Override
    public LegacyMolecularStructure getHighDegreeGroup() {
        return LegacyMolecularStructure.atom(LegacyElement.HYDROGEN);
    };

    @Override
    public LegacySpecies getElectrophile() {
        return DestroyMolecules.DIBORANE;
    };

    @Override
    public int getNucleophileRatio() {
        return 2;
    };
    
};
