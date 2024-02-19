package com.petrolpark.destroy.chemistry.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.Element;
import com.petrolpark.destroy.chemistry.Formula;
import com.petrolpark.destroy.chemistry.Reaction.ReactionBuilder;
import com.petrolpark.destroy.chemistry.index.DestroyMolecules;

public class ElectrophilicChlorohydrination extends ElectrophilicAddition {

    public ElectrophilicChlorohydrination(boolean alkyne) {
        super(Destroy.MOD_ID, "chlorohydrination", alkyne);
    };

    @Override
    public Formula getLowDegreeGroup() {
        return Formula.atom(Element.CHLORINE);
    };

    @Override
    public Formula getHighDegreeGroup() {
        return Formula.alcohol();
    };

    @Override
    public void transform(ReactionBuilder builder) {
        builder.addReactant(DestroyMolecules.HYPOCHLOROUS_ACID);
    };
    
};
