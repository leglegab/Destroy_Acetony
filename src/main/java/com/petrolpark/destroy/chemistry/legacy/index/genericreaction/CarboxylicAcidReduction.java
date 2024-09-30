package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyElement;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;
import com.petrolpark.destroy.chemistry.legacy.ReadOnlyMixture;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.SingleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.legacy.index.group.CarboxylicAcidGroup;

public class CarboxylicAcidReduction extends SingleGroupGenericReaction<CarboxylicAcidGroup> {

    public CarboxylicAcidReduction() {
        super(Destroy.asResource("carboxylic_acid_reduction"), DestroyGroupTypes.CARBOXYLIC_ACID);
    };

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        return mixture.getConcentrationOf(DestroyMolecules.BOROHYDRIDE) > 0f;
    };

    //TODO replace with some borane nonsense
    @Override
    public LegacyReaction generateReaction(GenericReactant<CarboxylicAcidGroup> reactant) {
        CarboxylicAcidGroup acid = reactant.getGroup();
        LegacyMolecularStructure structure = reactant.getMolecule().shallowCopyStructure();
        structure.moveTo(acid.carbon)
            .remove(acid.proton)
            .remove(acid.alcoholOxygen)
            .addAtom(LegacyElement.HYDROGEN);

        return reactionBuilder()
            .addReactant(reactant.getMolecule(), 4, 1)
            .addReactant(DestroyMolecules.BOROHYDRIDE)
            .addProduct(moleculeBuilder().structure(structure).build(), 4)
            .addProduct(DestroyMolecules.TETRAHYDROXYBORATE)
            .activationEnergy(25f)
            .build();
    };
    
};
