package nazario.grimoire.mixin.client;

import nazario.grimoire.common.entity.GlassItemFrameEntity;
import nazario.grimoire.common.entity.GlassItemFrameEntityRenderer;
import nazario.grimoire.registry.EntityTypeRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(ItemFrameEntityRenderer.class)
public abstract class ItemFrameEntityRendererMixin<T extends ItemFrameEntity> extends EntityRenderer<T> {

    protected ItemFrameEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Inject(method = "getModelId", at = @At("HEAD"), cancellable = true)
    public void grimoire$getModelId(T entity, ItemStack stack, CallbackInfoReturnable<ModelIdentifier> cir) {
        //boolean bl = entity.getType() == EntityTypeRegistry.GLASS_ITEM_FRAME;
        /*if(bl) {
            System.out.println("I'm tired...");
            cir.setReturnValue(GlassItemFrameEntityRenderer.GLASS_NORMAL_FRAME);
        }*/
    }
}
