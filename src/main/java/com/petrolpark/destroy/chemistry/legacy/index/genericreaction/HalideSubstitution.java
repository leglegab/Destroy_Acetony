package com.petrolpark.destroy.chemistry.legacy.index.genericreaction;

import com.petrolpark.destroy.chemistry.legacy.LegacyAtom;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacySpecies;
import com.petrolpark.destroy.chemistry.legacy.ReadOnlyMixture;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction.ReactionBuilder;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.legacy.genericreaction.SingleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyMolecules;
import com.petrolpark.destroy.chemistry.legacy.index.group.HalideGroup;

import net.minecraft.resources.ResourceLocation;

public abstract class HalideSubstitution extends SingleGroupGenericReaction<HalideGroup> {

    public HalideSubstitution(ResourceLocation id) {
        super(id, DestroyGroupTypes.HALIDE);
    };

    @Override
    public boolean isPossibleIn(ReadOnlyMixture mixture) {
        LegacySpecies nucleophile = getNucleophile();
        if (nucleophile != null) return mixture.getConcentrationOf(nucleophile) > 0f;
        return true;
    };

    @Override
    public LegacyReaction generateReaction(GenericReactant<HalideGroup> reactant) {
        LegacySpecies reactantMolecule = reactant.getMolecule();
        HalideGroup halideGroup = reactant.getGroup();
        LegacySpecies productMolecule = moleculeBuilder().structure(reactantMolecule.shallowCopyStructure()
            .moveTo(halideGroup.carbon)
            .addGroup(getSubstitutedGroup(), true)
            .remove(halideGroup.halogen)
        )
        .build();
        ReactionBuilder builder = reactionBuilder()
            .addReactant(reactantMolecule)
            .addProduct(productMolecule)
            .addProduct(getIon(halideGroup.halogen));
        LegacySpecies nucleophile = getNucleophile();
        if (nucleophile != null) builder.addReactant(nucleophile, 1, halideGroup.degree == 3 ? 0 : 1);
        transform(builder, halideGroup);
        return builder.build();
    };

    /**
     * The nucleophile substituting the carbon group.
     * @param builder
     * @return {@code null} to not add a species to the generated Reaction or Reaction requirements
     */
    public abstract LegacySpecies getNucleophile();

    public abstract LegacyMolecularStructure getSubstitutedGroup();

    /**
     * Add any other necessary products, reactants, catalysts and rate constants to the Reaction.
     * @param builder The builder with the Halide reactant, organic product and halide ion product already added
     */
    public void transform(ReactionBuilder builder, HalideGroup group) {};

    public LegacySpecies getIon(LegacyAtom atom) {
        switch (atom.getElement()) {
            case FLUORINE:
                return DestroyMolecules.FLUORIDE;
            case CHLORINE:
                return DestroyMolecules.CHLORIDE;
            case IODINE:
                return DestroyMolecules.IODIDE;
            default:
                throw new GenericReactionGenerationException(atom.getElement().toString()+" is not a halogen.");
        }
    };
    
};
