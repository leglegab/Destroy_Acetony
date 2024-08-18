package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyAtom;
import com.petrolpark.destroy.chemistry.legacy.LegacyElement;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;
import com.petrolpark.destroy.chemistry.legacy.ReadOnlyMixture;
import com.petrolpark.destroy.chemistry.legacy.LegacyBond.BondType;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.SingleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.legacy.index.group.PrimaryAmineGroup;

public class AminePhosgenation extends SingleGroupGenericReaction<PrimaryAmineGroup> {

    public AminePhosgenation() {
        super(Destroy.asResource("amine_phosgenation"), DestroyGroupTypes.PRIMARY_AMINE);
    };

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        return mixture.getConcentrationOf(DestroyMolecules.PHOSGENE) > 0f;
    };

    @Override
    public LegacyReaction generateReaction(GenericReactant<PrimaryAmineGroup> reactant) {
        PrimaryAmineGroup group = reactant.group;
        LegacyMolecularStructure structure = reactant.molecule.shallowCopyStructure();

        LegacyAtom carbon = new LegacyAtom(LegacyElement.CARBON);
        structure.moveTo(group.nitrogen)
            .remove(group.firstHydrogen)
            .remove(group.secondHydrogen)
            .addAtom(carbon, BondType.DOUBLE)
            .moveTo(carbon)
            .addAtom(LegacyElement.OXYGEN, BondType.DOUBLE);
        return reactionBuilder()
            .addReactant(reactant.molecule)
            .addReactant(DestroyMolecules.PHOSGENE)
            .addProduct(DestroyMolecules.HYDROCHLORIC_ACID, 2)
            .addProduct(moleculeBuilder().structure(structure).build())
            .build();

    };
    
};
