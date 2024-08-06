package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.SingleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.legacy.index.group.AcylChlorideGroup;

public class AcylChlorideHydrolysis extends SingleGroupGenericReaction<AcylChlorideGroup> {

    public AcylChlorideHydrolysis() {
        super(Destroy.asResource("acyl_chloride_hydrolysis"), DestroyGroupTypes.ACYL_CHLORIDE);
    };

    @Override
    public LegacyReaction generateReaction(GenericReactant<AcylChlorideGroup> reactant) {
        LegacyMolecularStructure structure = reactant.getMolecule().shallowCopyStructure();
        AcylChlorideGroup group = reactant.getGroup();

        structure.moveTo(group.getCarbon())
            .remove(group.getChlorine())
            .addGroup(LegacyMolecularStructure.alcohol(), true);

        return reactionBuilder()
            .addReactant(reactant.getMolecule())
            .addReactant(DestroyMolecules.WATER)
            .addProduct(moleculeBuilder().structure(structure).build())
            .addProduct(DestroyMolecules.HYDROCHLORIC_ACID)
            //TODO kinetics
            .build();
    };

};
