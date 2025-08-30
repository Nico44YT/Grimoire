package nazario.grimoire.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import nazario.grimoire.GrimoireMain;
import nazario.grimoire.ModFlags;
import nazario.liby.api.util.LibyDrawContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipBackgroundRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(TooltipBackgroundRenderer.class)
public abstract class TooltipBackgroundRendererMixin {

    @Unique
    private static final int BACKGROUND_COLOR = new Color(16, 0, 0, 240).getRGB();
    @Unique
    private static final int START_Y_BORDER_COLOR = new Color(80, 0, 0, 250).getRGB();
    @Unique
    private static final int END_Y_BORDER_COLOR = new Color(40, 0, 0, 250).getRGB();

    @Unique
    private static float time = 0f;

    @Unique
    private static final Identifier TOOLTIP_BACKGROUND = GrimoireMain.id("textures/misc/grimoire_tooltip.png");

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private static void grimoire$renderTooltipBackground(DrawContext context, int x, int y, int width, int height, int z, CallbackInfo ci) {
        if(ModFlags.drawCustomTooltip) {
            LibyDrawContext libyDrawContext = new LibyDrawContext(context);
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

            libyDrawContext.fillGradient(
                    startX, startY,
                    x + endX - border, y + endY - border, z,
                    new Color(80 - renderColor, 0, 0, 250).getRGB(),
                    new Color(40 + renderColor, 0, 0, 250).getRGB()
            );

            RenderSystem.enableBlend();
            libyDrawContext.drawNineSlicedTexture(TOOLTIP_BACKGROUND, x - toolTipBorder - 1, y - toolTipBorder - 1, z + 1, width + toolTipBorder * 2 + 2, height + toolTipBorder * 2 + 2, toolTipBorder, 130, 24, 0, 0);
            RenderSystem.disableBlend();

            ci.cancel();
            ModFlags.drawCustomTooltip = false;
        }
    }
}