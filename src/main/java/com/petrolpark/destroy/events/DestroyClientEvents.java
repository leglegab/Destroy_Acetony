package com.petrolpark.destroy.events;

import javax.annotation.Nonnull;

import net.minecraft.client.resources.language.I18n;
import org.apache.commons.lang3.mutable.MutableObject;

import com.jozufozu.flywheel.util.Color;
import com.petrolpark.destroy.block.renderer.BlockEntityBehaviourRenderer;
import com.petrolpark.destroy.capability.level.pollution.ClientLevelPollutionData;
import com.petrolpark.destroy.capability.level.pollution.LevelPollution;
import com.petrolpark.destroy.capability.level.pollution.LevelPollution.PollutionType;
import com.petrolpark.destroy.client.gui.button.OpenDestroyMenuButton;
import com.petrolpark.destroy.config.DestroyAllConfigs;
import com.petrolpark.destroy.item.SwissArmyKnifeItem;
import com.petrolpark.destroy.item.renderer.SeismometerItemRenderer;
import com.petrolpark.destroy.mixin.accessor.MenuRowsAccessor;
import com.petrolpark.destroy.util.CogwheelChainingHandler;
import com.petrolpark.destroy.util.PollutionHelper;
import com.simibubi.create.infrastructure.gui.OpenCreateMenuButton.MenuRows;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.world.level.material.FogType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.event.ViewportEvent.ComputeFogColor;
import net.minecraftforge.client.event.ViewportEvent.RenderFog;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

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
            CogwheelChainingHandler.tick();
            SeismometerItemRenderer.tick();
            SwissArmyKnifeItem.clientPlayerTick();
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
            LevelPollution levelPollution = ClientLevelPollutionData.getLevelPollution();
            if (levelPollution == null) return;
            event.scaleNearPlaneDistance(1f - (0.8f * (float)levelPollution.get(PollutionType.SMOG) / (float)PollutionType.SMOG.max));
            event.scaleFarPlaneDistance(1f - (0.5f * (float)levelPollution.get(PollutionType.SMOG) / (float)PollutionType.SMOG.max));
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
            LevelPollution levelPollution = ClientLevelPollutionData.getLevelPollution();
            if (levelPollution == null) return;
            Color existing = new Color(event.getRed(), event.getGreen(), event.getBlue(), 1f);
            Color color = Color.mixColors(existing, BROWN, 0.8f * (float)levelPollution.get(PollutionType.SMOG) / (float)PollutionType.SMOG.max);
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
        Screen screen = event.getScreen();

        MenuRows menu;
        int rowIdx;
        int offsetX;
        if (screen instanceof TitleScreen) {
            menu = MenuRows.MAIN_MENU;
            rowIdx = DestroyAllConfigs.client().mainMenuConfigButtonRow.get();
            offsetX = DestroyAllConfigs.client().mainMenuConfigButtonOffsetX.get();
        } else if (screen instanceof PauseScreen) {
            menu = MenuRows.INGAME_MENU;
            rowIdx = DestroyAllConfigs.client().ingameMenuConfigButtonRow.get();
            offsetX = DestroyAllConfigs.client().ingameMenuConfigButtonOffsetX.get();
        } else {
            return;
        }

        if (rowIdx == 0) {
            return;
        }

        boolean onLeft = offsetX < 0;
        String targetMessage = I18n.get((onLeft ? ((MenuRowsAccessor)menu).leftTextKeys() : ((MenuRowsAccessor)menu).rightTextKeys()).get(rowIdx - 1));

        int offsetX_ = offsetX;
        MutableObject<GuiEventListener> toAdd = new MutableObject<>(null);
        event.getListenersList()
                .stream()
                .filter(w -> w instanceof AbstractWidget)
                .map(w -> (AbstractWidget) w)
                .filter(w -> w.getMessage()
                        .getString()
                        .equals(targetMessage))
                .findFirst()
                .ifPresent(w -> toAdd
                        .setValue(new OpenDestroyMenuButton(w.getX() + offsetX_*2 + (onLeft ? -40 : w.getWidth()), w.getY())));
        if (toAdd.getValue() != null)
            event.addListener(toAdd.getValue());
    };
};
