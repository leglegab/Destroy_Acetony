package com.petrolpark.destroy.chemistry.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.Element;
import com.petrolpark.destroy.chemistry.Formula;
import com.petrolpark.destroy.chemistry.Reaction.ReactionBuilder;
import com.petrolpark.destroy.chemistry.index.DestroyMolecules;

public class ElectrophilicHydroiodination extends ElectrophilicAddition {

    public ElectrophilicHydroiodination(boolean alkyne) {
        super(Destroy.MOD_ID, "hydroiodination", alkyne);
    };

    @Override
    public Formula getLowDegreeGroup() {
        return Formula.atom(Element.HYDROGEN);
    };

    @Override
    public Formula getHighDegreeGroup() {
        return Formula.atom(Element.IODINE);
    };

    @Override
    public void transform(ReactionBuilder builder) {
        builder.addReactant(DestroyMolecules.HYDROGEN_IODIDE);
    };
    
};
