package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyElement;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;
import com.petrolpark.destroy.chemistry.legacy.ReadOnlyMixture;
import com.petrolpark.destroy.chemistry.legacy.LegacyBond.BondType;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.SingleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.index.group.CarbonylGroup;
import com.petrolpark.destroy.item.DestroyItems;

public class CarbonylReduction extends SingleGroupGenericReaction<CarbonylGroup> {

    public CarbonylReduction() {
        super(Destroy.asResource("carbonyl_reduction"), DestroyGroupTypes.CARBONYL);
    };

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        return true; // TODO check for actual reductant once Magic Reductant is removed
    };

    @Override
    public LegacyReaction generateReaction(GenericReactant<CarbonylGroup> reactant) {
        CarbonylGroup carbonyl = reactant.getGroup();

        LegacyMolecularStructure structure = reactant.getMolecule().shallowCopyStructure();
        structure.moveTo(carbonyl.carbon)
            .addAtom(LegacyElement.HYDROGEN)
            .replaceBondTo(carbonyl.oxygen, BondType.SINGLE)
            .moveTo(carbonyl.oxygen)
            .addAtom(LegacyElement.HYDROGEN);

        return reactionBuilder()
            .addReactant(reactant.getMolecule())
            .addSimpleItemCatalyst(DestroyItems.MAGIC_REDUCTANT::get, 1f)
            .addProduct(moleculeBuilder().structure(structure).build())
            .activationEnergy(200f)
            .build();
    };
    
};
