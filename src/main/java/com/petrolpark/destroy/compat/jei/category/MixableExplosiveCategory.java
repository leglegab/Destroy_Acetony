package com.petrolpark.destroy.compat.jei.category;

import java.util.List;
import java.util.Map.Entry;

import com.mojang.blaze3d.vertex.PoseStack;
import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.client.gui.DestroyGuiTextures;
import com.petrolpark.destroy.item.tooltip.ExplosivePropertiesTooltip;
import com.petrolpark.destroy.world.explosion.ExplosiveProperties;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class MixableExplosiveCategory extends DestroyRecipeCategory<MixableExplosiveCategory.MixableExplosiveRecipe> {
    
    public MixableExplosiveCategory(Info<MixableExplosiveRecipe> info, IJeiHelpers helpers) {
        super(info, helpers);
    };

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MixableExplosiveRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 2, 2)
            .setBackground(getRenderedSlot(), -1, -1)
            .addItemStack(new ItemStack(recipe.item));
    };

    @Override
    public List<Component> getTooltipStrings(MixableExplosiveRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        return ExplosivePropertiesTooltip.getSelected(recipe.properties, mouseX - 51, mouseY - 33).getTooltip(recipe.properties);
    };

    @Override
    public void draw(MixableExplosiveRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        Minecraft mc = Minecraft.getInstance();

        guiGraphics.drawString(mc.font,recipeSlotsView.getSlotViews(RecipeIngredientRole.INPUT).get(0).getDisplayedItemStack().get().getHoverName(), 24, 7, 0xFFFFFF);

        PoseStack ms = guiGraphics.pose();

        DestroyGuiTextures.CUSTOM_EXPLOSIVE_JEI_BACKGROUND.render(guiGraphics, 45, 24);
        ms.pushPose();
        ms.translate(51f, 33f, 0f);
        ExplosivePropertiesTooltip.renderProperties(recipe.properties, mc.font, guiGraphics, mouseX - 51d, mouseY - 33d);
        ms.popPose();
    };

    public static List<MixableExplosiveRecipe> getAllRecipes() {
        return ExplosiveProperties.ITEM_EXPLOSIVE_PROPERTIES.entrySet().stream().map(MixableExplosiveRecipe::new).toList();
    };
    
    public static class MixableExplosiveRecipe extends ShapelessRecipe {

        private static int id = 0;

        public final Item item;
        public final ExplosiveProperties properties;

        public MixableExplosiveRecipe(Entry<Item, ExplosiveProperties> e) {
            this(e.getKey(), e.getValue());
        };

        public MixableExplosiveRecipe(Item item, ExplosiveProperties properties) {
            super(Destroy.asResource("explosive_properties_"+id++), "", CraftingBookCategory.MISC, ItemStack.EMPTY, NonNullList.create());
            this.item = item;
            this.properties = properties;
        };

    };
};
