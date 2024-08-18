package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacySpecies;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;

public class HalideHydroxideSubstitution extends HalideSubstitution {

    public HalideHydroxideSubstitution() {
        super(Destroy.asResource("halide_hydroxide_substitution"));
    };

    @Override
    public LegacyMolecularStructure getSubstitutedGroup() {
        return LegacyMolecularStructure.alcohol();
    };

    @Override
    public LegacySpecies getNucleophile() {
        return DestroyMolecules.HYDROXIDE;
    };
    
};
