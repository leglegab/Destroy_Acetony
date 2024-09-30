package com.petrolpark.destroy.events;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.mutable.MutableObject;

import com.mojang.datafixers.util.Either;
import com.petrolpark.destroy.DestroyClient;
import com.petrolpark.destroy.block.renderer.BlockEntityBehaviourRenderer;
import com.petrolpark.destroy.capability.Pollution.PollutionType;
import com.petrolpark.destroy.client.gui.button.OpenDestroyMenuButton;
import com.petrolpark.destroy.client.gui.screen.CustomExplosiveScreen;
import com.petrolpark.destroy.config.DestroyAllConfigs;
import com.petrolpark.destroy.item.ICustomExplosiveMixItem;
import com.petrolpark.destroy.item.SwissArmyKnifeItem;
import com.petrolpark.destroy.item.renderer.SeismometerItemRenderer;
import com.petrolpark.destroy.item.tooltip.ExplosivePropertiesTooltip;
import com.petrolpark.destroy.mixin.accessor.MenuRowsAccessor;
import com.petrolpark.destroy.util.DestroyLang;
import com.petrolpark.destroy.util.FireproofingHelper;
import com.petrolpark.destroy.util.PollutionHelper;
import com.petrolpark.destroy.world.explosion.ExplosiveProperties;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.Color;
import com.simibubi.create.infrastructure.gui.OpenCreateMenuButton.MenuRows;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.FogType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.event.ViewportEvent.ComputeFogColor;
import net.minecraftforge.client.event.ViewportEvent.RenderFog;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(Dist.CLIENT)
public class DestroyClientEvents {

    @Nonnull
    private static Color BROWN = new Color(0xFF4D2F19);

    /**
     * Tick a couple of renderers.
     * @param event
     */
    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            SeismometerItemRenderer.tick();
            SwissArmyKnifeItem.clientPlayerTick();
            DestroyClient.FOG_HANDLER.tick();
            DestroyClient.EXTENDED_INVENTORY_HANDLER.tick(event);
        } else {
            BlockEntityBehaviourRenderer.tick();
        };
    };

    /**
     * Render fog according to the world's Smog Level.
     */
    @SubscribeEvent
    public static void renderFog(RenderFog event) {
        if (!DestroyClientEvents.smogEnabled()) return;
        if (event.getType() == FogType.NONE) {
            Minecraft mc = Minecraft.getInstance();
            float smog = (float)PollutionHelper.getPollution(mc.level, mc.player.blockPosition(), PollutionType.SMOG);
            event.scaleNearPlaneDistance(1f - (0.8f * smog / (float)PollutionType.SMOG.max));
            event.scaleFarPlaneDistance(1f - (0.5f * smog / (float)PollutionType.SMOG.max));
            event.setCanceled(true);
        };
    };

    /**
     * Set the color of Smog.
     */
    @SubscribeEvent
    public static void colorFog(ComputeFogColor event) {
        if (!DestroyClientEvents.smogEnabled()) return;
        if (event.getCamera().getFluidInCamera() == FogType.NONE) {
            Minecraft mc = Minecraft.getInstance();
            float smog = (float)PollutionHelper.getPollution(mc.level, mc.player.blockPosition(), PollutionType.SMOG);
            Color existing = new Color(event.getRed(), event.getGreen(), event.getBlue(), 1f);
            DestroyClient.FOG_HANDLER.setTargetColor(Color.mixColors(existing, BROWN, 0.8f * smog / (float)PollutionType.SMOG.max));
            Color color = DestroyClient.FOG_HANDLER.getColor(AnimationTickHolder.getPartialTicks());
            event.setRed(color.getRedAsFloat());
            event.setGreen(color.getGreenAsFloat());
            event.setBlue(color.getBlueAsFloat());
        };
    };

    public static boolean smogEnabled() {
        return PollutionHelper.pollutionEnabled() && DestroyAllConfigs.SERVER.pollution.smog.get();
    };

    /**
     * Add buttons to open Destroy's configurations to the main and pause menus.
     * All copied from the {@link com.simibubi.create.infrastructure.gui.OpenCreateMenuButton.OpenConfigButtonHandler#onGuiInit Create source code}.
     * @param event
     */
    @SubscribeEvent
    public static void onGuiInit(ScreenEvent.Init event) {
        Screen gui = event.getScreen();

        MenuRows menu = null;
        int rowIdx = 0;
        int offsetX = 0;

        if (gui instanceof TitleScreen) {
            menu = MenuRows.MAIN_MENU;
            rowIdx = DestroyAllConfigs.CLIENT.configurationButtons.mainMenuConfigButtonRow.get();
            offsetX =  DestroyAllConfigs.CLIENT.configurationButtons.mainMenuConfigButtonOffsetX.get();
        } else if (gui instanceof PauseScreen) {
            menu = MenuRows.INGAME_MENU;
            rowIdx =  DestroyAllConfigs.CLIENT.configurationButtons.pauseMenuConfigButtonRow.get();
            offsetX =  DestroyAllConfigs.CLIENT.configurationButtons.pauseMenuConfigButtonOffsetX.get();
        };

        if (rowIdx != 0 && menu != null) {
            boolean onLeft = offsetX < 0;
            String target =  I18n.get((onLeft ? ((MenuRowsAccessor)menu).getLeftButtons() : ((MenuRowsAccessor)menu).getRightButtons()).get(rowIdx - 1));

            int offsetX_ = offsetX;
            MutableObject<GuiEventListener> toAdd = new MutableObject<>(null);
            event.getListenersList()
                .stream()
                .filter(w -> w instanceof AbstractWidget)
                .map(w -> (AbstractWidget) w)
                .filter(w -> w.getMessage()
                    .getString()
                    .equals(target)
                ).findFirst()
                .ifPresent(w ->
                    toAdd.setValue(new OpenDestroyMenuButton(w.getX() + offsetX_ + (onLeft ? -20 : w.getWidth()), w.getY()))
                );
            if (toAdd.getValue() != null) event.addListener(toAdd.getValue());
        };
    };

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();

        // Add a bit of pedantry to TNT
        if (item.equals(Items.TNT)) event.getToolTip().add(DestroyLang.translate("tooltip.tnt").style(ChatFormatting.GRAY).component());

        // Inform of fireproof items
        if (FireproofingHelper.isFireproof(stack))
            event.getToolTip().add(DestroyLang.translate("tooltip.fireproof").style(ChatFormatting.RED).component());
    };

    @SubscribeEvent
    public static void onGatherTooltips(RenderTooltipEvent.GatherComponents event) {
        Minecraft mc = Minecraft.getInstance();
        ExplosiveProperties properties = null;
        if (event.getItemStack().getItem() instanceof ICustomExplosiveMixItem mixItem) {
            properties = mixItem.getExplosiveInventory(event.getItemStack()).getExplosiveProperties().withConditions(mixItem.getApplicableExplosionConditions());
        } else if (mc.screen instanceof CustomExplosiveScreen) {
            properties = ExplosiveProperties.ITEM_EXPLOSIVE_PROPERTIES.get(event.getItemStack().getItem());
        };
        if (properties != null) event.getTooltipElements().add(Either.right(new ExplosivePropertiesTooltip(properties)));
    };

    @SubscribeEvent
    public static void onOpenContainerScreen(ScreenEvent.Init.Post event) {
        DestroyClient.EXTENDED_INVENTORY_HANDLER.onOpenContainerScreen(event);
    };

    @SubscribeEvent
    public static void onRenderScreen(ScreenEvent.Render.Pre event) {
        DestroyClient.EXTENDED_INVENTORY_HANDLER.renderScreen(event);
    };

    @SubscribeEvent
    public static void onCloseScreen(ScreenEvent.Closing event) {
        DestroyClient.EXTENDED_INVENTORY_HANDLER.onCloseScreen(event);
    };
};
