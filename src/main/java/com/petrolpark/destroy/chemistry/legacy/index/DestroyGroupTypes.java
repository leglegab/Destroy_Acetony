package com.petrolpark.destroy.chemistry.legacy.index;

import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroupType;
import com.petrolpark.destroy.chemistry.legacy.index.group.AcidAnhydrideGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.AcylChlorideGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.AlcoholGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.CarbonylGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.CarboxylicAcidGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.EsterGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.HalideGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.NitrileGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.NonTertiaryAmineGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.SaturatedCarbonGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.UnsubstitutedAmideGroup;

public class DestroyGroupTypes {
    
    public static LegacyFunctionalGroupType<AcidAnhydrideGroup> ACID_ANHYDRIDE = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_ACID_ANHYDRIDE);
    public static LegacyFunctionalGroupType<AcylChlorideGroup> ACYL_CHLORIDE = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_ACYL_CHLORIDE);
    public static LegacyFunctionalGroupType<AlcoholGroup> ALCOHOL = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_ALCOHOL);
    public static LegacyFunctionalGroupType<SaturatedCarbonGroup> ALKENE = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_ALKENE);
    public static LegacyFunctionalGroupType<SaturatedCarbonGroup> ALKYNE = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_ALKYNE);
    public static LegacyFunctionalGroupType<CarbonylGroup> CARBONYL = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_CARBONYL);
    public static LegacyFunctionalGroupType<CarboxylicAcidGroup> CARBOXYLIC_ACID = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_CARBOXYLIC_ACID);
    public static LegacyFunctionalGroupType<EsterGroup> ESTER = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_ESTER);
    public static LegacyFunctionalGroupType<HalideGroup> HALIDE = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_CHLORIDE);
    public static LegacyFunctionalGroupType<NitrileGroup> NITRILE = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_NITRILE);
    public static LegacyFunctionalGroupType<NonTertiaryAmineGroup> NON_TERTIARY_AMINE = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_PRIMARY_AMINE);
    public static LegacyFunctionalGroupType<UnsubstitutedAmideGroup> UNSUBSTITUTED_AMIDE = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_AMIDE);
};
