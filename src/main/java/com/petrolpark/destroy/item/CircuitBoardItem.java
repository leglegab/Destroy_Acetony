package com.petrolpark.destroy.item;

import java.util.Optional;
import java.util.function.Consumer;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.client.gui.DestroyGuiTextures;
import com.petrolpark.destroy.item.renderer.CircuitPatternItemRenderer;
import com.petrolpark.destroy.item.tooltip.CircuitPatternTooltip;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class CircuitBoardItem extends CircuitPatternItem {

    public CircuitBoardItem(Properties properties) {
        super(properties);
    };

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return Optional.of(new CircuitPatternTooltip(getPattern(stack), true, DestroyGuiTextures.CIRCUIT_BOARD_BORDER, DestroyGuiTextures.CIRCUIT_BOARD_CELL, DestroyGuiTextures.CIRCUIT_BOARD_CELL_SHADING));
    };

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new CircuitPatternItemRenderer(false, Destroy.asResource("item/circuit_pattern/circuit_board"))));
    };
    
};
