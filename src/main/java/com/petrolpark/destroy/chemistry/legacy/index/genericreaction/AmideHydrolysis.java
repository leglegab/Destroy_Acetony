package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;
import com.petrolpark.destroy.chemistry.legacy.ReadOnlyMixture;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.SingleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.legacy.index.group.UnsubstitutedAmideGroup;

public class AmideHydrolysis extends SingleGroupGenericReaction<UnsubstitutedAmideGroup> {
    
    public AmideHydrolysis() {
        super(Destroy.asResource("amide_hydrolysis"), DestroyGroupTypes.UNSUBSTITUTED_AMIDE);
    };

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        return mixture.getConcentrationOf(DestroyMolecules.WATER) > 0f;
    };

    @Override
    public LegacyReaction generateReaction(GenericReactant<UnsubstitutedAmideGroup> reactant) {
        LegacyMolecularStructure structure = reactant.getMolecule().shallowCopyStructure();
        UnsubstitutedAmideGroup group = reactant.getGroup();

        structure.moveTo(group.carbon)
            .remove(group.hydrogen1)
            .remove(group.hydrogen2)
            .remove(group.nitrogen)
            .addGroup(LegacyMolecularStructure.alcohol());

        return reactionBuilder()
            .addReactant(reactant.getMolecule())
            .addReactant(DestroyMolecules.WATER)
            .addCatalyst(DestroyMolecules.PROTON, 1)
            .addProduct(moleculeBuilder().structure(structure).build())
            .addProduct(DestroyMolecules.AMMONIA)
            //TODO kinetics
            .build();
    };
};
