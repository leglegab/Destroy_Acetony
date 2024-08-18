package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyElement;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacySpecies;
import com.petrolpark.destroy.chemistry.legacy.LegacyBond.BondType;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;

public class HalideCyanideSubstitution extends HalideSubstitution {

    public HalideCyanideSubstitution() {
        super(Destroy.asResource("halide_cyanide_substitution"));
    };

    @Override
    public LegacyMolecularStructure getSubstitutedGroup() {
        return LegacyMolecularStructure.atom(LegacyElement.CARBON)
            .addAtom(LegacyElement.NITROGEN, BondType.TRIPLE);
    };

    @Override
    public LegacySpecies getNucleophile() {
        return DestroyMolecules.CYANIDE;
    };
    
};
