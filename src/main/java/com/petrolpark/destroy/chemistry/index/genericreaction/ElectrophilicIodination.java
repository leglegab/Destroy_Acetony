package com.petrolpark.destroy.chemistry.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.Element;
import com.petrolpark.destroy.chemistry.Formula;
import com.petrolpark.destroy.chemistry.Reaction.ReactionBuilder;
import com.petrolpark.destroy.chemistry.index.DestroyMolecules;

public class ElectrophilicIodination extends ElectrophilicAddition {

    public ElectrophilicIodination(boolean alkyne) {
        super(Destroy.MOD_ID, "iodination", alkyne);
    };

    @Override
    public Formula getLowDegreeGroup() {
        return Formula.atom(Element.IODINE);
    };

    @Override
    public Formula getHighDegreeGroup() {
        return Formula.atom(Element.IODINE);
    };

    @Override
    public void transform(ReactionBuilder builder) {
        builder.addReactant(DestroyMolecules.IODINE);
    };
    
};
