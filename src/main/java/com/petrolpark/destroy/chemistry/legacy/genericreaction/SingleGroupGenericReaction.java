package com.petrolpark.destroy.chemistry.legacy.genericreaction;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.chemistry.legacy.LegacyAtom;
import com.petrolpark.destroy.chemistry.legacy.LegacyElement;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroup;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroupType;
import com.petrolpark.destroy.chemistry.legacy.LegacySpecies;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;

import net.minecraft.resources.ResourceLocation;

public abstract class SingleGroupGenericReaction<G extends LegacyFunctionalGroup<G>> extends GenericReaction {

    protected final LegacyFunctionalGroupType<G> type;

    public SingleGroupGenericReaction(ResourceLocation id, LegacyFunctionalGroupType<G> type) {
        super(id);
        this.type = type;;
        LegacyFunctionalGroup.groupTypesAndReactions.get(type).add(this);
        GENERIC_REACTIONS.add(this); // Add this to the list of all known Generic Reactions
    };

    /**
     * Generates a Reaction (with non-abstract Reactant and Products) based on the given Molecule which has this Group.
     * @return The whole Reaction, including the defined structures of the product(s). Return {@code null} if the Reaction is impossible.
     */
    public abstract LegacyReaction generateReaction(GenericReactant<G> reactant);

    public final LegacyFunctionalGroupType<G> getGroupType() {
        return type;
    };

    @Override
    public final boolean involvesSingleGroup() {
        return true;
    };

    /**
     * Get the Reaction of the {@link LegacyFunctionalGroupType#getExampleMolecule example Molecule} of the {@link SingleGroupGenericReaction#getGroupType Group Type} of this 
     * generic Reaction. This re-generates the Reaction each time, so use this method minimally or cache the result.
     */
    @Override
    @SuppressWarnings("unchecked")
    public LegacyReaction generateExampleReaction() {
        LegacySpecies exampleMolecule = getGroupType().getExampleMolecule();
        int i = 1;
        LegacyMolecularStructure copiedStructure = exampleMolecule.shallowCopyStructure();
        for (LegacyAtom atom : exampleMolecule.getAtoms()) {
            if (atom.getElement() == LegacyElement.R_GROUP) {
                LegacyAtom newAtom = new LegacyAtom(LegacyElement.R_GROUP);
                newAtom.rGroupNumber = i;
                copiedStructure.replace(atom, newAtom);
                i++;
            };
        };
        copiedStructure.refreshFunctionalGroups();
        LegacySpecies copiedExampleMolecule = moleculeBuilder()
            .structure(copiedStructure)
            .build();
        for (LegacyFunctionalGroup<?> group : copiedExampleMolecule.getFunctionalGroups()) { // Just in case the example Molecule has multiple functional groups (which it shouldn't ideally)
            if (group.getType().equals(getGroupType())) {
                LegacyReaction reaction = generateReaction(new GenericReactant<>(copiedExampleMolecule, (G)group)); // Unchecked conversion
                return reaction;
            };
        };
        //TODO cache these generated Molecules as currently they get regenerated every frame
        Destroy.LOGGER.warn("Couldn't generate example Reaction for Generic Reaction "+id.toString());
        return null;
    };

    /**
     * You were probably looking through the source code and thought this was some sort of chemistry thing you had to do before a reaction.
     * Wow! This chemistry mod even simulates quantum mechanics! Impressive. But no. No, my friend. The purpose of this function is to fix
     * the strangest fucking Heisenbug I have ever seen. Sometimes reactants - somehow - get replaced with their products. There is nothing
     * which exists in Destroy's codebase which could do that. Nothing. It's all final fields. Yet somehow it happens. But you know when it
     * doesn't happen? For some fucking reason, this doesn't happen if you inspect the reactant before its reacted. Add a breakpoint, print
     * the structure, any of those are fine. It is only if you force the program to stop and look at what its doing that it realises it's
     * being fucking stupid. Or maybe I am. Maybe I just don't realize what's causing this. Maybe I got up in the middle of the night and
     * added multithreading to reaction generation and then forgot about it and now I can't find where I added it again. These are all
     * possibilities. I don't know. The only fucking thing I know is that printing the structure of the reactant fixes it. Fuck you.
     * @param molecule The stupid fucking molecule to stupid fucking fix
     */
    @SuppressWarnings("unused") // What's unused is your brain, you dumb fucking compiler.
    /* 
     * Update: Okay so I fixed it. In my defence, I was not lying about the Heisen weirdness. It's still stupid.
     */
    protected static final void collapseWavefunction(LegacySpecies molecule) {
        String fuckShitBollocks = molecule.getFullID();
    };

};
