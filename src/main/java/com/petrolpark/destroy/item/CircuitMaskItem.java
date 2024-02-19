package com.petrolpark.destroy.item;

import java.util.function.Consumer;
import java.util.Optional;

import com.mojang.blaze3d.vertex.PoseStack;
import com.petrolpark.destroy.client.gui.DestroyGuiTextures;
import com.petrolpark.destroy.item.directional.DirectionalTransportedItemStack;
import com.petrolpark.destroy.item.renderer.CircuitMaskItemRenderer;
import com.petrolpark.destroy.item.tooltip.DestroyTooltipComponent;
import com.petrolpark.destroy.util.DestroyLang;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class CircuitMaskItem extends CircuitPatternItem {

    public CircuitMaskItem(Properties properties) {
        super(properties);
    };

    public static final ChatFormatting[] directionColors = new ChatFormatting[]{ChatFormatting.WHITE, ChatFormatting.WHITE, ChatFormatting.RED, ChatFormatting.WHITE, ChatFormatting.YELLOW, ChatFormatting.BLUE};

    @Override
    public void launch(DirectionalTransportedItemStack stack, Direction launchDirection) {
        // If it is 'flipped', the Item has been rotated around 180 the north-south axis before being rotated around the up-down axis
        CompoundTag tag = stack.stack.getOrCreateTag();
        boolean alreadyFlipped = tag.contains("Flipped");
        tag.remove("Flipped");

        Rotation rotation = stack.getRotation();
        if (
            launchDirection.getAxis() == Axis.Z && (rotation == Rotation.NONE || rotation == Rotation.CLOCKWISE_90)
            || launchDirection.getAxis() == Axis.X && (rotation == Rotation.COUNTERCLOCKWISE_90 || rotation == Rotation.CLOCKWISE_180)
        ) {
            stack.rotate(Rotation.CLOCKWISE_180); // Fix the Rotation if certain orientations are flipped over certain axes
        };

        if (!alreadyFlipped) tag.putBoolean("Flipped", true);
        super.launch(stack, launchDirection);
    };

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        stack.getOrCreateTag().remove("Flipped");
        stack.getOrCreateTag().remove("RotationWhileFlying");

        if (level.isClientSide() && isSelected && entity instanceof Player player && player.isCrouching()) {
            Direction direction = player.getDirection();
            player.displayClientMessage(DestroyLang.translate("tooltip.circuit_mask.facing_direction", DestroyLang.direction(direction).style(directionColors[direction.ordinal()])).component(), true);
        };
    };

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return Optional.of(new CircuitMaskTooltip(getPattern(stack)));
    };

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new CircuitMaskItemRenderer()));
    };

    public static class CircuitMaskTooltip extends DestroyTooltipComponent<CircuitMaskTooltip, ClientCircuitMaskTooltipComponent> {

        public CircuitMaskTooltip(int pattern) {
            super(tooltip -> new ClientCircuitMaskTooltipComponent(pattern));
        };

    };

    public static class ClientCircuitMaskTooltipComponent implements ClientTooltipComponent {

        public final int pattern;

        public ClientCircuitMaskTooltipComponent(int pattern) {
            this.pattern = pattern;
        };

        @Override
        public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
            PoseStack ms = guiGraphics.pose();
            ms.pushPose();
            ms.translate(x, y, 250f);
            for (int i = 0; i < 16; i++) {
                if (isPunched(pattern, i)) continue;
                DestroyGuiTextures.CIRCUIT_MASK_CELL_SHADING.render(guiGraphics, 7 + (i % 4) * 8, 7 + (i / 4) * 8);
            };
            DestroyGuiTextures.CIRCUIT_MASK_BORDER.render(guiGraphics, 0, 0);
            for (int i = 0; i < 16; i++) {
                if (isPunched(pattern, i)) continue;
                DestroyGuiTextures.CIRCUIT_MASK_CELL.render(guiGraphics, 8 + (i % 4) * 8, 8 + (i / 4) * 8);
            };
            ms.popPose();
        };

        @Override
        public int getHeight() {
            return 48;
        };

        @Override
        public int getWidth(Font font) {
            return 48;
        };

    };
    
};
