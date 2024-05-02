package com.petrolpark.destroy.compat.jei.category;

import java.util.List;
import java.util.Collections;

import com.petrolpark.destroy.block.VatControllerBlock;
import com.petrolpark.destroy.client.gui.DestroyGuiTextures;
import com.petrolpark.destroy.config.DestroyAllConfigs;
import com.petrolpark.destroy.util.DestroyLang;
import com.petrolpark.destroy.util.vat.VatMaterial;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipHelper.Palette;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class VatMaterialCategory extends DestroyRecipeCategory<VatMaterialCategory.VatMaterialRecipe> {

    private final Minecraft mc;

    public VatMaterialCategory(Info<VatMaterialRecipe> info, IJeiHelpers helpers) {
        super(info, helpers);
        mc = Minecraft.getInstance();
    };

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, VatMaterialRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 2, 2)
            .setBackground(getRenderedSlot(), -1, -1)
            .addItemStack(new ItemStack(recipe.block));
    };

    @Override
    public List<Component> getTooltipStrings(VatMaterialRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseY > textY && mouseY < textY + textSeparation * 5) {
            if (mouseY < textY + textSeparation * 2) {
                return TooltipHelper.cutStringTextComponent(DestroyLang.translate("tooltip.vat_material.pressure.description").string(), DestroyLang.WHITE_AND_WHITE);
            } else if (mouseY < textY + textSeparation * 4) {
                return TooltipHelper.cutStringTextComponent(DestroyLang.translate("tooltip.vat_material.conductivity.description").string(), DestroyLang.WHITE_AND_WHITE);
            } else {
                return TooltipHelper.cutStringTextComponent(DestroyLang.translate("tooltip.vat_material.transparent.description").string(), DestroyLang.WHITE_AND_WHITE);
            }
        };
        return Collections.emptyList();
    };

    private final int textX = 2;
    private final int textY = 28;
    private final int textSeparation = 11;

    @Override
    public void draw(VatMaterialRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.drawString(mc.font, recipe.block.getName(), 24, 7, 0xFFFFFF);
        DestroyGuiTextures.JEI_LINE.render(guiGraphics, 2, 22);
        VatMaterial material = recipe.material;
        guiGraphics.drawString(mc.font, DestroyLang.vatMaterialMaxPressure(material, Palette.GRAY_AND_WHITE), textX, textY, 0xFFFFFF);
        guiGraphics.drawString(mc.font, DestroyLang.vatMaterialConductivity(material, Palette.GRAY_AND_WHITE), textX, textY + textSeparation * 2, 0xFFFFFF);
        guiGraphics.drawString(mc.font, DestroyLang.vatMaterialTransparent(material, Palette.GRAY_AND_WHITE), textX, textY + textSeparation * 4, 0xFFFFFF);
        if (DestroyAllConfigs.CLIENT.chemistry.nerdMode.get()) {
            guiGraphics.drawString(mc.font, DestroyLang.translate("tooltip.vat_material.pressure.nerd_mode", material.maxPressure() / 1000f).style(ChatFormatting.WHITE).component(), textX, textY + textSeparation, 0xFFFFFF);
            guiGraphics.drawString(mc.font, DestroyLang.translate("tooltip.vat_material.conductivity.nerd_mode", material.thermalConductivity()).style(ChatFormatting.WHITE).component(), textX, textY + textSeparation * 3, 0xFFFFFF);
        };
    };

    public static List<VatMaterialRecipe> getAllRecipes()  {
        return VatMaterial.BLOCK_MATERIALS.entrySet().stream().filter(entry -> !(entry.getKey() instanceof VatControllerBlock)).map(entry -> new VatMaterialRecipe(entry.getValue(), entry.getKey())).toList();
    };

    public static class VatMaterialRecipe extends ShapelessRecipe {

        public final Block block;
        public final VatMaterial material;

        public VatMaterialRecipe(VatMaterial material, Block block) {
            super(ForgeRegistries.BLOCKS.getKey(block), "", CraftingBookCategory.MISC, ItemStack.EMPTY, NonNullList.create());
            this.block = block;
            this.material = material;
        };

    };
};
