package com.petrolpark.destroy.config;

public class DestroyEquipmentConfigs extends DestroyConfigBase {
    
    public final ConfigGroup ppe = group(0, "ppe", "Personal protective equipment");
    public final ConfigInt laboratoryGogglesDurability = i(512, 1, Integer.MAX_VALUE, "laboratoryGogglesDurability", "Durability of Lab Goggles");
    public final ConfigInt goldLaboratoryGogglesDurability = i(1024, 1, Integer.MAX_VALUE, "goldLaboratoryGogglesDurability", "Durability of Solid Gold Lab Goggles");
    public final ConfigInt paperMaskDurability = i(256, 1, Integer.MAX_VALUE, "paperMaskDurability", "Durability of Paper Masks");
    public final ConfigInt gasMaskDurability = i(512, 1, Integer.MAX_VALUE, "gasMaskDurability", "Durability of Gas Masks");
    public final ConfigInt labCoatDurability = i(512, 1, Integer.MAX_VALUE, "labCoatDurability", "Durability of Lab Coats");

};
