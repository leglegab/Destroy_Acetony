package com.petrolpark.destroy.chemistry.legacy;

import java.util.function.Supplier;

import com.google.common.base.MoreObjects;
import com.petrolpark.destroy.chemistry.api.error.ChemistryException.ExampleMoleculeMissingGroupException;

public class LegacyFunctionalGroupType<G extends LegacyFunctionalGroup<G>> {

    private final Supplier<LegacySpecies> exampleMolecule;
    private boolean exampleMoleculeVerified = false;

    public LegacyFunctionalGroupType(Supplier<LegacySpecies> exampleMoleculeSupplier) {
        
        this.exampleMolecule = exampleMoleculeSupplier;
    };

    public LegacySpecies getExampleMolecule() {
        if (!exampleMoleculeVerified) verifyExampleMolecule();
        return exampleMolecule.get();
    };

    private void verifyExampleMolecule() {
        if (!exampleMolecule.get().getFunctionalGroups().stream().anyMatch(group -> {
            return group.getType() == this;
        })) {
            throw new ExampleMoleculeMissingGroupException(exampleMolecule.get());
        };
        exampleMoleculeVerified = true;
    };

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("Example Molecule", getExampleMolecule().getFullID()).toString();
    };
};
