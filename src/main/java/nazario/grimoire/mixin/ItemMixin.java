package nazario.grimoire.mixin;

import nazario.grimoire.common.item.TwoHanded;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "usageTick", at = @At("HEAD"), cancellable = true)
    public void grimoire$usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks, CallbackInfo ci) {
        if(stack == user.getOffHandStack()) if(user.getMainHandStack().getItem() instanceof TwoHanded) ci.cancel();
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void grimoire$use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if(user.getMainHandStack().getItem() instanceof TwoHanded) {
            cir.setReturnValue(TypedActionResult.fail(user.getStackInHand(hand)));
        }
    }

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    public void grimoire$useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if(context.getStack() == context.getPlayer().getOffHandStack()) if(context.getPlayer().getMainHandStack().getItem() instanceof TwoHanded) cir.setReturnValue(ActionResult.FAIL);
    }

    @Inject(method = "useOnEntity", at = @At("HEAD"), cancellable = true)
    public void grimoire$useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if(stack == user.getOffHandStack()) if(user.getMainHandStack().getItem() instanceof TwoHanded) cir.setReturnValue(ActionResult.FAIL);
    }
}
