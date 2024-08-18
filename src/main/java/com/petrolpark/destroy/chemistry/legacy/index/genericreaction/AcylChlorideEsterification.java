package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacySpecies;
import com.petrolpark.destroy.chemistry.legacy.ReadOnlyMixture;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;
import com.petrolpark.destroy.chemistry.legacy.LegacyBond.BondType;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.DoubleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.legacy.index.group.AcylChlorideGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.AlcoholGroup;

public class AcylChlorideEsterification extends DoubleGroupGenericReaction<AcylChlorideGroup, AlcoholGroup> {

    public AcylChlorideEsterification() {
        super(Destroy.asResource("acyl_chloride_esterification"), DestroyGroupTypes.ACYL_CHLORIDE, DestroyGroupTypes.ALCOHOL);
    };

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        return true;
    };

    @Override
    public LegacyReaction generateReaction(GenericReactant<AcylChlorideGroup> firstReactant, GenericReactant<AlcoholGroup> secondReactant) {
        LegacyMolecularStructure acylChlorideStructureCopy = firstReactant.getMolecule().shallowCopyStructure();
        AcylChlorideGroup acylChlorideGroup = firstReactant.getGroup();
        LegacyMolecularStructure alcoholStructureCopy = secondReactant.getMolecule().shallowCopyStructure();
        AlcoholGroup alcoholGroup = secondReactant.getGroup();

        alcoholStructureCopy.moveTo(alcoholGroup.oxygen);
        alcoholStructureCopy.remove(alcoholGroup.hydrogen);

        acylChlorideStructureCopy.moveTo(acylChlorideGroup.getCarbon());
        acylChlorideStructureCopy.remove(acylChlorideGroup.getChlorine());
        
        LegacySpecies ester = moleculeBuilder().structure(LegacyMolecularStructure.joinFormulae(acylChlorideStructureCopy, alcoholStructureCopy, BondType.SINGLE)).build();

        return reactionBuilder()
            .addReactant(firstReactant.getMolecule())
            .addReactant(secondReactant.getMolecule())
            .addProduct(ester)
            .addProduct(DestroyMolecules.HYDROCHLORIC_ACID)
            //TODO kinetics
            .build();
    };
    
}
