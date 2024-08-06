package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyElement;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.SingleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.legacy.index.group.IsocyanateGroup;

public class IsocyanateHydrolysis extends SingleGroupGenericReaction<IsocyanateGroup> {

    public IsocyanateHydrolysis() {
        super(Destroy.asResource("isocyanate_hydrolysis"), DestroyGroupTypes.ISOCYANATE);
    };

    @Override
    public LegacyReaction generateReaction(GenericReactant<IsocyanateGroup> reactant) {
        IsocyanateGroup group = reactant.group;
        LegacyMolecularStructure structure = reactant.molecule.shallowCopyStructure();
        structure.moveTo(group.nitrogen)
            .remove(group.oxygen)
            .remove(group.functionalCarbon)
            .addAtom(LegacyElement.HYDROGEN)
            .addAtom(LegacyElement.HYDROGEN);
        return reactionBuilder()
            .addReactant(reactant.molecule)
            .addReactant(DestroyMolecules.WATER)
            .addProduct(DestroyMolecules.CARBON_DIOXIDE)
            .addProduct(moleculeBuilder().structure(structure).build())
            .build();
    };
    
};
