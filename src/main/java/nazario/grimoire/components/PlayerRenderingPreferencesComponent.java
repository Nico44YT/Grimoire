package nazario.grimoire.components;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import nazario.grimoire.GrimoireMain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class PlayerRenderingPreferencesComponent implements AutoSyncedComponent {

    public static final Identifier ID = GrimoireMain.id("player_rendering_preferences");
    public static final ComponentKey<PlayerRenderingPreferencesComponent> KEY = ComponentRegistry.getOrCreate(ID, PlayerRenderingPreferencesComponent.class);

    private final PlayerEntity owner;

    public PlayerRenderingPreferencesComponent(PlayerEntity player) {
        this.owner = player;
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound) {

    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound) {

    }
}
