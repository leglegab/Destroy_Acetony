package com.petrolpark.destroy.events;

import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Stream;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.block.color.SmogAffectedBlockColor;
import com.petrolpark.destroy.chemistry.naming.SaltNameOverrides;
import com.petrolpark.destroy.client.model.UniversalArmorTrimModel;
import com.petrolpark.destroy.item.CircuitMaskItem.CircuitMaskTooltip;
import com.petrolpark.destroy.item.MoleculeDisplayItem.MoleculeTooltip;
import com.petrolpark.destroy.util.NameLists;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Destroy.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DestroyClientModEvents {

    @SubscribeEvent
    public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(SaltNameOverrides.RELOAD_LISTENER);
        event.registerReloadListener(NameLists.RELOAD_LISTENER);
    };

    /**
     * Override all the color generators to account for the {@link com.petrolpark.destroy.capability.level.pollution.LevelPollution.PollutionType smog level}.
     * @param event
     */
    @SubscribeEvent
    public static void changeBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(SmogAffectedBlockColor.GRASS, Blocks.GRASS, Blocks.GRASS_BLOCK, Blocks.FERN, Blocks.TALL_GRASS);
        event.register(SmogAffectedBlockColor.DOUBLE_TALL_GRASS, Blocks.TALL_GRASS, Blocks.LARGE_FERN);
        event.register(SmogAffectedBlockColor.PINK_PETALS, Blocks.PINK_PETALS);
        event.register(SmogAffectedBlockColor.FOLIAGE, Blocks.OAK_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.VINE, Blocks.MANGROVE_LEAVES);
        event.register(SmogAffectedBlockColor.BIRCH, Blocks.BIRCH_LEAVES);
        event.register(SmogAffectedBlockColor.SPRUCE, Blocks.SPRUCE_LEAVES);
        event.register(SmogAffectedBlockColor.WATER, Blocks.WATER, Blocks.BUBBLE_COLUMN, Blocks.WATER_CAULDRON);
        event.register(SmogAffectedBlockColor.SUGAR_CANE, Blocks.SUGAR_CANE);
    };

    @SubscribeEvent
    public static void onRegisterClientTooltipComponentFactories(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(MoleculeTooltip.class, MoleculeTooltip::getClientTooltipComponent);
        event.register(CircuitMaskTooltip.class, CircuitMaskTooltip::getClientTooltipComponent);
    };

    public static final ResourceLocation trimTypePredicateLocation = new ResourceLocation("trim_type");

    @SubscribeEvent
    public static void onModelBakel(ModelEvent.ModifyBakingResult event) {
        List<Entry<ResourceLocation, BakedModel>> modelsToReplace = event.getModels().entrySet()
            .stream()
            .filter(entry ->
                entry.getKey().toString().endsWith("#inventory")
                && entry.getValue() instanceof SimpleBakedModel // Don't override anything complicated
                && Stream.of(entry.getValue().getOverrides().properties).anyMatch(trimTypePredicateLocation::equals) // Check if this item is likely to support trims
            ) .toList();
        for (Entry<ResourceLocation, BakedModel> entry : modelsToReplace) 
            event.getModels().put(entry.getKey(), new UniversalArmorTrimModel(entry.getValue())); // Replace the model with one which wraps the old one but also provides the additional Armor Trims
    };

};
