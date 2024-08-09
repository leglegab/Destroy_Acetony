package com.petrolpark.destroy.config;

public class DestroyClientConfigs extends DestroyConfigBase {

    public final ConfigBool tempramentalItemDescriptions = b(true, "TempramentalItemDescriptions", Comments.temperamentalItemDescriptions);

    public final DestroyClientChemistryConfigs chemistry = nested(0, DestroyClientChemistryConfigs::new, Comments.chemistry);
    public final DestroyMenuButtonConfig configurationButtons = nested(0, DestroyMenuButtonConfig::new, Comments.configurationButtons);
    
    public final ConfigGroup extraInventory = group(0, "extraInventory");
    public final ConfigBool extraInventoryLeft = b(true, "extraHotbarLeft", "Whether the extra slots render to the left rather than right of containers");
    public final ConfigInt extraInventoryWidth = i(4, 1, Integer.MAX_VALUE, "extraInventoryWidth", "Maximum width of extra inventory space");


    @Override
    public String getName() {
        return "client";
    };

    private static class Comments {
        static String
        chemistry = "Many many molecules",
        configurationButtons = "The buttons to open Destroy's configurations which appear on the main menu and pause menu",
        temperamentalItemDescriptions = "Enable the tooltip for Items which are likely to change in the full release of Destroy.";
    };
}
