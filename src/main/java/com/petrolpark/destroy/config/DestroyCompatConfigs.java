package com.petrolpark.destroy.config;

public class DestroyCompatConfigs extends DestroyConfigBase {
    
    public final ConfigGroup tfmg = group(0, "TFMG", Comments.tfmg);

    @Override
    public String getName() {
        return "compatibility";
    };

    private static class Comments {
        static String
        tfmg = "Create: The Factory Must Grow",


        reloadRequired = "[Reload required]";
    };
};
