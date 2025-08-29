package nazario.grimoire.components;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import nazario.grimoire.GrimoireMain;
import nazario.grimoire.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class PlayerRenderingDataComponent implements AutoSyncedComponent, ServerTickingComponent {

    public static final Identifier ID = GrimoireMain.id("player_rendering_data");
    public static final ComponentKey<PlayerRenderingDataComponent> KEY = ComponentRegistry.getOrCreate(ID, PlayerRenderingDataComponent.class);

    private final PlayerEntity owner;

    private boolean renderOathbreakerFeature = false;

    public PlayerRenderingDataComponent(PlayerEntity player) {
        this.owner = player;
    }

    public boolean shouldOathbreakerFeatureRender() {
        return this.renderOathbreakerFeature;
    }

    public PlayerRenderingDataComponent setOathbreakerFeatureRendering(boolean bool) {
        this.renderOathbreakerFeature = bool;
        return this;
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound) {
        NbtCompound featureCompound = nbtCompound.getCompound("features");
        this.renderOathbreakerFeature = featureCompound.getBoolean("render_oathbreaker");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound) {
        NbtCompound featureCompound = new NbtCompound();
        featureCompound.putBoolean("render_oathbreaker", this.renderOathbreakerFeature);

        nbtCompound.put("features", featureCompound);
    }

    @Override
    public void serverTick() {
        this.setOathbreakerFeatureRendering(owner.getInventory().contains(ModItems.OATHBREAKER.getDefaultStack())
                && !owner.getStackInHand(Hand.MAIN_HAND).isOf(ModItems.OATHBREAKER)
                && !owner.getStackInHand(Hand.OFF_HAND).isOf(ModItems.OATHBREAKER));


        KEY.sync(this.owner);
    }
}
