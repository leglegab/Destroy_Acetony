package com.petrolpark.destroy.chemistry.legacy.index;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.petrolpark.destroy.chemistry.legacy.LegacyAtom;
import com.petrolpark.destroy.chemistry.legacy.LegacyBond;
import com.petrolpark.destroy.chemistry.legacy.LegacyElement;
import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroup;
import com.petrolpark.destroy.chemistry.legacy.GroupFinder;
import com.petrolpark.destroy.chemistry.legacy.LegacyBond.BondType;
import com.petrolpark.destroy.chemistry.legacy.index.group.AcidAnhydrideGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.AcylChlorideGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.AlcoholGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.AlkeneGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.AlkyneGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.CarbonylGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.CarboxylicAcidGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.EsterGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.HalideGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.NitrileGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.NonTertiaryAmineGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.UnsubstitutedAmideGroup;

public class DestroyGroupFinder extends GroupFinder {

    @Override
    public List<LegacyFunctionalGroup<?>> findGroups(Map<LegacyAtom, List<LegacyBond>> structure) {

        List<LegacyFunctionalGroup<?>> groups = new ArrayList<>();

        List<LegacyAtom> carbonsToIgnore = new ArrayList<>();
        List<LegacyAtom> carbonsToIgnoreForAlkenes = new ArrayList<>();
        List<LegacyAtom> carbonsToIgnoreForAlkynes = new ArrayList<>();

        for (LegacyAtom carbon : structure.keySet()) {

            if (carbon.getElement() != LegacyElement.CARBON || carbonsToIgnore.contains(carbon)) {
                continue;
            };

            List<LegacyAtom> carbonylOxygens = bondedAtomsOfElementTo(structure, carbon, LegacyElement.OXYGEN, BondType.DOUBLE);
            List<LegacyAtom> singleBondOxygens = bondedAtomsOfElementTo(structure, carbon, LegacyElement.OXYGEN, BondType.SINGLE);
            List<LegacyAtom> chlorines = bondedAtomsOfElementTo(structure, carbon, LegacyElement.CHLORINE, BondType.SINGLE);
            List<LegacyAtom> halogens = new ArrayList<>(chlorines);
            halogens.addAll(bondedAtomsOfElementTo(structure, carbon, LegacyElement.IODINE, BondType.SINGLE));
            List<LegacyAtom> nitrogens = bondedAtomsOfElementTo(structure, carbon, LegacyElement.NITROGEN, BondType.SINGLE);
            List<LegacyAtom> hydrogens = bondedAtomsOfElementTo(structure, carbon, LegacyElement.HYDROGEN, BondType.SINGLE);
            List<LegacyAtom> carbons = bondedAtomsOfElementTo(structure, carbon, LegacyElement.CARBON, BondType.SINGLE);
            List<LegacyAtom> alkeneCarbons = bondedAtomsOfElementTo(structure, carbon, LegacyElement.CARBON, BondType.DOUBLE);
            List<LegacyAtom> alkyneCarbons = bondedAtomsOfElementTo(structure, carbon, LegacyElement.CARBON, BondType.TRIPLE);
            List<LegacyAtom> nitrileNitrogens = bondedAtomsOfElementTo(structure, carbon, LegacyElement.NITROGEN, BondType.TRIPLE);
            List<LegacyAtom> rGroups = bondedAtomsOfElementTo(structure, carbon, LegacyElement.R_GROUP);

            if (carbonylOxygens.size() == 1) { // Ketones, aldehydes, esters, acids, acid anhydrides, acyl chlorides, amides
                LegacyAtom carbonylOxygen = carbonylOxygens.get(0);
                if (singleBondOxygens.size() == 1) { // Esters, carboxylic acids and acid anhydrides
                    LegacyAtom alcoholOxygen = singleBondOxygens.get(0);
                    if (bondedAtomsOfElementTo(structure, alcoholOxygen, LegacyElement.CARBON, BondType.SINGLE).size() == 2) { // Esters and acid anhydrides
                        LegacyAtom otherCarbon = getCarbonBondedToOxygenWhichIsntThisCarbonInThisStructure(alcoholOxygen, carbon, structure);
                        if (bondedAtomsOfElementTo(structure, otherCarbon, LegacyElement.OXYGEN, BondType.DOUBLE).size() == 1) { // Acid anhydride
                            groups.add(new AcidAnhydrideGroup(carbon, carbonylOxygen, otherCarbon, bondedAtomsOfElementTo(structure, otherCarbon, LegacyElement.OXYGEN, BondType.DOUBLE).get(0), alcoholOxygen));
                        } else { // Ester
                            groups.add(new EsterGroup(carbon, otherCarbon, carbonylOxygen, alcoholOxygen));
                        };
                        carbonsToIgnore.add(otherCarbon);
                        continue;
                    } else if (bondedAtomsOfElementTo(structure, alcoholOxygen, LegacyElement.HYDROGEN, BondType.SINGLE).size() == 1) { //Carboxylic Acid
                        groups.add(new CarboxylicAcidGroup(carbon, carbonylOxygen, alcoholOxygen, bondedAtomsOfElementTo(structure, alcoholOxygen, LegacyElement.HYDROGEN, BondType.SINGLE).get(0)));
                        continue;
                    };
                } else { // Ketones, aldehydes, acyl chlorides, amides
                     if (nitrogens.size() == 1) { // Amide
                        List<LegacyAtom> amideHydrogens = bondedAtomsOfElementTo(structure, nitrogens.get(0), LegacyElement.HYDROGEN);
                        if (amideHydrogens.size() == 2) {
                            groups.add(new UnsubstitutedAmideGroup(carbon, carbonylOxygen, nitrogens.get(0), amideHydrogens.get(0), amideHydrogens.get(1)));
                            continue;
                        };
                     } else if (chlorines.size() == 1) {
                        groups.add(new AcylChlorideGroup(carbon, carbonylOxygen, chlorines.get(0)));
                        continue;
                     } else {
                        if (carbons.size() == 2) {
                            groups.add(new CarbonylGroup(carbon, carbonylOxygen, true));
                        } else if (carbons.size() + hydrogens.size() + rGroups.size() == 2) {
                            groups.add(new CarbonylGroup(carbon, carbonylOxygen, false));
                        };
                     }
                };
            } else { // Alcohols, halides, nitriles, amines
                for (LegacyAtom halogen : halogens) {
                    groups.add(new HalideGroup(carbon, halogen, carbons.size()));
                };
                for (LegacyAtom oxygen : singleBondOxygens) { // Alcohols
                    if (bondedAtomsOfElementTo(structure, oxygen, LegacyElement.HYDROGEN).size() == 1) {
                        groups.add(new AlcoholGroup(carbon, oxygen, bondedAtomsOfElementTo(structure, oxygen, LegacyElement.HYDROGEN).get(0), carbons.size()));
                    };
                };
                for (LegacyAtom nitrogen : nitrogens) { // Primary and secondary amines
                    for (LegacyAtom hydrogen : bondedAtomsOfElementTo(structure, nitrogen, LegacyElement.HYDROGEN)) {
                        groups.add(new NonTertiaryAmineGroup(carbon, nitrogen, hydrogen));
                    };
                };

                // Nitriles
                if (nitrileNitrogens.size() == 1 && carbons.size() == 1) {
                    groups.add(new NitrileGroup(carbon, nitrileNitrogens.get(0)));
                };
            };

            addAllAlkenes: for (LegacyAtom alkeneCarbon : alkeneCarbons) {
                if (carbonsToIgnoreForAlkenes.contains(alkeneCarbon)) continue addAllAlkenes;
                int firstCarbonDegree = bondedAtomsOfElementTo(structure, carbon, LegacyElement.CARBON).size() - 1;
                int secondCarbonDegree = bondedAtomsOfElementTo(structure, alkeneCarbon, LegacyElement.CARBON).size() - 1;
                // If the two Carbons have the same degree, then there are two alkene Groups
                if (firstCarbonDegree >= secondCarbonDegree) {
                    groups.add(new AlkeneGroup(carbon, alkeneCarbon));
                };
                if (secondCarbonDegree >= firstCarbonDegree) {
                    groups.add(new AlkeneGroup(alkeneCarbon, carbon));
                };
                carbonsToIgnoreForAlkenes.add(carbon);
            };

            if (alkyneCarbons.size() == 1) { // There can only ever be 1 triple bond on a carbon - check if there is one
                LegacyAtom alkyneCarbon = alkyneCarbons.get(0);
                if (!carbonsToIgnoreForAlkynes.contains(alkyneCarbon)) {
                    int firstCarbonDegree = bondedAtomsOfElementTo(structure, carbon, LegacyElement.CARBON).size() - 1;
                    int secondCarbonDegree = bondedAtomsOfElementTo(structure, alkyneCarbon, LegacyElement.CARBON).size() - 1;
                    // If the two Carbons have the same degree, then there are two alkyne Groups
                    if (firstCarbonDegree >= secondCarbonDegree) {
                        groups.add(new AlkyneGroup(carbon, alkyneCarbon));
                    };
                    if (secondCarbonDegree >= firstCarbonDegree) {
                        groups.add(new AlkyneGroup(alkyneCarbon, carbon));
                    };
                    carbonsToIgnoreForAlkynes.add(carbon);
                    carbonsToIgnoreForAlkynes.add(alkyneCarbon); // This will only ever be in one alkyne group so we can ignore it the second time
                };
            };

        };

        return groups;
    };

    /**
     * Who needs JavaDocs when you explain everything perfectly in the method identifier?
     * @param oxygen The oxygen bonded to both the carbons
     * @param carbon The carbon we don't want
     * @param structure The structure in which all of these silly little Atoms are
     * @return The carbon we do want
     */
    private LegacyAtom getCarbonBondedToOxygenWhichIsntThisCarbonInThisStructure(LegacyAtom oxygen, LegacyAtom carbon, Map<LegacyAtom, List<LegacyBond>> structure) { //clear method names are my passion
        List<LegacyAtom> carbonsBondedToOxygen = bondedAtomsOfElementTo(structure, oxygen, LegacyElement.CARBON); //get both the carbons
        carbonsBondedToOxygen.remove(carbon); //remove the carbonyl one
        return carbonsBondedToOxygen.get(0);
    };

    public static void register() {
        new DestroyGroupFinder();
    };
    
};
