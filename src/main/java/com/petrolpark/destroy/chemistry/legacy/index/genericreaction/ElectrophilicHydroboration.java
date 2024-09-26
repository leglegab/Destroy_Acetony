package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyElement;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;
import com.petrolpark.destroy.chemistry.legacy.ReadOnlyMixture;
import com.petrolpark.destroy.chemistry.legacy.LegacyBond.BondType;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.DoubleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.index.group.NonTertiaryBoraneGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.SaturatedCarbonGroup;

public class ElectrophilicHydroboration extends DoubleGroupGenericReaction<NonTertiaryBoraneGroup, SaturatedCarbonGroup> {

    public final boolean isForAlkynes;

    public ElectrophilicHydroboration(boolean isForAlkynes) {
        super(Destroy.asResource((isForAlkynes ? "alkyne" : "alkene") + "_hydroboration"), DestroyGroupTypes.NON_TERTIARY_BORANE, DestroyGroupTypes.ALKENE);
        this.isForAlkynes = isForAlkynes;
    };
    
    @Override
    public LegacyReaction generateReaction(GenericReactant<NonTertiaryBoraneGroup> firstReactant, GenericReactant<SaturatedCarbonGroup> secondReactant) {
        LegacyMolecularStructure borane = firstReactant.molecule.shallowCopyStructure();
        NonTertiaryBoraneGroup boraneGroup = firstReactant.group;
        LegacyMolecularStructure alkene = secondReactant.molecule.shallowCopyStructure();
        SaturatedCarbonGroup alkeneGroup = secondReactant.group;
        alkene.moveTo(alkeneGroup.highDegreeCarbon)
            .replaceBondTo(alkeneGroup.lowDegreeCarbon, isForAlkynes ? BondType.DOUBLE : BondType.SINGLE)
            .addAtom(LegacyElement.HYDROGEN)
            .moveTo(alkeneGroup.lowDegreeCarbon);
        borane.moveTo(boraneGroup.boron)
            .remove(boraneGroup.hydrogen);
        LegacyMolecularStructure product = LegacyMolecularStructure.joinFormulae(borane, alkene, BondType.SINGLE);
        return reactionBuilder()
            .addReactant(firstReactant.molecule)
            .addReactant(secondReactant.molecule)
            .addProduct(moleculeBuilder().structure(product).build())
            .build();
    };

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        return true;
    };
    
};
