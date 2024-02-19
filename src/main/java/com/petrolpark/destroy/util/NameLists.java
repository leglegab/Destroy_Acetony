package com.petrolpark.destroy.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.gson.Gson;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

public class NameLists {

    public static final Listener RELOAD_LISTENER = new Listener();
    
    public static final Map<ResourceLocation, List<String>> NAME_LISTS = new HashMap<>();

    private static class Listener implements ResourceManagerReloadListener {

        private static final Gson GSON = new Gson();

        @Override
        public void onResourceManagerReload(ResourceManager resourceManager) {
            Minecraft minecraft = Minecraft.getInstance();
            for (String namespace : resourceManager.getNamespaces()) {
                Optional<Resource> resource = resourceManager.getResource(new ResourceLocation(namespace, "lang/destroy_compat/name_lists/"+minecraft.getLanguageManager().getSelected()+".json"));
                if (resource.isEmpty()) resource = resourceManager.getResource(new ResourceLocation(namespace, "lang/destroy_compat/name_lists/en_us.json")); // Default to American English
                if (!resource.isEmpty()) {

                };
            };
        };



    };
};
