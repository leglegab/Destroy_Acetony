package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction.ReactionBuilder;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.legacy.index.group.HalideGroup;

public class HalideHydroxideSubstitution extends HalideSubstitution {

    public HalideHydroxideSubstitution() {
        super(Destroy.asResource("halide_hydroxide_substitution"));
    }

    @Override
    public LegacyMolecularStructure getSubstitutedGroup() {
        return LegacyMolecularStructure.alcohol();
    };

    @Override
    public void transform(ReactionBuilder builder, HalideGroup group) {
        builder.addReactant(DestroyMolecules.HYDROXIDE, 1, group.degree == 3 ? 0 : 1); // If this is a tertiary chloride, the mechanism is SN1 so hydroxide does not appear in the rate equation
    };
    
};
