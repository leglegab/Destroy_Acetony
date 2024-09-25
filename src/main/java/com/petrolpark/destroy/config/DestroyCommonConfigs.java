package com.petrolpark.destroy.config;

public class DestroyCommonConfigs extends DestroyConfigBase {
    
    public final ConfigBool enableBabyBlue = b(true, "enableBabyBlue", "Allow the crafting and effects of Baby Blue-related products");
    public final ConfigBool enableAlcohol = b(true, "enableAlcohol", "Allow the crafting and effects of alcoholic products");
};
