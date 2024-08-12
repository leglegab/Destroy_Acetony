package com.petrolpark.destroy.client.gui;

import org.joml.Matrix4f;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.simibubi.create.foundation.utility.Color;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;

public enum DestroyNineSlices {
    
    INVENTORY_BACKGROUND(DestroyGuiTextures.INVENTORY_BACKGROUND, 4, 5, 4, 5),
    HOTBAR(DestroyGuiTextures.HOTBAR_BACKGROUND, 8, 14, 8, 14)
    ;
    
    public final boolean stretch;
    public final DestroyGuiTextures tex;
    public final int leftColumn;
    public final int rightColumn;
    public final int topRow;
    public final int bottomRow;

    private DestroyNineSlices(DestroyGuiTextures tex, int leftColumn, int rightColumn, int topRow, int bottomRow) {
        stretch = true;
        this.tex = tex;
        this.leftColumn = leftColumn;
        this.rightColumn = rightColumn;
        this.topRow = topRow;
        this.bottomRow = bottomRow;
    };

    public void render(GuiGraphics graphics, Rect2i rect) {
        render(graphics, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    };

    public void render(GuiGraphics graphics, int x, int y, int width, int height) {
        renderStretch(graphics, x, y, width, height);
    };

    private void renderStretch(GuiGraphics graphics, int x, int y, int width, int height) {
        tex.bind();

        // Top left corner
		drawQuad(graphics, 
            x, x + leftColumn, y, y + topRow,
            tex.startX, tex.startX + leftColumn, tex.startY, tex.startY + topRow
        );
        // Bottom left corner
        drawQuad(graphics,
            x, x + leftColumn, y + height + bottomRow - tex.height, y + height,
            tex.startX, tex.startX + leftColumn, tex.startY + bottomRow, tex.startY + tex.height
        );
        // Top right corner
        drawQuad(graphics,
            x + width + rightColumn - tex.width, x + width, y, y + topRow,
            tex.startX + rightColumn, tex.startX + tex.width, tex.startY, tex.startY + topRow
        );
        // Bottom right corner
        drawQuad(graphics,
            x + width + rightColumn - tex.width, x + width, y + height + bottomRow - tex.height, y + height,
            tex.startX + rightColumn, tex.startX + tex.width, tex.startY + bottomRow, tex.startY + tex.height
        );

        // Top
        drawQuad(graphics,
            x + leftColumn, x + width + rightColumn - tex.width, y, y + topRow,
            tex.startX + leftColumn, tex.startX + rightColumn, tex.startY, tex.startY + topRow
        );
        // Bottom
        drawQuad(graphics,
            x + leftColumn, x + width + rightColumn - tex.width, y + height + bottomRow - tex.height, y + height,
            tex.startX + leftColumn, tex.startX + rightColumn, tex.startY + bottomRow, tex.startY + tex.height
        );
        // Left
        drawQuad(graphics,
            x, x + leftColumn, y + topRow, y + height + bottomRow - tex.height,
            tex.startX, tex.startX + leftColumn, tex.startY + topRow, tex.startY + bottomRow
        );
        // Right
        drawQuad(graphics,
            x + width + rightColumn - tex.width, x + width, y + topRow, y + height + bottomRow - tex.height,
            tex.startX + rightColumn, tex.startX + tex.width, tex.startY + topRow, tex.startY + bottomRow
        );

        // Middle
        drawQuad(graphics,
            x + leftColumn, x + width + rightColumn - tex.width, y + topRow,  y + height + bottomRow - tex.height,
            tex.startX + leftColumn, tex.startX + rightColumn, tex.startY + topRow, tex.startY + bottomRow
        );
    };

    private void drawQuad(GuiGraphics graphics, int left, int right, int top, int bottom, int u1, int u2, int v1, int v2) {
        drawTexturedQuad(graphics.pose().last().pose(), Color.WHITE, left, right, top, bottom, 0, u1 / (float)tex.textureWidth, u2 / (float)tex.textureWidth, v1 / (float)tex.textureHeight, v2 / (float)tex.textureHeight);
    };

    /**
     * Copied from Create source code.
     */
    private static void drawTexturedQuad(Matrix4f m, Color c, int left, int right, int top, int bot, int z, float u1, float u2, float v1, float v2) {
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
		bufferbuilder.vertex(m, (float) left , (float) bot, (float) z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).uv(u1, v2).endVertex();
		bufferbuilder.vertex(m, (float) right, (float) bot, (float) z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).uv(u2, v2).endVertex();
		bufferbuilder.vertex(m, (float) right, (float) top, (float) z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).uv(u2, v1).endVertex();
		bufferbuilder.vertex(m, (float) left , (float) top, (float) z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).uv(u1, v1).endVertex();
		tesselator.end();
		RenderSystem.disableBlend();
	};


};
