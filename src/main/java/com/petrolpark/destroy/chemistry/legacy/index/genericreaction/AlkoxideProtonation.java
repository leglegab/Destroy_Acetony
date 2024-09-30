package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;
import com.petrolpark.destroy.chemistry.legacy.ReadOnlyMixture;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.SingleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.legacy.index.group.AlkoxideGroup;

public class AlkoxideProtonation extends SingleGroupGenericReaction<AlkoxideGroup> {

    public AlkoxideProtonation() {
        super(Destroy.asResource("alkoxide_protonation"), DestroyGroupTypes.ALKOXIDE);
    };

    @Override
    public LegacyReaction generateReaction(GenericReactant<AlkoxideGroup> reactant) {
        LegacyMolecularStructure structure = reactant.getMolecule().shallowCopyStructure();
        structure.moveTo(reactant.group.carbon)
            .remove(reactant.group.oxygen)
            .addGroup(LegacyMolecularStructure.alcohol());
        return reactionBuilder()
            .addReactant(reactant.molecule)
            .addReactant(DestroyMolecules.PROTON)
            .addProduct(moleculeBuilder().structure(structure).build())
            .build();
    };

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        return mixture.getConcentrationOf(DestroyMolecules.PROTON) > 0f;
    };
    
};
