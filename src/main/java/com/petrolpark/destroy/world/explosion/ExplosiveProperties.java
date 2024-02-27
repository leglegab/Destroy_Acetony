package com.petrolpark.destroy.world.explosion;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.petrolpark.destroy.util.DestroyLang;
import com.petrolpark.destroy.util.DestroyReloadListener;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition.IContext;

public class ExplosiveProperties extends EnumMap<ExplosiveProperties.ExplosiveProperty, Float> {

    public static final Map<Item, ExplosiveProperties> ITEM_EXPLOSIVE_PROPERTIES = new HashMap<>();
  
    public ExplosiveProperties() {
        super(Arrays.stream(ExplosiveProperty.values()).collect(Collectors.toMap(p -> p, p -> 0f)));
    };

    public CompoundTag write() {
        CompoundTag tag = new CompoundTag();
        forEach((p, f) -> {
            if (f != 0f) tag.putFloat(p.name(), f);
        });
        return tag;
    };

    public static ExplosiveProperties read(CompoundTag tag) {
        ExplosiveProperties properties = new ExplosiveProperties();
        for (ExplosiveProperty property : ExplosiveProperty.values()) {
            properties.put(property, tag.getFloat(property.name()));
        };
        return properties;
    };

    public static ExplosiveProperties fromJson(JsonObject json) {
        ExplosiveProperties properties = new ExplosiveProperties();
        for (ExplosiveProperty property : ExplosiveProperty.values()) {
            if (json.has(property.name())) {
                try { properties.put(property, json.get(property.name()).getAsFloat()); } catch (Throwable e) {};
            };
        };
        return properties;
    };

    public static enum ExplosiveProperty {

        ENERGY,
        OXYGEN_BALANCE,
        TEMPERATURE,
        BRISANCE,
        SENSITIVTY;

        public Component getName() {
            return DestroyLang.translate("tooltip.explosive_property."+name()).component();
        };
    };

    public static class Listener extends DestroyReloadListener {

        public final IContext context;

        public Listener(IContext context) {
            super();
            this.context = context;
        };

        @Override
        public String getPath() {
            return "destroy_compat/explosive_items";
        };

        @Override
        public void beforeReload() {
            ITEM_EXPLOSIVE_PROPERTIES.clear();
            super.beforeReload();
        };

        @Override
        @SuppressWarnings("deprecation")
        public void forEachNameSpaceJsonFile(JsonObject jsonObject) {
            for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                JsonObject object = entry.getValue().getAsJsonObject();
                if (!CraftingHelper.processConditions(object, "conditions", this.context)) return;

                Optional<? extends Holder<Item>> itemOptional = BuiltInRegistries.ITEM.asLookup().get(ResourceKey.create(Registries.ITEM, new ResourceLocation(entry.getKey())));
                if (itemOptional.isEmpty()) throw new IllegalStateException("Invalid item ID: "+entry.getKey());
                ITEM_EXPLOSIVE_PROPERTIES.put(itemOptional.get().value(), fromJson(object));
            };
        };

    };
};
