package com.petrolpark.destroy.chemistry.legacy.genericreaction;

import com.petrolpark.destroy.chemistry.legacy.LegacyAtom;
import com.petrolpark.destroy.chemistry.legacy.LegacyElement;
import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroup;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroupType;
import com.petrolpark.destroy.chemistry.legacy.LegacySpecies;
import com.petrolpark.destroy.chemistry.legacy.LegacyReaction;

import net.minecraft.resources.ResourceLocation;

public abstract class DoubleGroupGenericReaction<FirstGroup extends LegacyFunctionalGroup<FirstGroup>, SecondGroup extends LegacyFunctionalGroup<SecondGroup>> extends GenericReaction {

    protected final LegacyFunctionalGroupType<FirstGroup> firstType;
    protected final LegacyFunctionalGroupType<SecondGroup> secondType;

    /**
     * This number is used to number R Groups in example Reactions.
     */
    private int i;

    public DoubleGroupGenericReaction(ResourceLocation id, LegacyFunctionalGroupType<FirstGroup> firstType, LegacyFunctionalGroupType<SecondGroup> secondType) {
        super(id);
        this.firstType = firstType;
        this.secondType = secondType;
        LegacyFunctionalGroup.groupTypesAndReactions.get(firstType).add(this);
        LegacyFunctionalGroup.groupTypesAndReactions.get(secondType).add(this);
        GENERIC_REACTIONS.add(this);
    };
    
    /**
     * Generates a Reaction (with non-abstract Reactants and Products) based on the given Molecules which have these Groups.
     * @param reactant1 has the {@link FirstGroup first declared Group}.
     * @param reactant2 has the {@link SecondGroup second declared Group}.
     * @return The whole Reaction, including the defined structures of the product(s). Return {@code null} if the Reaction is impossible.
     */
    public abstract LegacyReaction generateReaction(GenericReactant<FirstGroup> firstReactant, GenericReactant<SecondGroup> secondReactant);

    @Override
    public final boolean involvesSingleGroup() {
        return false;
    };

    public final LegacyFunctionalGroupType<FirstGroup> getFirstGroupType() {
        return firstType;
    };

    public final LegacyFunctionalGroupType<SecondGroup> getSecondGroupType() {
        return secondType;
    };

    @Override
    @SuppressWarnings("unchecked")
    public LegacyReaction generateExampleReaction() {

        i = 1;
        LegacySpecies exampleMolecule1 = copyAndNumberRGroups(getFirstGroupType().getExampleMolecule());
        LegacySpecies exampleMolecule2 = copyAndNumberRGroups(getSecondGroupType().getExampleMolecule());
        
        GenericReactant<FirstGroup> reactant1 = null;
        GenericReactant<SecondGroup> reactant2 = null;

        for (LegacyFunctionalGroup<?> group : exampleMolecule1.getFunctionalGroups()) { // Just in case the example Molecule has multiple functional groups (which it shouldn't ideally)
            if (group.getType() == getFirstGroupType()) reactant1 = new GenericReactant<>(exampleMolecule1, (FirstGroup)group);
        };
        for (LegacyFunctionalGroup<?> group : exampleMolecule2.getFunctionalGroups()) { // Just in case the example Molecule has multiple functional groups (which it shouldn't ideally)
            if (group.getType() == getSecondGroupType()) reactant2 = new GenericReactant<>(exampleMolecule2, (SecondGroup)group);
        };

        if (reactant1 == null || reactant2 == null) throw new IllegalStateException("Couldn't generate example Reaction for Generic Reaction "+id.toString());

        return generateReaction(reactant1, reactant2); // Unchecked conversion
        
    };

    private LegacySpecies copyAndNumberRGroups(LegacySpecies molecule) {
        LegacyMolecularStructure copiedStructure = molecule.shallowCopyStructure();
        for (LegacyAtom atom : molecule.getAtoms()) {
            if (atom.getElement() == LegacyElement.R_GROUP) {
                LegacyAtom newAtom = new LegacyAtom(LegacyElement.R_GROUP);
                newAtom.rGroupNumber = i;
                copiedStructure.replace(atom, newAtom);
                i++;
            };
        };
        return moleculeBuilder()
            .structure(copiedStructure)
            .build();
    };

};
