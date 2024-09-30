package com.petrolpark.destroy.chemistry.legacy.index;

import com.petrolpark.destroy.chemistry.legacy.LegacyFunctionalGroupType;
import com.petrolpark.destroy.chemistry.legacy.index.group.AcidAnhydrideGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.AcylChlorideGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.AlcoholGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.AlkoxideGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.BoraneGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.BorateEsterGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.BoricAcidGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.CarbonylGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.CarboxylicAcidGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.EsterGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.HalideGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.IsocyanateGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.NitrileGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.NitroGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.NonTertiaryAmineGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.NonTertiaryBoraneGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.PrimaryAmineGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.SaturatedCarbonGroup;
import com.petrolpark.destroy.chemistry.legacy.index.group.UnsubstitutedAmideGroup;

public class DestroyGroupTypes {
    
    public static final LegacyFunctionalGroupType<AcidAnhydrideGroup> ACID_ANHYDRIDE = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_ACID_ANHYDRIDE);
    public static final LegacyFunctionalGroupType<AcylChlorideGroup> ACYL_CHLORIDE = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_ACYL_CHLORIDE);
    public static final LegacyFunctionalGroupType<AlcoholGroup> ALCOHOL = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_ALCOHOL);
    public static final LegacyFunctionalGroupType<SaturatedCarbonGroup> ALKENE = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_ALKENE);
    public static final LegacyFunctionalGroupType<AlkoxideGroup> ALKOXIDE = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_ALKOXIDE);
    public static final LegacyFunctionalGroupType<SaturatedCarbonGroup> ALKYNE = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_ALKYNE);
    public static final LegacyFunctionalGroupType<BoraneGroup> BORANE = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_BORANE);
    public static final LegacyFunctionalGroupType<BorateEsterGroup> BORATE_ESTER = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_BORATE_ESTER);
    public static final LegacyFunctionalGroupType<BoricAcidGroup> BORIC_ACID = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_ORGANIC_BORIC_ACID);
    public static final LegacyFunctionalGroupType<CarbonylGroup> CARBONYL = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_CARBONYL);
    public static final LegacyFunctionalGroupType<CarboxylicAcidGroup> CARBOXYLIC_ACID = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_CARBOXYLIC_ACID);
    public static final LegacyFunctionalGroupType<EsterGroup> ESTER = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_ESTER);
    public static final LegacyFunctionalGroupType<HalideGroup> HALIDE = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_CHLORIDE);
    public static final LegacyFunctionalGroupType<IsocyanateGroup> ISOCYANATE = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_ISOCYANATE);
    public static final LegacyFunctionalGroupType<NitrileGroup> NITRILE = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_NITRILE);
    public static final LegacyFunctionalGroupType<NitroGroup> NITRO = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_NITRO);
    public static final LegacyFunctionalGroupType<NonTertiaryAmineGroup> NON_TERTIARY_AMINE = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_PRIMARY_AMINE);
    public static final LegacyFunctionalGroupType<NonTertiaryBoraneGroup> NON_TERTIARY_BORANE = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_PRIMARY_BORANE);
    public static final LegacyFunctionalGroupType<PrimaryAmineGroup> PRIMARY_AMINE = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_PRIMARY_AMINE);
    public static final LegacyFunctionalGroupType<UnsubstitutedAmideGroup> UNSUBSTITUTED_AMIDE = new LegacyFunctionalGroupType<>(() -> DestroyMolecules.GENERIC_AMIDE);
};
