package com.petrolpark.destroy.chemistry.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.Element;
import com.petrolpark.destroy.chemistry.Formula;
import com.petrolpark.destroy.chemistry.Reaction.ReactionBuilder;
import com.petrolpark.destroy.chemistry.index.DestroyMolecules;

public class ElectrophilicHydrochlorination extends ElectrophilicAddition {

    public ElectrophilicHydrochlorination(boolean alkyne) {
        super(Destroy.MOD_ID, "hydrochlorination", alkyne);
    };

    @Override
    public Formula getLowDegreeGroup() {
        return Formula.atom(Element.HYDROGEN);
    };

    @Override
    public Formula getHighDegreeGroup() {
        return Formula.atom(Element.CHLORINE);
    };

    @Override
    public void transform(ReactionBuilder builder) {
        builder.addReactant(DestroyMolecules.HYDROCHLORIC_ACID);
    };
    
};
