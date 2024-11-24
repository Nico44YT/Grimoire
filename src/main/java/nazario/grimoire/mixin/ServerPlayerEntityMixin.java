package nazario.grimoire.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    //@Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    //private void grimoire$attack(Entity target, CallbackInfo ci) {
    //    ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)(Object)this;

    //    if(serverPlayerEntity.interactionManager.getGameMode() != GameMode.SPECTATOR) {
    //        ItemStack mainHandStack = serverPlayerEntity.getMainHandStack();
    //        if(serverPlayerEntity.getItemCooldownManager().isCoolingDown(mainHandStack.getItem())) ci.cancel();
    //    }
    //}
}
