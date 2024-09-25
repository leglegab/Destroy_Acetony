package com.petrolpark.destroy.chemistry.api.error;

import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.LegacySpecies;

/**
 * Chemistry Exceptions pertain to Destroy's chemical system.
 * If a Chemistry Exception is thrown while a {@link com.petrolpark.destroy.chemistry.legacy.genericreaction.GenericReaction Generic Reaction}
 * is being generated, it is ignored.
 */
public abstract class ChemistryException extends RuntimeException {

    public ChemistryException(String message) {
        super(message);
    };

    public ChemistryException(String message, Throwable cause) {
        super(message, cause);
    };

    public static class MoleculeDeserializationException extends ChemistryException {

        public MoleculeDeserializationException(String message) {
            super(message);
        };
    };

    public static abstract class FormulaException extends ChemistryException {

        public final LegacyMolecularStructure formula;

        public FormulaException(LegacyMolecularStructure formula, String message) {
            super("Problem with Formula '" + formula.serialize() + "': " + message);
            this.formula = formula;
        };

        public static class FormulaModificationException extends FormulaException {

            public FormulaModificationException(LegacyMolecularStructure formula, String message) {
                super(formula, message);
            };

        };

        public static class FormulaRenderingException extends FormulaException {

            public FormulaRenderingException(LegacyMolecularStructure formula, String message) {
                super(formula, message);
            };

        };
    };

    public static class TopologyDefinitionException extends ChemistryException {

        public TopologyDefinitionException(String message) {
            super(message);
        };

    };

    public static class FormulaSerializationException extends ChemistryException {

        public FormulaSerializationException(String message) {
            super(message);
        };

    };

    public static class ExampleMoleculeMissingGroupException extends ChemistryException {

        public ExampleMoleculeMissingGroupException(LegacySpecies exampleMolecule) {
            super("Example Molecule '"+exampleMolecule.getFullID()+"' does not contain the group it is meant to be exemplifying.");
        };

    };
};


