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
import com.petrolpark.destroy.chemistry.legacy.index.group.NitrileGroup;
import com.simibubi.create.AllTags;

public class NitrileHydrogenation extends SingleGroupGenericReaction<NitrileGroup> {

    public NitrileHydrogenation() {
        super(Destroy.asResource("nitrile_hydrogenation"), DestroyGroupTypes.NITRILE);
    };

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        return mixture.getConcentrationOf(DestroyMolecules.HYDROGEN) > 0f;
    };

    @Override
    public LegacyReaction generateReaction(GenericReactant<NitrileGroup> reactant) {
        NitrileGroup group = reactant.getGroup();
        LegacyMolecularStructure structure = reactant.getMolecule().shallowCopyStructure();
        
        structure.moveTo(group.carbon)
            .remove(group.nitrogen)
            .addAtom(LegacyElement.HYDROGEN)
            .addAtom(LegacyElement.HYDROGEN)
            .addGroup(LegacyMolecularStructure.atom(LegacyElement.NITROGEN)
                .addAtom(LegacyElement.HYDROGEN)
                .addAtom(LegacyElement.HYDROGEN)
            );

        return reactionBuilder()
            .addReactant(reactant.getMolecule())
            .addReactant(DestroyMolecules.HYDROGEN, 2)
            .addSimpleItemTagCatalyst(AllTags.forgeItemTag("dusts/nickel"), 1f)
            .addProduct(moleculeBuilder().structure(structure).build())
            .build();
    };
    
};
