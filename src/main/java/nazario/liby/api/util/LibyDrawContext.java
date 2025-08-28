package nazario.liby.api.util;

import it.unimi.dsi.fastutil.ints.IntIterator;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Divider;
import net.minecraft.util.math.MathHelper;

public class LibyDrawContext extends DrawContext {
    public LibyDrawContext(MinecraftClient client, VertexConsumerProvider.Immediate vertexConsumers) {
        super(client, vertexConsumers);
    }

    public LibyDrawContext(DrawContext drawContext) {
        this(MinecraftClient.getInstance(), drawContext.getVertexConsumers());
    }

    public void drawTexture(Identifier texture, int x, int y, int z, int u, int v, int width, int height) {
        this.drawTexture(texture, x, y, z, (float)u, (float)v, width, height, 256, 256);
    }

    public void drawNineSlicedTexture(Identifier texture, int x, int y, int z, int width, int height, int outerSliceSize, int centerSliceWidth, int centerSliceHeight, int u, int v) {
        this.drawNineSlicedTexture(texture, x, y, z, width, height, outerSliceSize, outerSliceSize, outerSliceSize, outerSliceSize, centerSliceWidth, centerSliceHeight, u, v);
    }

    public void drawNineSlicedTexture(Identifier texture, int x, int y, int z, int width, int height, int outerSliceWidth, int outerSliceHeight, int centerSliceWidth, int centerSliceHeight, int u, int v) {
        this.drawNineSlicedTexture(texture, x, y, z, width, height, outerSliceWidth, outerSliceHeight, outerSliceWidth, outerSliceHeight, centerSliceWidth, centerSliceHeight, u, v);
    }

    public void drawNineSlicedTexture(Identifier texture, int x, int y, int z, int width, int height, int leftSliceWidth, int topSliceHeight, int rightSliceWidth, int bottomSliceHeight, int centerSliceWidth, int centerSliceHeight, int u, int v) {
        leftSliceWidth = Math.min(leftSliceWidth, width / 2);
        rightSliceWidth = Math.min(rightSliceWidth, width / 2);
        topSliceHeight = Math.min(topSliceHeight, height / 2);
        bottomSliceHeight = Math.min(bottomSliceHeight, height / 2);
        if (width == centerSliceWidth && height == centerSliceHeight) {
            this.drawTexture(texture, x, y, z, u, v, width, height);
        } else if (height == centerSliceHeight) {
            this.drawTexture(texture, x, y, z, u, v, leftSliceWidth, height);
            this.drawRepeatingTexture(texture, x + leftSliceWidth, y, z,width - rightSliceWidth - leftSliceWidth, height, u + leftSliceWidth, v, centerSliceWidth - rightSliceWidth - leftSliceWidth, centerSliceHeight);
            this.drawTexture(texture, x + width - rightSliceWidth, y, z,u + centerSliceWidth - rightSliceWidth, v, rightSliceWidth, height);
        } else if (width == centerSliceWidth) {
            this.drawTexture(texture, x, y, z, u, v, width, topSliceHeight);
            this.drawRepeatingTexture(texture, x, y + topSliceHeight, z, width, height - bottomSliceHeight - topSliceHeight, u, v + topSliceHeight, centerSliceWidth, centerSliceHeight - bottomSliceHeight - topSliceHeight);
            this.drawTexture(texture, x, y + height - bottomSliceHeight, z, u, v + centerSliceHeight - bottomSliceHeight, width, bottomSliceHeight);
        } else {
            this.drawTexture(texture, x, y, z, u, v, leftSliceWidth, topSliceHeight);
            this.drawRepeatingTexture(texture, x + leftSliceWidth, y, z, width - rightSliceWidth - leftSliceWidth, topSliceHeight, u + leftSliceWidth, v, centerSliceWidth - rightSliceWidth - leftSliceWidth, topSliceHeight);
            this.drawTexture(texture, x + width - rightSliceWidth, y, z,u + centerSliceWidth - rightSliceWidth, v, rightSliceWidth, topSliceHeight);
            this.drawTexture(texture, x, y + height - bottomSliceHeight, z, u, v + centerSliceHeight - bottomSliceHeight, leftSliceWidth, bottomSliceHeight);
            this.drawRepeatingTexture(texture, x + leftSliceWidth, y + height - bottomSliceHeight, z, width - rightSliceWidth - leftSliceWidth, bottomSliceHeight, u + leftSliceWidth, v + centerSliceHeight - bottomSliceHeight, centerSliceWidth - rightSliceWidth - leftSliceWidth, bottomSliceHeight);
            this.drawTexture(texture, x + width - rightSliceWidth, y + height - bottomSliceHeight, z, u + centerSliceWidth - rightSliceWidth, v + centerSliceHeight - bottomSliceHeight, rightSliceWidth, bottomSliceHeight);
            this.drawRepeatingTexture(texture, x, y + topSliceHeight, z, leftSliceWidth, height - bottomSliceHeight - topSliceHeight, u, v + topSliceHeight, leftSliceWidth, centerSliceHeight - bottomSliceHeight - topSliceHeight);
            this.drawRepeatingTexture(texture, x + leftSliceWidth, y + topSliceHeight, z, width - rightSliceWidth - leftSliceWidth, height - bottomSliceHeight - topSliceHeight, u + leftSliceWidth, v + topSliceHeight, centerSliceWidth - rightSliceWidth - leftSliceWidth, centerSliceHeight - bottomSliceHeight - topSliceHeight);
            this.drawRepeatingTexture(texture, x + width - rightSliceWidth, y + topSliceHeight, z, leftSliceWidth, height - bottomSliceHeight - topSliceHeight, u + centerSliceWidth - rightSliceWidth, v + topSliceHeight, rightSliceWidth, centerSliceHeight - bottomSliceHeight - topSliceHeight);
        }
    }

    public void drawRepeatingTexture(Identifier texture, int x, int y, int z, int width, int height, int u, int v, int textureWidth, int textureHeight) {
        int i = x;

        int j;
        for(IntIterator intIterator = createDivider(width, textureWidth); intIterator.hasNext(); i += j) {
            j = intIterator.nextInt();
            int k = (textureWidth - j) / 2;
            int l = y;

            int m;
            for(IntIterator intIterator2 = createDivider(height, textureHeight); intIterator2.hasNext(); l += m) {
                m = intIterator2.nextInt();
                int n = (textureHeight - m) / 2;
                this.drawTexture(texture, i, l, z,u + k, v + n, j, m);
            }
        }

    }

    private static IntIterator createDivider(int sideLength, int textureSideLength) {
        int i = MathHelper.ceilDiv(sideLength, textureSideLength);
        return new Divider(sideLength, i);
    }
}
