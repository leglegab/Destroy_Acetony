package com.petrolpark.destroy.chemistry.api.property;

/**
 * An object of which instances usually form a standard number of covalent bonds.
 * This information is used in serializing and deserializing {@link ISpecies} and <a href="https://github.com/petrolpark/Destroy/wiki/FROWNS">FROWNS</a> strings.
 * TODO link to FROWNS de/serializer
 * @since 1.0
 * @author petrolpark
 */
public interface IStandardValenciesForFROWNS {
    
    /**
     * Used by the TODO LINK FROWNS SERIALIZER to determine how many hydrogen atoms to attach back onto {@link IAtom}s.
     * @param valency The combined single bond equivalents of all covalent bonds to this object
     * @return The next lowest combined total of single bond equivalents this object is "happy" to have (e.g. almost always {@code 4f} for carbon) - this should just give a sensible result for the number of hydrogens to add
     */
    public float getNextLowestValency(float valency);

};
