package com.petrolpark.destroy.config;

public class DestroyClientConfigs extends DestroyConfigBase {

    public final ConfigBool tempramentalItemDescriptions = b(true, "TempramentalItemDescriptions", Comments.temperamentalItemDescriptions);

    public final DestroyClientChemistryConfigs chemistry = nested(0, DestroyClientChemistryConfigs::new, Comments.chemistry);
    public final DestroyMenuButtonConfig configurationButtons = nested(0, DestroyMenuButtonConfig::new, Comments.configurationButtons);
    
    public final ConfigGroup extraInventory = group(0, "extraInventory");
    public final ConfigBool extraInventoryLeft = b(true, "extraHotbarLeft", "Whether the extra slots render to the left rather than right of containers");
    public final ConfigInt extraInventoryWidth = i(3, 1, Integer.MAX_VALUE, "extraInventoryWidth", "Maximum width of extra inventory space");
    public final ConfigEnum<ExtraHotbarSlotLocation> extraHotbarSlotLocation = e(ExtraHotbarSlotLocation.ALL_LEFT, "extraHotbarSlotLocation", "Where the additional hotbar slots go", "ALL_X - All slots on X", "START_X - Alternate sides, starting on X", "PRIORITIZE_X - Up to extraHotbarPrioritySlotCount on X, the rest on the other side");
    public final ConfigInt extraHotbarPrioritySlotCount = i(3, 0, Integer.MAX_VALUE, "extraHotbarPrioritySlotCount");

    public static enum ExtraHotbarSlotLocation {
        
        ALL_LEFT,
        ALL_RIGHT,
        START_LEFT,
        START_RIGHT,
        PRIORITY_LEFT,
        PRIORITY_RIGHT;
    };

    public static int getLeftSlots(int extraSlots) {
        if (extraSlots == 0) return 0;
        switch (DestroyAllConfigs.CLIENT.extraHotbarSlotLocation.get()) {
            case ALL_LEFT: return extraSlots;
            case ALL_RIGHT: return 0;
            case START_LEFT: return (extraSlots % 2) + (extraSlots / 2);
            case START_RIGHT: return extraSlots / 2;
            case PRIORITY_LEFT: return Math.min(extraSlots, DestroyAllConfigs.CLIENT.extraHotbarPrioritySlotCount.get());
            case PRIORITY_RIGHT: return Math.max(0, extraSlots - DestroyAllConfigs.CLIENT.extraHotbarPrioritySlotCount.get());
        };
        return 0;
    };

    public static int getRightSlots(int extraSlots) {
        return extraSlots - getLeftSlots(extraSlots);
    };

    public ExtraInventoryClientSettings getExtraInventorySettings() {
        return new ExtraInventoryClientSettings(extraInventoryLeft.get(), extraInventoryWidth.get(), extraHotbarSlotLocation.get(), extraHotbarPrioritySlotCount.get());
    };

    public static class ExtraInventoryClientSettings {
        private final boolean left;
        private final int width;
        private final ExtraHotbarSlotLocation loc;
        private final int hotbarCount;

        public ExtraInventoryClientSettings(boolean left, int width, ExtraHotbarSlotLocation loc, int hotbarCount) {
            this.left = left;
            this.width = width;
            this.loc = loc;
            this.hotbarCount = hotbarCount;
        };

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof ExtraInventoryClientSettings settings)) return false;
            return (settings.left == left) && settings.width == width && settings.loc == loc && settings.hotbarCount == hotbarCount;
        };
    };

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
