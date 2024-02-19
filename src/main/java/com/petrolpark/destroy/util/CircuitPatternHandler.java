package com.petrolpark.destroy.util;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Map;
import java.util.function.Supplier;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.petrolpark.destroy.item.CircuitPatternItem;
import com.petrolpark.destroy.network.DestroyMessages;
import com.petrolpark.destroy.network.packet.CircuitPatternsS2CPacket;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public class CircuitPatternHandler {

    public static Listener RELOAD_LISTENER = new Listener();
  
    public static final Map<String, Supplier<CircuitPatternGenerator>> GENERATOR_KEYS = new HashMap<>();
    private static final Map<ResourceLocation, CircuitPatternGenerator> PATTERN_GENERATORS = new HashMap<>();
    private static final Map<ResourceLocation, Integer> GENERATED_PATTERNS = new HashMap<>();

    static {

    };

    public static Integer getPattern(ResourceLocation location) {
        if (!GENERATED_PATTERNS.containsKey(location) && !PATTERN_GENERATORS.containsKey(location)) throw new IllegalStateException("No such circuit pattern "+location.toString());
        return GENERATED_PATTERNS.computeIfAbsent(location, rl -> {
            CircuitPatternGenerator gen = PATTERN_GENERATORS.get(rl);
            return gen == null ? null : gen.generatePattern();
        });
    };

    public static Map<ResourceLocation, Integer> getAllPatterns() {
        Map<ResourceLocation, Integer> patterns = new HashMap<>(PATTERN_GENERATORS.size());
        for (ResourceLocation rl : PATTERN_GENERATORS.keySet()) patterns.put(rl, getPattern(rl));
        return patterns;
    };

    public static void setPatterns(Map<ResourceLocation, Integer> patterns) {
        GENERATED_PATTERNS.putAll(patterns);
    };

    public static interface CircuitPatternGenerator {

        public int generatePattern();

        public static class Specific implements CircuitPatternGenerator {

            public final int pattern;

            public Specific(int pattern) {
                this.pattern = pattern;
            };

            @Override
            public int generatePattern() {
                return pattern;
            };

        };
    };

    public static class Listener extends SimpleJsonResourceReloadListener {

        private static final Gson GSON = new Gson();

        public Listener() {
            super(GSON, "destroy_compat/circuit_patterns");
        };

        @Override
        protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager manager, ProfilerFiller profiler) {
            for (Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
                ResourceLocation rl = entry.getKey();
                try {
                    String generationString = entry.getValue().getAsJsonObject().get("pattern").getAsString();
                    CircuitPatternGenerator generator;
                    if (GENERATOR_KEYS.containsKey(generationString)) {
                        generator = GENERATOR_KEYS.get(generationString).get();
                    } else if (generationString.length() == 16) {
                        int pattern = 0;
                        for (int i = 0; i < 16; i++) {
                            if (generationString.charAt(i) != ' ') pattern = CircuitPatternItem.punch(pattern, i);
                        };
                        generator = new CircuitPatternGenerator.Specific(pattern);
                    } else {
                        throw new JsonSyntaxException("Circuit patterns must specify an exact pattern or a circuit pattern generator");
                    };
                    PATTERN_GENERATORS.put(rl, generator);
                } catch (Throwable e) {
                    throw new JsonSyntaxException("Could not read circuit pattern generator '"+rl+"': ", e);
                };
            };
            try { DestroyMessages.sendToAllClients(new CircuitPatternsS2CPacket(GENERATED_PATTERNS)); } catch (Throwable e) {};
        };

    };
};
