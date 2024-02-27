package com.petrolpark.destroy.chemistry.index.genericreaction;

import java.util.List;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.Atom;
import com.petrolpark.destroy.chemistry.Element;
import com.petrolpark.destroy.chemistry.Formula;
import com.petrolpark.destroy.chemistry.Reaction;
import com.petrolpark.destroy.chemistry.Bond.BondType;
import com.petrolpark.destroy.chemistry.Reaction.ReactionBuilder;
import com.petrolpark.destroy.chemistry.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.genericreaction.SingleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.index.group.AlcoholGroup;

public class AlcoholDehydration extends SingleGroupGenericReaction<AlcoholGroup> {

    public AlcoholDehydration() {
        super(Destroy.asResource("alcohol_dehydration"), DestroyGroupTypes.ALCOHOL);
    };

    @Override
    public Reaction generateReaction(GenericReactant<AlcoholGroup> reactant) {
        Formula structure = reactant.getMolecule().shallowCopyStructure();
        AlcoholGroup alcohol = reactant.getGroup();

        ReactionBuilder builder = reactionBuilder();

        int products = 0;
        for (Atom carbon : structure.moveTo(alcohol.carbon).getBondedAtomsOfElement(Element.CARBON)) {
            List<Atom> hydrogens = structure.moveTo(carbon).getBondedAtomsOfElement(Element.HYDROGEN);
            List<Atom> carbons = structure.getBondedAtomsOfElement(Element.CARBON);
            List<Atom> rGroups = structure.getBondedAtomsOfElement(Element.R_GROUP);
            if (rGroups.size() + hydrogens.size() + carbons.size() != 4 || hydrogens.size() == 0) continue; // Don't form from non-sp3 alkyl carbons
            Formula productStructure = structure.shallowCopy();
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
    public Reaction generateExampleReaction() {
        Atom carbon = new Atom(Element.CARBON);
        Atom oxygen = new Atom(Element.OXYGEN);
        Atom hydrogen = new Atom(Element.HYDROGEN);
        Atom r1 = new Atom(Element.R_GROUP);
        r1.rGroupNumber = 1;
        Atom r2 = new Atom(Element.R_GROUP);
        r2.rGroupNumber = 2;
        Atom r3 = new Atom(Element.R_GROUP);
        r3.rGroupNumber = 3;
        Atom r4 = new Atom(Element.R_GROUP);
        r4.rGroupNumber = 4;

        return generateReaction(
            new GenericReactant<AlcoholGroup>(
                moleculeBuilder().structure(
                    Formula.atom(Element.CARBON)
                        .addAtom(Element.HYDROGEN)
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
