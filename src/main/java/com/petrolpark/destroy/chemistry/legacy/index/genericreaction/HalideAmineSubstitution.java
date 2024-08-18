package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyAtom;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacySpecies;
import com.petrolpark.destroy.chemistry.legacy.ReadOnlyMixture;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;
import com.petrolpark.destroy.chemistry.legacy.LegacyBond.BondType;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.DoubleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.legacy.index.group.HalideGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.NonTertiaryAmineGroup;

public class HalideAmineSubstitution extends DoubleGroupGenericReaction<HalideGroup, NonTertiaryAmineGroup> {

    public HalideAmineSubstitution() {
        super(Destroy.asResource("halide_amine_substitution"), DestroyGroupTypes.HALIDE, DestroyGroupTypes.NON_TERTIARY_AMINE);
    };

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        return true;
    };

    @Override
    public LegacyReaction generateReaction(GenericReactant<HalideGroup> firstReactant, GenericReactant<NonTertiaryAmineGroup> secondReactant) {
        LegacyMolecularStructure halideStructureCopy = firstReactant.getMolecule().shallowCopyStructure();
        HalideGroup halideGroup = firstReactant.getGroup();
        LegacyMolecularStructure amineStructureCopy = secondReactant.getMolecule().shallowCopyStructure();
        NonTertiaryAmineGroup amineGroup = secondReactant.getGroup();

        halideStructureCopy
            .moveTo(halideGroup.carbon)
            .remove(halideGroup.halogen);

        amineStructureCopy
            .moveTo(amineGroup.nitrogen)
            .remove(amineGroup.hydrogen);

        LegacySpecies substitutedAmine = moleculeBuilder().structure(LegacyMolecularStructure.joinFormulae(halideStructureCopy, amineStructureCopy, BondType.SINGLE)).build();

        return reactionBuilder()
            .addReactant(firstReactant.getMolecule())
            .addReactant(secondReactant.getMolecule(), 1, 2)
            .addProduct(substitutedAmine)
            .addProduct(getIon(halideGroup.halogen))
            .addProduct(DestroyMolecules.PROTON)
            //TODO kinetics
            .build();
    };

    public LegacySpecies getIon(LegacyAtom atom) {
        switch (atom.getElement()) {
            case FLUORINE:
                return DestroyMolecules.FLUORIDE;
            case CHLORINE:
                return DestroyMolecules.CHLORIDE;
            case IODINE:
                return DestroyMolecules.IODIDE;
            default:
                throw new GenericReactionGenerationException(atom.getElement().toString()+" is not a halogen.");
        }
    };
    
};
