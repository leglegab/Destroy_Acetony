package com.petrolpark.destroy.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.petrolpark.destroy.Destroy;
import com.simibubi.create.foundation.gui.UIRenderHelper;
import com.simibubi.create.foundation.gui.element.ScreenElement;
import com.simibubi.create.foundation.utility.Color;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum DestroyGuiTextures implements ScreenElement {

	// CIRCUIT MASK
	CIRCUIT_MASK_BORDER("circuit_mask", 0, 0, 48, 48),
	CIRCUIT_MASK_CELL_SHADING("circuit_mask", 48, 0, 10, 10),
	CIRCUIT_MASK_CELL("circuit_mask", 48, 16, 8, 8),

	// VAT
	VAT("vat", 0, 0, 256, 226),
	VAT_CARD_UNSELECTED("vat", 0, 227, 100, 28),
	VAT_CARD_SELECTED("vat", 116, 226, 102, 30),
	VAT_CARD_ARROW("vat", 218, 226, 25, 30),
	VAT_SCROLL_DOT("vat", 100, 226, 7, 8),

	// REDSTONE PROGRAMMER
	REDSTONE_PROGRAMMER_LEFT("redstone_programmer_1", 0, 0, 256, 256),
	REDSTONE_PROGRAMMER_RIGHT("redstone_programmer_2", 0, 0, 178, 256),
	NOTE_BORDER_MIDDLE("redstone_programmer_2", 192, 0, 4, 18),
	NOTE_BORDER_LEFT("redstone_programmer_2", 196, 0, 4, 18),
	NOTE_BORDER_LONE("redstone_programmer_2", 200, 0, 4, 18),
	NOTE_BORDER_RIGHT("redstone_programmer_2", 204, 0, 4, 18),
	NOTE_0("redstone_programmer_2", 192, 18, 4, 18),
	NOTE_1("redstone_programmer_2", 196, 18, 4, 18),
	NOTE_2("redstone_programmer_2", 200, 18, 4, 18),
	NOTE_3("redstone_programmer_2", 204, 18, 4, 18),
	NOTE_4("redstone_programmer_2", 208, 18, 4, 18),
	NOTE_5("redstone_programmer_2", 212, 18, 4, 18),
	NOTE_6("redstone_programmer_2", 216, 18, 4, 18),
	NOTE_7("redstone_programmer_2", 220, 18, 4, 18),
	NOTE_8("redstone_programmer_2", 224, 18, 4, 18),
	NOTE_9("redstone_programmer_2", 228, 18, 4, 18),
	NOTE_10("redstone_programmer_2", 232, 18, 4, 18),
	NOTE_11("redstone_programmer_2", 236, 18, 4, 18),
	NOTE_12("redstone_programmer_2", 240, 18, 4, 18),
	NOTE_13("redstone_programmer_2", 244, 18, 4, 18),
	NOTE_14("redstone_programmer_2", 248, 18, 4, 18),
	NOTE_15("redstone_programmer_2", 252, 18, 4, 18),
	REDSTONE_PROGRAMMER_LINE("redstone_programmer_2", 179, 16, 2, 199),
	REDSTONE_PROGRAMMER_BARLINE("redstone_programmer_2", 181, 16, 2, 199),

    // JEI
    JEI_SHORT_DOWN_ARROW("jei/widgets", 0, 64, 18, 18),
    JEI_SHORT_RIGHT_ARROW("jei/widgets", 0, 82, 18, 16),
	JEI_EQUILIBRIUM_ARROW("jei/widgets", 0, 96, 42, 11),
	JEI_LINE("jei/widgets", 40, 38, 177, 2),
	JEI_TEXT_BOX_LONG("jei/widgets", 0, 0, 169, 19),
	JEI_TEXT_BOX_SHORT("jei/widgets", 0, 19, 115, 19),
	JEI_DISTILLATION_TOWER_BOTTOM("jei/widgets", 0, 52, 12, 12),
	JEI_DISTILLATION_TOWER_MIDDLE("jei/widgets", 0, 40, 20, 12),
	JEI_DISTILLATION_TOWER_TOP("jei/widgets", 0, 38, 12, 2),
	JEI_DISTILLATION_TOWER_BRANCH("jei/widgets", 20, 45, 20, 2),
	JEI_EXPLOSION("jei/widgets", 169, 0, 18, 21),

	// MISC
	NERD_EMOJI("jei/widgets", 115, 19, 16, 14);

    public final ResourceLocation location;
	public int width, height, startX, startY;

    private DestroyGuiTextures(String location, int startX, int startY, int width, int height) {
		this.location = Destroy.asResource("textures/gui/" + location + ".png");
		this.startX = startX;
		this.startY = startY;
		this.width = width;
		this.height = height;
	};

	private static final DestroyGuiTextures[] notes = new DestroyGuiTextures[]{NOTE_0, NOTE_1, NOTE_2, NOTE_3, NOTE_4, NOTE_5, NOTE_6, NOTE_7, NOTE_8, NOTE_9, NOTE_10, NOTE_11, NOTE_12, NOTE_13, NOTE_14, NOTE_15};

	public static DestroyGuiTextures getRedstoneProgrammerNote(int strength) {
		if (strength > 0 && strength <= 15) return notes[strength];
		return NOTE_0;
	};

    @OnlyIn(Dist.CLIENT)
	public void bind() {
		RenderSystem.setShaderTexture(0, location);
	};

	@OnlyIn(Dist.CLIENT)
	public void render(GuiGraphics graphics, int x, int y) {
		graphics.blit(location, x, y, startX, startY, width, height);
	};

	@OnlyIn(Dist.CLIENT)
	public void render(GuiGraphics graphics, int x, int y, Color c) {
		bind();
		UIRenderHelper.drawColoredTexture(graphics, c, x, y, startX, startY, width, height);
	};
    
};
