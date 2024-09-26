package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyElement;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;
import com.petrolpark.destroy.chemistry.legacy.ReadOnlyMixture;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.SingleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.index.group.CarboxylicAcidGroup;
import com.petrolpark.destroy.item.DestroyItems;

public class CarboxylicAcidReduction extends SingleGroupGenericReaction<CarboxylicAcidGroup> {

    public CarboxylicAcidReduction() {
        super(Destroy.asResource("carboxylic_acid_reduction"), DestroyGroupTypes.CARBOXYLIC_ACID);
    };

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        return true; // TODO check for actual oxidant once Magic Reductant is removed
    };

    @Override
    public LegacyReaction generateReaction(GenericReactant<CarboxylicAcidGroup> reactant) {
        CarboxylicAcidGroup acid = reactant.getGroup();
        LegacyMolecularStructure structure = reactant.getMolecule().shallowCopyStructure();
        structure.moveTo(acid.carbon)
            .remove(acid.proton)
            .remove(acid.alcoholOxygen)
            .addAtom(LegacyElement.HYDROGEN);

        return reactionBuilder()
            .addReactant(reactant.getMolecule())
            .addSimpleItemCatalyst(DestroyItems.MAGIC_REDUCTANT::get, 1f)
            .addProduct(moleculeBuilder().structure(structure).build())
            .activationEnergy(25f)
            .build();
    };
    
};
