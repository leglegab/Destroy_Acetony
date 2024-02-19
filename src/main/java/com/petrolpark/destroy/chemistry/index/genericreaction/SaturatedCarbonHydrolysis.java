package com.petrolpark.destroy.chemistry.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.Element;
import com.petrolpark.destroy.chemistry.Formula;
import com.petrolpark.destroy.chemistry.Reaction.ReactionBuilder;
import com.petrolpark.destroy.chemistry.index.DestroyMolecules;

public class SaturatedCarbonHydrolysis extends ElectrophilicAddition {

    public SaturatedCarbonHydrolysis(boolean alkyne) {
        super(Destroy.MOD_ID, "hydrolysis", alkyne);
    };

    @Override
    public Formula getLowDegreeGroup() {
        return Formula.atom(Element.HYDROGEN);
    };

    @Override
    public Formula getHighDegreeGroup() {
        return Formula.alcohol();
    };

    @Override
    public void transform(ReactionBuilder builder) {
        builder.addReactant(DestroyMolecules.WATER, 1, 0)
            .displayAsReversible()
            .addCatalyst(DestroyMolecules.SULFURIC_ACID, 1)
            .activationEnergy(200f);
    };

    
};
