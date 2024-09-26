package com.petrolpark.destroy.chemistry.legacy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReaction;

/**
 * A functional group in a {@link LegacySpecies}, containing the specific {@link LegacyAtom Atoms} within it.
 * Each subclass of Group represents a functional group (e.g. alkene, alcohol),
 * but instances of a Group subclass are specific to Molecules.
 * <p>Groups are identified within Molecule with {@link GroupFinder Group Finders}.</p>
 * <p>See the <a href="https://github.com/petrolpark/Destroy/wiki/Custom-Groups">Destroy Wiki</a> for more detail.</p>
 */
public abstract class LegacyFunctionalGroup<G extends LegacyFunctionalGroup<G>> {

    /**
     * All {@link GenericReaction Generic Reactions} in which {@link LegacySpecies Molecules} with certain functional Groups can participate, indexed by the {@link LegacyFunctionalGroup#getType Group Types} of those functional Groups.
     */
    public static Map<LegacyFunctionalGroupType<?>, Set<GenericReaction>> groupTypesAndReactions = new HashMap<>();

    public LegacyFunctionalGroup() {
        groupTypesAndReactions.putIfAbsent(getType(), new HashSet<>());
    };

    /**
     * Get the {@link GenericReaction Generic Reactions} in which the given functional Group participates.
     * @param group
     */
    public static final Set<GenericReaction> getReactionsOf(LegacyFunctionalGroup<?> group) {
        return groupTypesAndReactions.get(group.getType());
    };

    public abstract LegacyFunctionalGroupType<? extends G> getType();

    /**
     * Get the {@link GenericReaction Generic Reactions} which the functional Group identified by the given ID participates in.
     * @param ID The {@link LegacyFunctionalGroup#getID String ID} of the functional Group.
     */
    public static final Set<GenericReaction> getReactionsOfGroupByID(LegacyFunctionalGroupType<?> type) {
        return groupTypesAndReactions.get(type);
    };

    @Override
    public String toString() {
        return getType().toString();
    };
};
