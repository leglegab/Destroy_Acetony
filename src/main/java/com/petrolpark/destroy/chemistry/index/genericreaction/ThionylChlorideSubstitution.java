package com.petrolpark.destroy.chemistry.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.Element;
import com.petrolpark.destroy.chemistry.Formula;
import com.petrolpark.destroy.chemistry.Reaction;
import com.petrolpark.destroy.chemistry.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.genericreaction.SingleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.index.group.AlcoholGroup;
import com.petrolpark.destroy.chemistry.index.group.UnsubstitutedAmideGroup;

public class ThionylChlorideSubstitution extends SingleGroupGenericReaction<AlcoholGroup> {

    public ThionylChlorideSubstitution() {
        super(Destroy.asResource("thionyl_chloride_substitution"), DestroyGroupTypes.ALCOHOL);
    }

    @Override
    public Reaction generateReaction(GenericReactant<AlcoholGroup> reactant) {
        Formula structure = reactant.getMolecule().shallowCopyStructure();
        AlcoholGroup group = reactant.getGroup();

        structure.moveTo(group.carbon)
            .remove(group.oxygen)
            .remove(group.hydrogen)
            .addGroup(Formula.atom(Element.CHLORINE));

        return reactionBuilder()
            .addReactant(reactant.getMolecule())
            .addReactant(DestroyMolecules.THIONYL_CHLORIDE)
            .addProduct(moleculeBuilder().structure(structure).build())
            .addProduct(DestroyMolecules.HYDROCHLORIC_ACID)
            .addProduct(DestroyMolecules.SULFUR_DIOXIDE)
            //TODO kinetics
            .build();
    };
};
