package com.petrolpark.destroy.client.gui.screen;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import com.petrolpark.destroy.client.gui.DestroyGuiTextures;
import com.petrolpark.destroy.network.DestroyMessages;
import com.petrolpark.destroy.network.packet.SelectGlassblowingRecipeC2SPacket;
import com.petrolpark.destroy.recipe.GlassblowingRecipe;
import com.simibubi.create.foundation.gui.AbstractSimiScreen;
import com.simibubi.create.foundation.gui.widget.AbstractSimiWidget;
import com.simibubi.create.foundation.recipe.RecipeFinder;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class BlowpipeScreen extends AbstractSimiScreen {

    private static final Object recipeCacheKey = new Object();

    protected DestroyGuiTextures background;

    protected final InteractionHand hand;
    protected List<GlassblowingRecipe> recipes;

    protected int scroll;

    public BlowpipeScreen(InteractionHand hand) {
        this.hand = hand;
        background = DestroyGuiTextures.BLOWPIPE_BACKGROUND;
    };

    @Override
    protected void init() {
        setWindowSize(background.width, background.height);
        super.init();
        recipes = RecipeFinder.get(recipeCacheKey, minecraft.level, r -> r instanceof GlassblowingRecipe).stream().map(r -> (GlassblowingRecipe)r).toList();
        refreshRecipeButtons();
    };

    public void refreshRecipeButtons() {
        clearWidgets();
        for (int i = 0; i < 3; i++) {
            if (scroll + i >= recipes.size()) return;
            addRenderableWidget(new RecipeButton(scroll + i, guiLeft + 8, 15 + guiTop + (i * 18)));
        };
    };

    @Override
    protected void renderWindow(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        background.render(graphics, guiLeft, guiTop);
        PoseStack ms = graphics.pose();
        ms.pushPose();
        ms.translate(guiLeft, guiTop, 100);
        for (int i = 0; i < 3; i++) {
            if (scroll + i >= recipes.size()) continue;
            ItemStack stack = recipes.get(scroll + i).getResultItem(minecraft.level.registryAccess());
            graphics.renderItem(stack, 56, 15 + i * 18, i);
        };
        ms.popPose();
    };

    public class RecipeButton extends AbstractSimiWidget {

        protected RecipeButton(int recipeNo, int x, int y) {
            super(x, y, 64, 18);
            withCallback(() -> {
                if (recipeNo >= recipes.size()) return;
                DestroyMessages.sendToServer(new SelectGlassblowingRecipeC2SPacket(hand, recipes.get(recipeNo).getId()));
                if (minecraft != null && minecraft.player != null) minecraft.player.closeContainer();
            });
        };

        @Override
        protected void doRender(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
            (isMouseOver(mouseX, mouseY) ? DestroyGuiTextures.BLOWPIPE_RECIPE_SELECTED : DestroyGuiTextures.BLOWPIPE_RECIPE).render(graphics, getX(), getY());
        };

    };
    
};
