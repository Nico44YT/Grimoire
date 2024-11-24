package nazario.grimoire.mixin.client;

import nazario.grimoire.Grimoire;
import nazario.grimoire.registry.ItemRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Environment(EnvType.CLIENT)
@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @ModifyVariable(method = "renderItem", at = @At("HEAD"))
    public BakedModel grimoire$renderItem(BakedModel value, ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if(stack.isOf(ItemRegistry.ANGELIC_SPEAR) && renderMode != ModelTransformation.Mode.GUI) {
            return ((ItemRendererAccessor) this).grimoire$getModels().getModelManager().getModel(new ModelIdentifier(Grimoire.MOD_ID, "angelic_spear_in_hand", "inventory"));
        }
        return value;
    }
}
