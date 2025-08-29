package nazario.grimoire.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import nazario.grimoire.GrimoireMain;
import nazario.grimoire.ModMixinFlags;
import nazario.grimoire.item.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin {
    @Shadow private static ItemGroup selectedTab;

    @Inject(method = "renderTabTooltipIfHovered", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTooltip(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;II)V"))
    public void grimoire$renderTabTooltip(DrawContext context, ItemGroup group, int mouseX, int mouseY, CallbackInfoReturnable<Boolean> cir) {
        if(group.equals(Registries.ITEM_GROUP.get(ModItems.GROUP))) {
            ModMixinFlags.drawCustomTooltip = true;
        }
    }

    float opacity = 0;

    @Redirect(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"))
    public void grimoire$redirectBackgroundRenderCall(DrawContext instance, Identifier texture, int x, int y, int u, int v, int width, int height) {
        boolean isGrimoireTab = selectedTab.equals(Registries.ITEM_GROUP.get(ModItems.GROUP));

        float opacityTarget = isGrimoireTab ? 0.5f : 0f;
        opacity = MathHelper.lerp(0.05f, opacity, opacityTarget);

        if (opacity > 0.001f) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            instance.setShaderColor(1f, 1f, 1f, opacity);

            int w = MinecraftClient.getInstance().getWindow().getScaledWidth();
            int h = MinecraftClient.getInstance().getWindow().getScaledHeight();
            instance.drawTexture(Identifier.of("grimoire", "textures/misc/grimoire_anxiety_overlay.png"),
                    0, 0, 0, 0, w, h, w, h);

            instance.setShaderColor(1f, 1f, 1f, 1f); // reset
            RenderSystem.disableBlend();
        }

        if(isGrimoireTab) {
            instance.drawTexture(GrimoireMain.id(texture.getPath()), x, y, u, v, width, height);
            return;
        }

        instance.drawTexture(texture, x, y, u, v, width, height);
    }

    @Unique
    private boolean shouldRender = false;

    @Inject(method = "renderTabIcon", at = @At("HEAD"))
    public void grimoire$renderTabIcon(DrawContext context, ItemGroup group, CallbackInfo ci) {
        shouldRender = group.equals(Registries.ITEM_GROUP.get(ModItems.GROUP));
    }

    @Redirect(method = "renderTabIcon", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"))
    public void grimoire$redirectRenderTabIconCall(DrawContext instance, Identifier texture, int x, int y, int u, int v, int width, int height) {
        if(shouldRender) {
            instance.drawTexture(GrimoireMain.id(texture.getPath()), x, y, u, v, width, height);
            return;
        }

        instance.drawTexture(texture, x, y, u, v, width, height);
    }
}
