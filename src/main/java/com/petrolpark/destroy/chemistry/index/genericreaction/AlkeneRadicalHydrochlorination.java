package com.petrolpark.destroy.chemistry.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.Element;
import com.petrolpark.destroy.chemistry.Formula;
import com.petrolpark.destroy.chemistry.Reaction.ReactionBuilder;
import com.petrolpark.destroy.chemistry.index.DestroyMolecules;

public class AlkeneRadicalHydrochlorination extends AlkeneAddition {

    public AlkeneRadicalHydrochlorination() {
        super(Destroy.asResource("alkene_radical_hydrochlorination"));
    };

    @Override
    public Formula getLowDegreeGroup() {
        return Formula.atom(Element.CHLORINE);
    };

    @Override
    public Formula getHighDegreeGroup() {
        return Formula.atom(Element.HYDROGEN);
    };

    @Override
    public void transform(ReactionBuilder builder) {
        builder.addReactant(DestroyMolecules.HYDROCHLORIC_ACID, 1, 1)
            .addCatalyst(DestroyMolecules.HYDROGEN_PEROXIDE, 1)
            .requireUV()
            .activationEnergy(30f);
    };

    
};
