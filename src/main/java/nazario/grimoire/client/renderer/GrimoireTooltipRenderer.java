package nazario.grimoire.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import nazario.grimoire.GrimoireMain;
import nazario.liby.api.assetgen.v1.client.LibyDrawContext;
import nazario.liby.api.assetgen.v1.client.renderer.LibyTooltipRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.tooltip.TooltipBackgroundRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.awt.*;

public class GrimoireTooltipRenderer implements LibyTooltipRenderer {
    private static final int BACKGROUND_COLOR = new Color(16, 0, 0, 240).getRGB();
    private static final int START_Y_BORDER_COLOR = new Color(80, 0, 0, 250).getRGB();
    private static final int END_Y_BORDER_COLOR = new Color(40, 0, 0, 250).getRGB();

    private static float time = 0f;

    private static final Identifier TOOLTIP_BACKGROUND = GrimoireMain.id("textures/misc/grimoire_tooltip.png");

    @Override
    public void render(ItemStack itemStack, LibyDrawContext context, int x, int y, int width, int height, int z) {
        int toolTipBorder = 9;
        int border = 10;
        int startX = x - border;
        int startY = y - border;
        int endX = width + border * 2;
        int endY = height + border * 2;

        TooltipBackgroundRenderer.renderHorizontalLine(context, startX, startY - 1, endX, z, BACKGROUND_COLOR);
        TooltipBackgroundRenderer.renderHorizontalLine(context, startX, startY + endY, endX, z, BACKGROUND_COLOR);
        TooltipBackgroundRenderer.renderVerticalLine(context, startX - 1, startY, endY, z, BACKGROUND_COLOR);
        TooltipBackgroundRenderer.renderVerticalLine(context, startX + endX, startY, endY, z, BACKGROUND_COLOR);
        TooltipBackgroundRenderer.renderBorder(context, startX, startY + 1, endX, endY, z, START_Y_BORDER_COLOR, END_Y_BORDER_COLOR);

        time += MinecraftClient.getInstance().getLastFrameDuration() * 0.05f;

        float wave = (float) Math.sin(time) * 25f;

        int renderColor = Math.round(wave);

        context.fillGradient(
                startX, startY,
                x + endX - border, y + endY - border, z,
                new Color(80 - renderColor, 0, 0, 250).getRGB(),
                new Color(40 + renderColor, 0, 0, 250).getRGB()
        );

        RenderSystem.enableBlend();
        context.drawNineSlicedTexture(TOOLTIP_BACKGROUND, x - toolTipBorder - 1, y - toolTipBorder - 1, z + 1, width + toolTipBorder * 2 + 2, height + toolTipBorder * 2 + 2, toolTipBorder, 130, 24, 0, 0);
        RenderSystem.disableBlend();
    }
}
