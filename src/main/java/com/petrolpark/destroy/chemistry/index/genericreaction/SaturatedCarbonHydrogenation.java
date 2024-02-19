package com.petrolpark.destroy.chemistry.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.Element;
import com.petrolpark.destroy.chemistry.Formula;
import com.petrolpark.destroy.chemistry.Reaction.ReactionBuilder;
import com.petrolpark.destroy.chemistry.index.DestroyMolecules;
import com.simibubi.create.AllTags;

public class SaturatedCarbonHydrogenation extends ElectrophilicAddition {

    public SaturatedCarbonHydrogenation(boolean alkyne) {
        super(Destroy.MOD_ID, "hydrogenation", alkyne);
    };

    @Override
    public Formula getLowDegreeGroup() {
        return Formula.atom(Element.HYDROGEN);
    };

    @Override
    public Formula getHighDegreeGroup() {
        return Formula.atom(Element.HYDROGEN);
    };

    @Override
    public void transform(ReactionBuilder builder) {
        builder.addReactant(DestroyMolecules.HYDROGEN)
            .addSimpleItemTagCatalyst(AllTags.forgeItemTag("dusts/nickel"), 1f);
    };
    
};
