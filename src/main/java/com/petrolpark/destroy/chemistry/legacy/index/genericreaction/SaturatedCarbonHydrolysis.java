package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyElement;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction.ReactionBuilder;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;

public class SaturatedCarbonHydrolysis extends ElectrophilicAddition {

    public SaturatedCarbonHydrolysis(boolean alkyne) {
        super(Destroy.MOD_ID, "hydrolysis", alkyne);
    };

    @Override
    public LegacyMolecularStructure getLowDegreeGroup() {
        return LegacyMolecularStructure.atom(LegacyElement.HYDROGEN);
    };

    @Override
    public LegacyMolecularStructure getHighDegreeGroup() {
        return LegacyMolecularStructure.alcohol();
    };

    @Override
    public void transform(ReactionBuilder builder) {
        builder.addReactant(DestroyMolecules.WATER, 1, 0)
            .displayAsReversible()
            .addCatalyst(DestroyMolecules.PROTON, 2)
            .activationEnergy(20f);
    };

    
};
