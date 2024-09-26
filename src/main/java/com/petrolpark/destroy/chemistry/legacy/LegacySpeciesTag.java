package com.petrolpark.destroy.chemistry.legacy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

/**
 * Molecule Tags do not affect the behaviour of {@link LegacySpecies Molecules} in {@link LegacyMixture Mixtures}, but instruct other parts of the game on how to deal with them.
 */
public class LegacySpeciesTag {

    private final String nameSpace;
    private final String id;

    private int color;

    public static final Map<String, LegacySpeciesTag> MOLECULE_TAGS = new HashMap<>();
    public static final Map<LegacySpeciesTag, Set<LegacySpecies>> MOLECULES_WITH_TAGS = new HashMap<>();

    public LegacySpeciesTag(String nameSpace, String id) { //TODO replace with proper registry
        this.nameSpace = nameSpace;
        this.id = id;
        color = 0xFFFFFF;
        MOLECULE_TAGS.put(nameSpace + ":" + id, this);
    };

    public LegacySpeciesTag withColor(int color) {
        this.color = color;
        return this;
    };

    public static void registerMoleculeToTag(LegacySpecies molecule, LegacySpeciesTag moleculeTag) {
        MOLECULES_WITH_TAGS.putIfAbsent(moleculeTag, new HashSet<>());
        MOLECULES_WITH_TAGS.get(moleculeTag).add(molecule);
    };

    public String getId() {
        return nameSpace + ":" + id;
    };

    public Component getFormattedName() {
        return Component.translatable(nameSpace + ".molecule_tag." + id).withStyle(Style.EMPTY.withColor(color));
    };  
};
