package com.petrolpark.destroy.config;

public class DestroyServerConfigs extends DestroyConfigBase {

    public final ConfigBool automaticGoggles = b(true, Comments.autoGoggles);
    public final DestroyContraptionsConfigs contraptions = nested(0, DestroyContraptionsConfigs::new, Comments.contraptions);
	public final DestroyPollutionConfigs pollution = nested(0, DestroyPollutionConfigs::new, Comments.pollution);
    public final DestroyCompatConfigs compat = nested(0, DestroyCompatConfigs::new, Comments.compat);

    
    @Override
    public String getName() {
        return "world";
    };

    private static class Comments {
        static String

        contraptions = "Destroy's processing machines",

        compat = "Compatibility with other mods",

		pollution = "Change the effects of pollution on the world",

		autoGoggles = "Players in Creative mode are treated as if they are wearing Engineer's Goggles even if they are not";
    };
}
