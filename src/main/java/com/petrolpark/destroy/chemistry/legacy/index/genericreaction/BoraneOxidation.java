package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyAtom;
import com.petrolpark.destroy.chemistry.legacy.LegacyElement;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;
import com.petrolpark.destroy.chemistry.legacy.ReadOnlyMixture;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.SingleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.legacy.index.group.BoraneGroup;

public class BoraneOxidation extends SingleGroupGenericReaction<BoraneGroup> {

    public BoraneOxidation() {
        super(Destroy.asResource("borane_oxidation"), DestroyGroupTypes.BORANE);
    };

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        return mixture.getConcentrationOf(DestroyMolecules.HYDROGEN_PEROXIDE) > 0f;
    };

    @Override
    public LegacyReaction generateReaction(GenericReactant<BoraneGroup> reactant) {
        LegacyMolecularStructure structure = reactant.getMolecule().shallowCopyStructure();
        structure.insertBridgingAtom(reactant.group.carbon, reactant.group.boron, new LegacyAtom(LegacyElement.OXYGEN));
        return reactionBuilder()
            .addReactant(reactant.molecule)
            .addReactant(DestroyMolecules.HYDROGEN_PEROXIDE)
            .addCatalyst(DestroyMolecules.HYDROXIDE, 1)
            .addProduct(moleculeBuilder().structure(structure).build())
            .addProduct(DestroyMolecules.WATER)
            .build();
    };
    
};
