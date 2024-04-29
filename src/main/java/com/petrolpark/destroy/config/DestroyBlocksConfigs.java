package com.petrolpark.destroy.config;

public class DestroyBlocksConfigs extends DestroyConfigBase {

    public final ConfigGroup dynamo = group(0, "dynamo", "Dynamo");
    public final ConfigBool dynamoBulkCharging = b(true, "dynamoBulkCharging", "Whether Dynamos can charge multiple Item Stacks at once.");

    public final ConfigGroup redstoneProgrammer = group(0, "redstoneProgrammer", "Redstone Programmer");
    public final ConfigInt redstoneProgrammerMaxChannels = i(20, 0, 64, "redstoneProgrammerMaxChannels", "The maximum number of channels a Redstone Programmer can have.");
    public final ConfigInt redstoneProgrammerMinTicksPerBeat = i(2, 1, 20, "redstoneProgrammerMinTicksPerBeat", "The shortest length (in ticks) a Redstone Programmer can change signal over.", "The lower this is, the greater the potential for players to cause lag.");

    public final ConfigGroup vat = group(0, "vat", "Vat");
    public final ConfigBool vatExplodesAtHighPressure = b(true, "vatExplodesAtHighPressure", "Whether Vats explode if the pressure exceeds the maximum of the weakest block.");
    public final ConfigInt simulationLevel = i(10, "simulationLevel", "How many times per tick reactions and thermodynamics are simulated.", "Increasing this may cause lag. Decreasing it can cause flickering in Vats.");
    
    
    @Override
    public String getName() {
        return "blocks";
    };
};
