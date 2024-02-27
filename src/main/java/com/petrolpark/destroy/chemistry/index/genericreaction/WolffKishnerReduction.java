package com.petrolpark.destroy.chemistry.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.Element;
import com.petrolpark.destroy.chemistry.Formula;
import com.petrolpark.destroy.chemistry.Reaction;
import com.petrolpark.destroy.chemistry.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.genericreaction.SingleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.index.group.CarbonylGroup;

public class WolffKishnerReduction extends SingleGroupGenericReaction<CarbonylGroup> {

    public WolffKishnerReduction() {
        super(Destroy.asResource("wolff_kishner_reduction"), DestroyGroupTypes.CARBONYL);
    };

    @Override
    public Reaction generateReaction(GenericReactant<CarbonylGroup> reactant) {
        CarbonylGroup carbonyl = reactant.getGroup();
        Formula structure = reactant.getMolecule().shallowCopyStructure();

        structure.moveTo(carbonyl.carbon)
            .remove(carbonyl.oxygen)
            .addAtom(Element.HYDROGEN)
            .addAtom(Element.HYDROGEN);
        
        return reactionBuilder()
            .addReactant(reactant.getMolecule())
            .addReactant(DestroyMolecules.HYDRAZINE)
            .addCatalyst(DestroyMolecules.HYDROXIDE, 1)
            .addProduct(moleculeBuilder().structure(structure).build())
            .addProduct(DestroyMolecules.WATER)
            .addProduct(DestroyMolecules.NITROGEN)
            .build();
    };
    
};
