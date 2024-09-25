package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyElement;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;
import com.petrolpark.destroy.chemistry.legacy.ReadOnlyMixture;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.SingleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.legacy.index.group.NitroGroup;
import com.simibubi.create.AllTags;

public class NitroHydrogenation extends SingleGroupGenericReaction<NitroGroup> {

    public NitroHydrogenation() {
        super(Destroy.asResource("nitro_hydrogenation"), DestroyGroupTypes.NITRO);
    };

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        return mixture.getConcentrationOf(DestroyMolecules.HYDROGEN) > 0f;
    };

    @Override
    public LegacyReaction generateReaction(GenericReactant<NitroGroup> reactant) {
        NitroGroup group = reactant.getGroup();
        LegacyMolecularStructure structure = reactant.molecule.shallowCopyStructure();
        structure.moveTo(group.nitrogen)
            .remove(group.firstOxygen)
            .remove(group.secondOxygen)
            .addAtom(LegacyElement.HYDROGEN)
            .addAtom(LegacyElement.HYDROGEN);
        return reactionBuilder()
            .addReactant(reactant.getMolecule())
            .addReactant(DestroyMolecules.HYDROGEN, 3)
            .addProduct(DestroyMolecules.WATER, 2)
            .addProduct(moleculeBuilder().structure(structure).build())
            .addSimpleItemTagCatalyst(AllTags.forgeItemTag("dusts/palladium"), 1.0f)
            .build();
    };
    
};
