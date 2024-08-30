package com.petrolpark.destroy.client.ponder;

import java.util.List;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.block.DestroyBlocks;
import com.petrolpark.destroy.block.PeriodicTableBlock;
import com.petrolpark.destroy.client.ponder.scene.ChemistryScenes;
import com.petrolpark.destroy.client.ponder.scene.DestroyScenes;
import com.petrolpark.destroy.client.ponder.scene.DynamoScenes;
import com.petrolpark.destroy.client.ponder.scene.ExplosivesScenes;
import com.petrolpark.destroy.client.ponder.scene.OilScenes;
import com.petrolpark.destroy.client.ponder.scene.PollutionScenes;
import com.petrolpark.destroy.client.ponder.scene.ProcessingScenes;
import com.petrolpark.destroy.client.ponder.scene.TrypolithographyScenes;
import com.petrolpark.destroy.item.DestroyItems;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderStoryBoardEntry;
import com.simibubi.create.infrastructure.ponder.AllPonderTags;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class DestroyPonderIndex {

    public static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(Destroy.MOD_ID);

    public static void register() {

        // Aging Barrel
        HELPER.forComponents(DestroyBlocks.AGING_BARREL)
            .addStoryBoard("processing/aging_barrel", ProcessingScenes::agingBarrel);

        // Basin
        HELPER.forComponents(AllBlocks.BASIN)
            .addStoryBoard("reactions", ChemistryScenes::reactions, DestroyPonderTags.CHEMISTRY)
            .addStoryBoard("pollution/basins_and_vats", PollutionScenes::basinsAndVats);

        // Blacklight
        HELPER.forComponents(DestroyBlocks.BLACKLIGHT)
            .addStoryBoard("vat/uv", ChemistryScenes::vatUV);

        // Blaze Burner
        HELPER.forComponents(AllBlocks.BLAZE_BURNER)
            .addStoryBoard("vat/interaction", DestroyScenes::vatInteraction, DestroyPonderTags.CHEMISTRY);

        // Blowpipe
        HELPER.forComponents(DestroyBlocks.BLOWPIPE)
            .addStoryBoard("processing/blowpipe", ProcessingScenes::blowpipe)
            .addStoryBoard("processing/blowpipe_automation", ProcessingScenes::blowpipeAutomation);

        // Bubble Cap
        HELPER.forComponents(DestroyBlocks.BUBBLE_CAP)
            .addStoryBoard("processing/bubble_cap/generic", ProcessingScenes::bubbleCapGeneric)
            .addStoryBoard("processing/bubble_cap/mixtures", ProcessingScenes::bubbleCapMixtures, DestroyPonderTags.CHEMISTRY)
            .addStoryBoard("pollution/room_temperature", ChemistryScenes::roomTemperature);

        // Catalytic Converter
        HELPER.forComponents(DestroyBlocks.CATALYTIC_CONVERTER)
            .addStoryBoard("pollution/catalytic_converter", PollutionScenes::catalyticConverter);

        // Centrifuge
        HELPER.forComponents(DestroyBlocks.CENTRIFUGE)
            .addStoryBoard("processing/centrifuge/generic", ProcessingScenes::centrifugeGeneric)
            .addStoryBoard("processing/centrifuge/mixture", ProcessingScenes::centrifugeMixture, DestroyPonderTags.CHEMISTRY);

        
        // Colorimeter
        HELPER.forComponents(DestroyBlocks.COLORIMETER)
            .addStoryBoard("colorimeter", ChemistryScenes::colorimeter);

        // Cooler
        HELPER.forComponents(DestroyBlocks.COOLER)
            .addStoryBoard("processing/cooler", ProcessingScenes::cooler)
            .addStoryBoard("vat/temperature", ChemistryScenes::vatTemperature, DestroyPonderTags.CHEMISTRY);

        // Custom Explosive Mix
        HELPER.forComponents(DestroyBlocks.CUSTOM_EXPLOSIVE_MIX)
            .addStoryBoard("explosives/custom_explosive_mix", (s, u) -> ExplosivesScenes.filling(s, u, DestroyBlocks.CUSTOM_EXPLOSIVE_MIX::asStack))
            .addStoryBoard("explosives/custom_explosive_mix_explosion", ExplosivesScenes::exploding)
            .addStoryBoard("explosives/custom_explosive_mix", (s, u) -> ExplosivesScenes.dyeing(s, u, DestroyBlocks.CUSTOM_EXPLOSIVE_MIX::asStack))
            .addStoryBoard("explosives/custom_explosive_mix", ExplosivesScenes::naming);
        
        // Dynamo
        HELPER.forComponents(DestroyBlocks.DYNAMO)
            .addStoryBoard("processing/dynamo/redstone", DynamoScenes::dynamoRedstone)
            .addStoryBoard("processing/dynamo/charging", DynamoScenes::dynamoCharging, AllPonderTags.KINETIC_APPLIANCES)
            .addStoryBoard("processing/dynamo/electrolysis", DynamoScenes::dynamoElectrolysis)
            .addStoryBoard("processing/dynamo/arc_furnace", DynamoScenes::arcFurnace);

        // Extrusion Die
        HELPER.forComponents(DestroyBlocks.EXTRUSION_DIE)
            .addStoryBoard("processing/extrusion_die", ProcessingScenes::extrusionDie);

        // Hyperaccumulating Fertilizer
        HELPER.forComponents(DestroyItems.HYPERACCUMULATING_FERTILIZER)
            .addStoryBoard("processing/phytomining", ProcessingScenes::phytomining)
            .addStoryBoard("pollution/crop_growth_failure", PollutionScenes::cropGrowthFailure);

        // Circuit Mask and Keypunch
        HELPER.forComponents(DestroyItems.CIRCUIT_MASK, DestroyBlocks.KEYPUNCH)
            .addStoryBoard("trypolithography/intro", TrypolithographyScenes::intro)
            .addStoryBoard("trypolithography/rotating", TrypolithographyScenes::rotating)
            .addStoryBoard("trypolithography/flipping", TrypolithographyScenes::flipping);

        // Mechanical Mixer
        HELPER.forComponents(AllBlocks.MECHANICAL_MIXER)
            .addStoryBoard("reactions", DestroyScenes::reactions, DestroyPonderTags.CHEMISTRY)
            .addStoryBoard("pollution/basins_and_vats", PollutionScenes::basinsAndVats);

        // Mechanical Sieve
        HELPER.forComponents(DestroyBlocks.MECHANICAL_SIEVE)
            .addStoryBoard("processing/mechanical_sieve", ProcessingScenes::mechanicalSieve);
        
        // Pollution
        HELPER.forComponents(DestroyItems.POLLUTION_SYMBOL)
            .addStoryBoard("pollution/tanks", PollutionScenes::pipesAndTanks)
            .addStoryBoard("pollution/basins_and_vats", PollutionScenes::basinsAndVats)
            .addStoryBoard("pollution/smog", PollutionScenes::smog)
            .addStoryBoard("pollution/crop_growth_failure", PollutionScenes::cropGrowthFailure)
            .addStoryBoard("pollution/fishing_failure", PollutionScenes::fishingFailure)
            .addStoryBoard("blank_3x3", PollutionScenes::breedingFailure)
            .addStoryBoard("pollution/smog", PollutionScenes::villagerPriceIncrease)
            .addStoryBoard("pollution/cancer", PollutionScenes::cancer)
            .addStoryBoard("pollution/smog", PollutionScenes::acidRain)
            .addStoryBoard("pollution/room_temperature", ChemistryScenes::roomTemperature)
            .addStoryBoard("pollution/reduction", PollutionScenes::reduction)
            .addStoryBoard("blank_3x3", PollutionScenes::lightning)
            .addStoryBoard("pollution/catalytic_converter", PollutionScenes::catalyticConverter);

        // Pumpjack
        HELPER.forComponents(DestroyBlocks.PUMPJACK)
            .addStoryBoard("oil/seismometer", OilScenes::seismometer)
            .addStoryBoard("oil/pumpjack", OilScenes::pumpjack, AllPonderTags.KINETIC_APPLIANCES);

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
            .addStoryBoard("processing/tree_tap", ProcessingScenes::treeTap);

        // Vat Controller
        HELPER.forComponents(DestroyBlocks.VAT_CONTROLLER)
            .addStoryBoard("vat/construction", ChemistryScenes::vatConstruction)
            .addStoryBoard("vat/fluids", ChemistryScenes::vatFluids, AllPonderTags.FLUIDS)
            .addStoryBoard("vat/items", ChemistryScenes::vatItems)
            .addStoryBoard("reactions", ChemistryScenes::reactions, DestroyPonderTags.CHEMISTRY)
            .addStoryBoard("vat/temperature", ChemistryScenes::vatTemperature)
            //.addStoryBoard("bunsen_burner", ChemistryScenes::bunsenBurner)
            .addStoryBoard("pollution/room_temperature", ChemistryScenes::roomTemperature)
            .addStoryBoard("vat/pressure", ChemistryScenes::vatPressure)
            .addStoryBoard("pollution/basins_and_vats", PollutionScenes::basinsAndVats)
            .addStoryBoard("vat/reading", ChemistryScenes::vatReading)
            .addStoryBoard("colorimeter", ChemistryScenes::colorimeter)
            .addStoryBoard("vat/uv", ChemistryScenes::vatUV);
    };

    private static final ResourceLocation periodicTableSchematicLocation = Destroy.asResource("periodic_table");

    @SuppressWarnings("deprecation")
    public static void refreshPeriodicTableBlockScenes() {
        PeriodicTableBlock.ELEMENTS.forEach(entry -> {
            entry.blocks().forEach(block -> {
                ResourceLocation rl = BuiltInRegistries.ITEM.getKey(block.asItem());
                List<PonderStoryBoardEntry> list = PonderRegistry.ALL.get(rl);
                if (list != null) list.removeIf(storyBoard -> storyBoard.getSchematicLocation().equals(periodicTableSchematicLocation));
                HELPER.addStoryBoard(rl, periodicTableSchematicLocation, ChemistryScenes::periodicTable);
            });
        });
    };
};
