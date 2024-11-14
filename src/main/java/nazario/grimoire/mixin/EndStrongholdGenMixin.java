package nazario.grimoire.mixin;

import nazario.grimoire.PersistentData;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.world.gen.structure.StrongholdStructure;
import net.minecraft.world.gen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StrongholdStructure.class)
public class EndStrongholdGenMixin {
    @Inject(method = "addPieces", at = @At("HEAD"), cancellable = true)
    private static void addPieces(StructurePiecesCollector collector, Structure.Context context, CallbackInfo ci) {
        if(PersistentData.INSTANCE.strongholdCount > 2) ci.cancel();
        PersistentData.INSTANCE.strongholdCount++;
    }
}
