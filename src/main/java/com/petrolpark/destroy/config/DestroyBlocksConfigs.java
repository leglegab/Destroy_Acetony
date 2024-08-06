package com.petrolpark.destroy.config;

public class DestroyBlocksConfigs extends DestroyConfigBase {

    public final ConfigGroup bubbleCap = group(0, "bubbleCap", "Bubble Cap");
    public final ConfigInt bubbleCapCapacity = i(1000, 1, "bubbleCapCapacity", "[in mB]", "Fluid capacity of Bubble Caps", "[setting this too low may make some recpies impossible]");
    public final ConfigInt bubbleCapRecipeFrequency = i(100, 1, "bubbleCapRecipeFrequency", "[in ticks]", "How often distillation towers try and distill");

    public final ConfigGroup catalyticConverter = group(0, "catalyticConverter", "Catalytic Converter");
    public final ConfigFloat catalyticConverterReduction = f(0.25f, 0f, Float.MAX_VALUE, "catalyticConverterReduction", "The proportion by which Fluids polluted through a Catalytic Converter have their potential pollution multiplied.");
    
    public final ConfigGroup centrifuge = group(0, "centrifuge", "Centrifuge");
    public final ConfigInt centrifugeCapacity = i(1000, 1, "centrifugeCapacity", "[in mB]", "Fluid capacity of each of the Centrifuge's three tanks", "[setting this too low may make some recipes impossible]");

    public final ConfigGroup cooler = group(0, "cooler", "Refrigerstrayter");
    public final ConfigInt maximumCoolingTicks = i(12000, 1, "maximumCoolingTicks", "[in ticks]", "Roughly the length of cooling time a Refrigerstrayter must have before it stops accepting more coolant");
    public final ConfigFloat coolerEfficiency = f(1f, 0f, "coolerEfficiency", "A multiplier for the standard cooling-second-kelvin-per-joule of Refrigerstrayters", "[0 will disable Refrigerstrayters]");
    public final ConfigBool coolerEnhancedByPurity = b(true, "coolerEnhancedByPurity", "Whether to scale the efficiency of coolers with the purity of supplied refrigerants");
    
    public final ConfigGroup dynamite = group(0, "dynamite", "Dynamite");
    public final ConfigInt dynamiteMaxRadius = i(10, 1, 128, "dynamiteMaxRadius", "[in blocks]", "The maximum distance to which a side of a Dyanmite explosive can extend");
    public final ConfigBool dynamiteExplodesResistant = b(false, "dynamiteExplodesResistant", "Whether Dynamite explosions can explode unbreakable blocks like Obsidian");
    
    public final ConfigGroup dynamo = group(0, "dynamo", "Dynamo");
    public final ConfigBool dynamoBulkCharging = b(true, "dynamoBulkCharging", "Whether Dynamos can charge multiple Item Stacks at once.");

    public final ConfigGroup pumpjack = group(0, "pumpjack", "Pumpjack");
    public final ConfigInt pumpjackCapacity = i(2000, 1, "pumpjackCapacity", "[in mB]", "Fluid capacity of Pumpjacks");
    public final ConfigFloat pumpjackExtractionSpeed = f(1f, 0f, "pumpjackExtractionSpeed", "A multiplier for the standard mB-per-tick-per-RPM for Pumpjacks", "[0 will disable Pumpjacks]");
    
    public final ConfigGroup redstoneProgrammer = group(0, "redstoneProgrammer", "Redstone Programmer");
    public final ConfigInt redstoneProgrammerMaxChannels = i(20, 0, 64, "redstoneProgrammerMaxChannels", "The maximum number of channels a Redstone Programmer can have.");
    public final ConfigInt redstoneProgrammerMinTicksPerBeat = i(2, 1, 20, "redstoneProgrammerMinTicksPerBeat", "The shortest length (in ticks) a Redstone Programmer can change signal over.", "The lower this is, the greater the potential for players to cause lag.");

    public final ConfigGroup treeTap = group(0, "treeTap", "Tapper");
    public final ConfigInt treeTapCapacity = i(1000, 1, "treeTapCapacity", "[in mB]", "Fluid capacity of Tappers");
    public final ConfigFloat treeTapExtractionSpeed = f(1f, 0f, "treeTapExtractionSpeed", "A multiplier for the standard mB-per-tick-per-RPM for Tappers", "[0 will disable Tappers]");
    
    public final ConfigGroup vat = group(0, "vat", "Vat");
    public final ConfigBool vatExplodesAtHighPressure = b(true, "vatExplodesAtHighPressure", "Whether Vats explode if the pressure exceeds the maximum of the weakest block.");
    public final ConfigInt simulationLevel = i(10, "simulationLevel", "How many times per tick reactions and thermodynamics are simulated.", "Increasing this may cause lag. Decreasing it can cause flickering in Vats.");
    public final ConfigFloat blazeBurnerHeatingPower = f(15000f, Float.MIN_VALUE, Float.MAX_VALUE, "blazeBurnerHeatingPower", "The power supplied by kindled Blaze Burners to Vats and Basins");
    public final ConfigFloat blazeBurnerSuperHeatingPower = f(50000f, Float.MIN_VALUE, Float.MAX_VALUE, "blazeBurnerSuperHeatingPower", "The power supplied by superheating Blaze Burners to Vats and Basins");
    public final ConfigFloat coolerHeatingPower = f(-30000f, Float.MIN_VALUE, Float.MAX_VALUE, "coolerHeatingPower", "The power supplied by cooling Refrigerstraytors to Vats and Basins");
    
    public final ConfigInt customExplosiveMixSize = i(5, 0, 16, "customExplosiveMixSize", "Inventory size of Mixed Explosives");
    
    public final ConfigInt beakerCapacity = i(500, "beakerCapacity", "The capacity (in mB) of Beakers");
    public final ConfigInt roundBottomedFlaskCapacity = i(500, "roundBottomedFlaskCapacity", "The capacity (in mB) of Round-Bottomed Flasks");
    public final ConfigInt measuringCylinderCapacity = i(300, "measuringCylinderCapacity", "The capacity (in mB) of Beakers");

    @Override
    public String getName() {
        return "blocks";
    };
};
