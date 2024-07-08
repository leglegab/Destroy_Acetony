package com.petrolpark.destroy.client.ponder;

import java.util.List;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.block.DestroyBlocks;
import com.petrolpark.destroy.block.PeriodicTableBlock;
import com.petrolpark.destroy.client.ponder.scene.ChemistryScenes;
import com.petrolpark.destroy.client.ponder.scene.DestroyScenes;
import com.petrolpark.destroy.client.ponder.scene.KineticsScenes;
import com.petrolpark.destroy.client.ponder.scene.OilScenes;
import com.petrolpark.destroy.client.ponder.scene.PollutionScenes;
import com.petrolpark.destroy.client.ponder.scene.TrypolithographyScenes;
import com.petrolpark.destroy.item.DestroyItems;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderStoryBoardEntry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class DestroyPonderIndex {

    public static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(Destroy.MOD_ID);
    private static final PonderRegistrationHelper CREATE_HELPER = new PonderRegistrationHelper(Create.ID);

    public static void register() {

        // Aging Barrel
        HELPER.forComponents(DestroyBlocks.AGING_BARREL)
            .addStoryBoard("aging_barrel", DestroyScenes::agingBarrel);

        // Basin
        HELPER.forComponents(AllBlocks.BASIN)
            .addStoryBoard("reactions", DestroyScenes::reactions);

        // Blacklight
        HELPER.forComponents(DestroyBlocks.BLACKLIGHT)
            .addStoryBoard("vat/uv", ChemistryScenes::uv);

        // Blaze Burner
        HELPER.forComponents(AllBlocks.BLAZE_BURNER)
            .addStoryBoard("vat/interaction", DestroyScenes::vatInteraction);

        // Bubble Cap
        HELPER.forComponents(DestroyBlocks.BUBBLE_CAP)
            .addStoryBoard("bubble_cap/generic", DestroyScenes::bubbleCapGeneric)
            .addStoryBoard("bubble_cap/mixtures", DestroyScenes::bubbleCapMixtures)
            .addStoryBoard("pollution/room_temperature", ChemistryScenes::roomTemperature);

        // Centrifuge
        HELPER.forComponents(DestroyBlocks.CENTRIFUGE)
            .addStoryBoard("centrifuge/generic", DestroyScenes::centrifugeGeneric)
            .addStoryBoard("centrifuge/mixture", DestroyScenes::centrifugeMixture);

        // Coaxial Gear
        CREATE_HELPER.forComponents(DestroyBlocks.COAXIAL_GEAR)
            .addStoryBoard("cog/small", com.simibubi.create.infrastructure.ponder.scenes.KineticsScenes::cogAsRelay);
        HELPER.forComponents(DestroyBlocks.COAXIAL_GEAR)
            .addStoryBoard("coaxial_gear/shaftless", DestroyScenes::coaxialGearShaftless)
            .addStoryBoard("coaxial_gear/through", DestroyScenes::coaxialGearThrough);

        HELPER.forComponents(DestroyBlocks.COLORIMETER)
            .addStoryBoard("colorimeter", ChemistryScenes::colorimeter);

        // Colossal Cogwheel
        HELPER.forComponents(DestroyBlocks.COLOSSAL_COGWHEEL)
            .addStoryBoard("kinetics/colossal_cogwheel", KineticsScenes::colossalCogwheel);

        // Cooler
        HELPER.forComponents(DestroyBlocks.COOLER)
            .addStoryBoard("cooler", DestroyScenes::cooler)
            .addStoryBoard("vat/interaction", DestroyScenes::vatInteraction);

        // Differential
        HELPER.forComponents(DestroyBlocks.DIFFERENTIAL)
            .addStoryBoard("differential", DestroyScenes::differential);

        // Double Cardan Shaft
        HELPER.forComponents(DestroyBlocks.DOUBLE_CARDAN_SHAFT)
            .addStoryBoard("double_cardan_shaft", DestroyScenes::doubleCardanShaft);
        
        // Dynamo
        HELPER.forComponents(DestroyBlocks.DYNAMO)
            .addStoryBoard("dynamo/redstone", DestroyScenes::dynamoRedstone)
            .addStoryBoard("dynamo/charging", DestroyScenes::dynamoCharging)
            .addStoryBoard("dynamo/electrolysis", DestroyScenes::dynamoElectrolysis);

        // Extrusion Die
        HELPER.forComponents(DestroyBlocks.EXTRUSION_DIE)
            .addStoryBoard("extrusion_die", DestroyScenes::extrusionDie);

        // Hyperaccumulating Fertilizer
        HELPER.forComponents(DestroyItems.HYPERACCUMULATING_FERTILIZER)
            .addStoryBoard("phytomining", DestroyScenes::phytomining)
            .addStoryBoard("pollution/crop_growth_failure", PollutionScenes::cropGrowthFailure);

        // Circuit Mask and Keypunch
        HELPER.forComponents(DestroyItems.CIRCUIT_MASK, DestroyBlocks.KEYPUNCH)
            .addStoryBoard("trypolithography/intro", TrypolithographyScenes::intro)
            .addStoryBoard("trypolithography/rotating", TrypolithographyScenes::rotating)
            .addStoryBoard("trypolithography/flipping", TrypolithographyScenes::flipping);

        // Mechanical Mixer
        HELPER.forComponents(AllBlocks.MECHANICAL_MIXER)
            .addStoryBoard("reactions", DestroyScenes::reactions)
            .addStoryBoard("pollution/basins_and_vats", PollutionScenes::basinsAndVats);

        // Pollution
        HELPER.forComponents(DestroyItems.POLLUTION_SYMBOL)
            .addStoryBoard("pollution/tanks", PollutionScenes::pipesAndTanks)
            .addStoryBoard("pollution/basins_and_vats", PollutionScenes::basinsAndVats)
            .addStoryBoard("pollution/smog", PollutionScenes::smog)
            .addStoryBoard("pollution/crop_growth_failure", PollutionScenes::cropGrowthFailure)
            .addStoryBoard("pollution/fishing_failure", PollutionScenes::fishingFailure)
            .addStoryBoard("blank_3x3", PollutionScenes::breedingFailure)
            .addStoryBoard("blank_3x3", PollutionScenes::villagerPriceIncrease)
            .addStoryBoard("pollution/cancer", PollutionScenes::cancer)
            .addStoryBoard("pollution/acid_rain", PollutionScenes::acidRain)
            .addStoryBoard("pollution/room_temperature", ChemistryScenes::roomTemperature)
            .addStoryBoard("pollution/reduction", PollutionScenes::reduction)
            .addStoryBoard("pollution/catalytic_converter", PollutionScenes::catalyticConverter);

        // Planetary Gearset
        CREATE_HELPER.forComponents(DestroyBlocks.PLANETARY_GEARSET)
            .addStoryBoard("cog/speedup", com.simibubi.create.infrastructure.ponder.scenes.KineticsScenes::cogsSpeedUp)
            .addStoryBoard("cog/large", com.simibubi.create.infrastructure.ponder.scenes.KineticsScenes::largeCogAsRelay);
        HELPER.forComponents(DestroyBlocks.PLANETARY_GEARSET)
            .addStoryBoard("planetary_gearset", DestroyScenes::planetaryGearset);

        // Pumpjack
        HELPER.forComponents(DestroyBlocks.PUMPJACK)
            .addStoryBoard("oil/seismometer", OilScenes::seismometer)
            .addStoryBoard("oil/pumpjack", OilScenes::pumpjack);

        // Redstone Programmer
        HELPER.forComponents(DestroyBlocks.REDSTONE_PROGRAMMER)
            .addStoryBoard("redstone_programmer", DestroyScenes::redstoneProgrammer);

        // Seismograph
        HELPER.forComponents(DestroyItems.SEISMOGRAPH)
            .addStoryBoard("oil/seismometer", OilScenes::seismometer)
            .addStoryBoard("oil/seismograph", OilScenes::seismograph);

        // Seismometer
        HELPER.forComponents(DestroyItems.SEISMOMETER)
            .addStoryBoard("oil/seismometer", OilScenes::seismometer)
            .addStoryBoard("oil/seismograph", OilScenes::seismograph);

        // Tree Tap
        HELPER.forComponents(DestroyBlocks.TREE_TAP)      
            .addStoryBoard("tree_tap", DestroyScenes::treeTap);

        // Vat Controller
        HELPER.forComponents(DestroyBlocks.VAT_CONTROLLER)
            .addStoryBoard("vat/construction", ChemistryScenes::vatConstruction)
            .addStoryBoard("vat/heating", ChemistryScenes::vatHeating)
            .addStoryBoard("bunsen_burner", ChemistryScenes::bunsenBurner)
            .addStoryBoard("pollution/room_temperature", ChemistryScenes::roomTemperature)
            .addStoryBoard("pollution/basins_and_vats", PollutionScenes::basinsAndVats)
            .addStoryBoard("vat/monitoring", ChemistryScenes::vatMonitoring)
            .addStoryBoard("colorimeter", ChemistryScenes::colorimeter)
            .addStoryBoard("vat/uv", ChemistryScenes::uv);
            //.addStoryBoard("vat/construction", DestroyScenes::vatConstruction)
            //.addStoryBoard("reactions", DestroyScenes::reactions)
            //.addStoryBoard("vat/interaction", DestroyScenes::vatInteraction)
            //.addStoryBoard("vat/uv", DestroyScenes::uv);
    };

    private static final ResourceLocation periodicTableSchematicLocation = Destroy.asResource("periodic_table");

    @SuppressWarnings("deprecation")
    public static void refreshPeriodicTableBlockScenes() {
        PeriodicTableBlock.ELEMENTS.forEach(entry -> {
            entry.blocks().forEach(block -> {
                ResourceLocation rl = BuiltInRegistries.ITEM.getKey(block.asItem());
                List<PonderStoryBoardEntry> list = PonderRegistry.ALL.get(rl);
                if (list != null) list.removeIf(storyBoard -> storyBoard.getSchematicLocation().equals(periodicTableSchematicLocation));
                HELPER.addStoryBoard(rl, periodicTableSchematicLocation, DestroyScenes::periodicTable);
            });
        });
    };
};
