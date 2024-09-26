package com.petrolpark.destroy.chemistry.legacy.genericreaction;

import java.util.HashSet;
import java.util.Set;

import org.jetbrains.annotations.NotNull;

import com.petrolpark.destroy.chemistry.api.error.ChemistryException;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;
import com.petrolpark.destroy.chemistry.legacy.ReadOnlyMixture;
import com.petrolpark.destroy.chemistry.legacy.LegacySpecies.MoleculeBuilder;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction.ReactionBuilder;

import net.minecraft.resources.ResourceLocation;

public abstract class GenericReaction {

    /**
     * The set of all Generic Reactions known to Destroy.
     */
    public static Set<GenericReaction> GENERIC_REACTIONS = new HashSet<>();

    /**
     * The identifier for this Generic Reaction, which JEI uses to find the title and description.
     */
    public final ResourceLocation id;

    /**
     * The example Reaction to be displayed in JEI.
     */
    private LegacyReaction exampleReaction;

    public GenericReaction(ResourceLocation id) {
        this.id = id;
    };

    public abstract boolean involvesSingleGroup();

    /**
     * Whether all necessary catalysts and non-generic Reactants are present. No need to check for presence of generic Reactants.
     * @param mixture
     * @return {@code true} to go on and calculate actual Reactions for generic Reactants in this Mixture
     */
    public abstract boolean isPossibleIn(ReadOnlyMixture mixture);

    public LegacyReaction getExampleReaction() {
        if (exampleReaction == null) exampleReaction = generateExampleReaction();
        if (exampleReaction == null) throw new GenericReactionGenerationException("Could not generate example reaction for Generic Reaction '"+ id.toString() + "'- reaction generator returned null, which is only allowed in Mixtures.");
        return exampleReaction;
    };

    /**
     * Generate a Reaction to be used to exemplify this Reaction. There should not usually be any need to override this.
     * The {@link com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroupType groups} of the resultant {@link com.petrolpark.destroy.chemistry.legacy.LegacySpecies#isNovel novel}
     * {@link com.petrolpark.destroy.chemistry.legacy.LegacySpecies Molecules} are used to determine what functional groups this Generic Reaction produces.
     */
    @NotNull
    protected abstract LegacyReaction generateExampleReaction();

    protected static MoleculeBuilder moleculeBuilder() {
        return new MoleculeBuilder("novel");
    };

    protected static ReactionBuilder reactionBuilder() {
        return LegacyReaction.generatedReactionBuilder();
    };

    /**
     * Instantiate a {@link com.petrolpark.destroy.chemistry.api.error.ChemistryException Chemistry Exception}. These kinds of exceptions are
     * ignored when a Mixture is generating generic Reactions.
     * @param string The message of the exception. The identifier of this Generic Reaction will be prepended if this error is logged.
     */
    protected GenericReactionGenerationException exception(String string) {
        return new GenericReactionGenerationException("Problem generating " + (involvesSingleGroup() ? "single" : "double") + "-Group Generic Reaction '" + id.toString() + "': " + string);
    };

    public class GenericReactionGenerationException extends ChemistryException {

        public GenericReactionGenerationException(String message) {
            super(message);
        };

    };

};
