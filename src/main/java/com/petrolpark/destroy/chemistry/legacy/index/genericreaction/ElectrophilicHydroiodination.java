package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyElement;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacySpecies;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;

public class ElectrophilicHydroiodination extends ElectrophilicAddition {

    public ElectrophilicHydroiodination(boolean alkyne) {
        super(Destroy.MOD_ID, "hydroiodination", alkyne);
    };

    @Override
    public LegacyMolecularStructure getLowDegreeGroup() {
        return LegacyMolecularStructure.atom(LegacyElement.HYDROGEN);
    };

    @Override
    public LegacyMolecularStructure getHighDegreeGroup() {
        return LegacyMolecularStructure.atom(LegacyElement.IODINE);
    };

    @Override
    public LegacySpecies getElectrophile() {
        return DestroyMolecules.HYDROGEN_IODIDE;
    };
    
};
