package nazario.grimoire.mixin;

import nazario.grimoire.ModMixinFlags;
import nazario.grimoire.item.CustomTooltipBackground;
import net.minecraft.client.item.TooltipData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow @Final @Deprecated private @Nullable Item item;

    @Inject(method = "getTooltipData", at = @At("HEAD"))
    public void grimoire$getTooltipData(CallbackInfoReturnable<Optional<TooltipData>> cir) {
        ModMixinFlags.drawCustomBackground = this.item instanceof CustomTooltipBackground;
    }

}
