package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyBond.BondType;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;
import com.petrolpark.destroy.chemistry.legacy.ReadOnlyMixture;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.DoubleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.legacy.index.group.AlcoholGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.BoricAcidGroup;

public class BorateEsterification extends DoubleGroupGenericReaction<BoricAcidGroup, AlcoholGroup> {

    public BorateEsterification() {
        super(Destroy.asResource("borate_esterification"), DestroyGroupTypes.BORIC_ACID, DestroyGroupTypes.ALCOHOL);
    };

    @Override
    public LegacyReaction generateReaction(GenericReactant<BoricAcidGroup> firstReactant, GenericReactant<AlcoholGroup> secondReactant) {
        LegacyMolecularStructure boricAcid = firstReactant.molecule.shallowCopyStructure();
        LegacyMolecularStructure alcohol = secondReactant.molecule.shallowCopyStructure();
        boricAcid.moveTo(firstReactant.group.boron)
            .remove(firstReactant.group.hydrogen)
            .remove(firstReactant.group.oxygen);
        alcohol.moveTo(secondReactant.group.oxygen)
            .remove(secondReactant.group.hydrogen);
        LegacyMolecularStructure product = LegacyMolecularStructure.joinFormulae(boricAcid, alcohol, BondType.SINGLE);
        return reactionBuilder()
            .addReactant(firstReactant.molecule)
            .addReactant(secondReactant.molecule)
            .addProduct(moleculeBuilder().structure(product).build())
            .addProduct(DestroyMolecules.WATER)
            .displayAsReversible()
            .build();
    };

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        return true;
    };
    
};
