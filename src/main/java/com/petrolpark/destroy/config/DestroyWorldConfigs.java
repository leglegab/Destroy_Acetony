package com.petrolpark.destroy.config;

public class DestroyWorldConfigs extends DestroyConfigBase {

    public final ConfigBool automaticGoggles = b(true, "automaticGoggles", "Players in Creative mode are treated as if they are wearing Engineer's Goggles even if they are not");
    public final DestroyBlocksConfigs contraptions = nested(0, DestroyBlocksConfigs::new, "Destroy's blocks");
	public final DestroyPollutionConfigs pollution = nested(0, DestroyPollutionConfigs::new, "The effects of pollution on the world");
    public final DestroySubstancesConfigs substances = nested(0, DestroySubstancesConfigs::new, "Destroy's drugs and medicines");
    public final DestroyCompatConfigs compat = nested(0, DestroyCompatConfigs::new, "Compatibility with other mods");
    
    @Override
    public String getName() {
        return "world";
    };
};
