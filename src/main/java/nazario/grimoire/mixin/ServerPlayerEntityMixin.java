package nazario.grimoire.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
