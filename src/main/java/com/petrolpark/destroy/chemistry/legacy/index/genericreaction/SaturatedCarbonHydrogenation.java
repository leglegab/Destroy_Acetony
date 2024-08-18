package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyElement;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction.ReactionBuilder;
import com.petrolpark.destroy.chemistry.legacy.LegacySpecies;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import com.simibubi.create.AllTags;

public class SaturatedCarbonHydrogenation extends ElectrophilicAddition {

    public SaturatedCarbonHydrogenation(boolean alkyne) {
        super(Destroy.MOD_ID, "hydrogenation", alkyne);
    };

    @Override
    public LegacyMolecularStructure getLowDegreeGroup() {
        return LegacyMolecularStructure.atom(LegacyElement.HYDROGEN);
    };

    @Override
    public LegacyMolecularStructure getHighDegreeGroup() {
        return LegacyMolecularStructure.atom(LegacyElement.HYDROGEN);
    };

    @Override
    public LegacySpecies getElectrophile() {
        return DestroyMolecules.HYDROGEN;
    };

    @Override
    public void transform(ReactionBuilder builder) {
        builder.addSimpleItemTagCatalyst(AllTags.forgeItemTag("dusts/nickel"), 1f);
    };
    
};
