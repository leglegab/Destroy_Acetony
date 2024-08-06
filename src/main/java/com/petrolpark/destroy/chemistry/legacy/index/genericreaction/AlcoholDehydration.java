package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import java.util.List;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyAtom;
import com.petrolpark.destroy.chemistry.legacy.LegacyElement;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;
import com.petrolpark.destroy.chemistry.legacy.LegacyBond.BondType;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction.ReactionBuilder;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.SingleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.legacy.index.group.AlcoholGroup;

public class AlcoholDehydration extends SingleGroupGenericReaction<AlcoholGroup> {

    public AlcoholDehydration() {
        super(Destroy.asResource("alcohol_dehydration"), DestroyGroupTypes.ALCOHOL);
    };

    @Override
    public LegacyReaction generateReaction(GenericReactant<AlcoholGroup> reactant) {
        LegacyMolecularStructure structure = reactant.getMolecule().shallowCopyStructure();
        AlcoholGroup alcohol = reactant.getGroup();

        ReactionBuilder builder = reactionBuilder();

        int products = 0;
        for (LegacyAtom carbon : structure.moveTo(alcohol.carbon).getBondedAtomsOfElement(LegacyElement.CARBON)) {
            List<LegacyAtom> hydrogens = structure.moveTo(carbon).getBondedAtomsOfElement(LegacyElement.HYDROGEN);
            List<LegacyAtom> carbons = structure.getBondedAtomsOfElement(LegacyElement.CARBON);
            List<LegacyAtom> rGroups = structure.getBondedAtomsOfElement(LegacyElement.R_GROUP);
            if (rGroups.size() + hydrogens.size() + carbons.size() != 4 || hydrogens.size() == 0) continue; // Don't form from non-sp3 alkyl carbons
            LegacyMolecularStructure productStructure = structure.shallowCopy();
            productStructure
                .remove(hydrogens.get(0))
                .remove(alcohol.oxygen)
                .remove(alcohol.hydrogen)
                .moveTo(carbon)
                .replaceBondTo(alcohol.carbon, BondType.DOUBLE);
            builder.addProduct(moleculeBuilder().structure(productStructure).build());
            products++;
        };

        if (products == 0) return null;

        builder
            .addReactant(reactant.getMolecule(), products)
            .addReactant(DestroyMolecules.OLEUM, products)
            .addProduct(DestroyMolecules.SULFURIC_ACID, products * 2);

        return builder.build();
    };

    @Override
    public LegacyReaction generateExampleReaction() {
        LegacyAtom carbon = new LegacyAtom(LegacyElement.CARBON);
        LegacyAtom oxygen = new LegacyAtom(LegacyElement.OXYGEN);
        LegacyAtom hydrogen = new LegacyAtom(LegacyElement.HYDROGEN);
        LegacyAtom r1 = new LegacyAtom(LegacyElement.R_GROUP);
        r1.rGroupNumber = 1;
        LegacyAtom r2 = new LegacyAtom(LegacyElement.R_GROUP);
        r2.rGroupNumber = 2;
        LegacyAtom r3 = new LegacyAtom(LegacyElement.R_GROUP);
        r3.rGroupNumber = 3;
        LegacyAtom r4 = new LegacyAtom(LegacyElement.R_GROUP);
        r4.rGroupNumber = 4;

        return generateReaction(
            new GenericReactant<AlcoholGroup>(
                moleculeBuilder().structure(
                    LegacyMolecularStructure.atom(LegacyElement.CARBON)
                        .addAtom(LegacyElement.HYDROGEN)
                        .addAtom(r1)
                        .addAtom(r2)
                        .addAtom(carbon)
                        .moveTo(carbon)
                        .addAtom(r3)
                        .addAtom(r4)
                        .addAtom(oxygen)
                        .moveTo(oxygen)
                        .addAtom(hydrogen)
                ).build(),
                new AlcoholGroup(carbon, oxygen, hydrogen, 2)
            )
        );
    };
    
};
