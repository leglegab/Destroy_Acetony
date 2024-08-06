package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyElement;
import com.petrolpark.destroy.chemistry.legacy.LegacySpecies;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.SingleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.legacy.index.group.CarboxylicAcidGroup;

public class AcylChlorideFormation extends SingleGroupGenericReaction<CarboxylicAcidGroup> {

    public AcylChlorideFormation() {
        super(Destroy.asResource("acyl_chloride_formation"), DestroyGroupTypes.CARBOXYLIC_ACID);
    };

    @Override
    public LegacyReaction generateReaction(GenericReactant<CarboxylicAcidGroup> reactant) {
        LegacySpecies reactantMolecule = reactant.getMolecule();
        CarboxylicAcidGroup acidGroup = reactant.getGroup();

        LegacySpecies productMolecule = moleculeBuilder().structure(reactantMolecule.shallowCopyStructure()
            .moveTo(acidGroup.carbon)
            .remove(acidGroup.alcoholOxygen)
            .remove(acidGroup.proton)
            .addAtom(LegacyElement.CHLORINE)
        ).build();

        return reactionBuilder()
            .addReactant(reactantMolecule)
            .addReactant(DestroyMolecules.PHOSGENE)
            .addProduct(productMolecule)
            .addProduct(DestroyMolecules.HYDROCHLORIC_ACID)
            .addProduct(DestroyMolecules.CARBON_DIOXIDE)
            //TODO kinetic constants
            .build();
    };
    
};
