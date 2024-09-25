package com.petrolpark.destroy.test;

import com.petrolpark.destroy.chemistry.legacy.LegacyMolecularStructure;
import com.petrolpark.destroy.chemistry.legacy.index.DestroyTopologies;

public class ChemistryTest {

    public static void main(String ...args) {

        DestroyTopologies.register();
        
        String line = "pee^poo";
        System.out.println(line.split("\\^")[0]);
        System.out.println(LegacyMolecularStructure.deserialize("destroy:linear:O=N^1(O^-1)O^-1").serialize());
        
    };
};
