package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyElement;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;
import com.petrolpark.destroy.chemistry.legacy.ReadOnlyMixture;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction.ReactionBuilder;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.SingleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.legacy.index.group.BorateEsterGroup;

public class BorateEsterHydrolysis extends SingleGroupGenericReaction<BorateEsterGroup> {

    public BorateEsterHydrolysis() {
        super(Destroy.asResource("borate_ester_hydrolysis"), DestroyGroupTypes.BORATE_ESTER);
    };

    @Override
    public LegacyReaction generateReaction(GenericReactant<BorateEsterGroup> reactant) {
        LegacyMolecularStructure structure = reactant.getMolecule().shallowCopyStructure();
        BorateEsterGroup borate = reactant.getGroup();
        LegacyMolecularStructure fragment = structure.moveTo(borate.oxygen)
            .cleaveBondTo(borate.boron);
        LegacyMolecularStructure withBoron, noBoron;
        if (structure == fragment) {
            withBoron = noBoron = structure;
        } else if (structure.containsAtom(borate.boron)) {
            withBoron = structure;
            noBoron = fragment;
        } else {
            withBoron = fragment;
            noBoron = structure;
        };
        withBoron.moveTo(borate.boron)
            .addGroup(LegacyMolecularStructure.alcohol());
        noBoron.moveTo(borate.oxygen)
            .addAtom(LegacyElement.HYDROGEN);
        ReactionBuilder reaction = reactionBuilder()
            .addReactant(reactant.getMolecule())
            .addReactant(DestroyMolecules.WATER)
            .addProduct(moleculeBuilder().structure(withBoron).build())
            .displayAsReversible();
        if (structure!= fragment) reaction.addProduct(moleculeBuilder().structure(noBoron).build());
        return reaction.build();
    };

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        return mixture.getConcentrationOf(DestroyMolecules.WATER) > 0f;
    };
    
};
