package com.petrolpark.destroy.test;

import com.petrolpark.destroy.chemistry.Formula;
//import com.petrolpark.destroy.chemistry.index.DestroyMolecules;
//import com.petrolpark.destroy.chemistry.index.DestroyReactions;
import com.petrolpark.destroy.chemistry.index.DestroyTopologies;

public class ChemistryTest {

    public static void main(String ...args) {

        DestroyTopologies.register();
        
        String line = "pee^poo";
        System.out.println(line.split("\\^")[0]);
        System.out.println(Formula.deserialize("destroy:linear:O=N^1(O^-1)O^-1").serialize());
        
    };
};
