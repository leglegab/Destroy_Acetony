package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyElement;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacySpecies;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;

public class ElectrophilicChlorohydrination extends ElectrophilicAddition {

    public ElectrophilicChlorohydrination(boolean alkyne) {
        super(Destroy.MOD_ID, "chlorohydrination", alkyne);
    };

    @Override
    public LegacyMolecularStructure getLowDegreeGroup() {
        return LegacyMolecularStructure.atom(LegacyElement.CHLORINE);
    };

    @Override
    public LegacyMolecularStructure getHighDegreeGroup() {
        return LegacyMolecularStructure.alcohol();
    };

    @Override
    public LegacySpecies getElectrophile() {
        return DestroyMolecules.HYPOCHLOROUS_ACID;
    };
    
};
