package com.petrolpark.destroy.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.petrolpark.destroy.Destroy;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

public class NameLists {

    public static final Listener RELOAD_LISTENER = new Listener();
    
    private static final Map<ResourceLocation, List<String>> NAME_LISTS = new HashMap<>();

    public static List<String> getNames(ResourceLocation id) {
        List<String> list = NAME_LISTS.get(id);
        if (list == null || list.isEmpty()) return List.of("Empty name list!");
        return list;
    };

    private static class Listener implements ResourceManagerReloadListener {

        private static final Gson GSON = new Gson();

        @Override
        public void onResourceManagerReload(ResourceManager resourceManager) {
            Minecraft minecraft = Minecraft.getInstance();
            for (String namespace : resourceManager.getNamespaces()) {
                ResourceLocation fileLocation = new ResourceLocation(namespace, "lang/destroy_compat/name_lists/"+minecraft.getLanguageManager().getSelected()+".json");
                Optional<Resource> resource = resourceManager.getResource(fileLocation);
                if (resource.isEmpty()) { // Default to American English
                    fileLocation = new ResourceLocation(namespace, "lang/destroy_compat/name_lists/en_us.json");
                    resource = resourceManager.getResource(fileLocation);
                }; 
                if (!resource.isEmpty()) {
                    try (InputStream inputStream = resource.get().open()) {
                        JsonObject jsonObject = GSON.fromJson(new InputStreamReader(inputStream, StandardCharsets.UTF_8), JsonObject.class);
                        for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                            ResourceLocation nameListRl = new ResourceLocation(namespace, entry.getKey());
                            JsonArray jsonArray = entry.getValue().getAsJsonArray();
                            List<String> names = new ArrayList<>(jsonArray.size());
                            jsonArray.forEach(element -> names.add(element.getAsString()));
                            NAME_LISTS.put(nameListRl, names);
                        };
                    } catch (Throwable e) {
                        Destroy.LOGGER.error("Failed to read salt name overrides: " + fileLocation, e);
                    };
                };
            };
        };



    };
};
