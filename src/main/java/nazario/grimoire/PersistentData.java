package nazario.grimoire;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

public class PersistentData extends PersistentState {
    public int strongholdCount = 0;

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putInt("stronghold_count", strongholdCount);
        return nbt;
    }

    public static PersistentData createFromNbt(NbtCompound tag) {
        PersistentData data = new PersistentData();
        data.strongholdCount = tag.getInt("stronghold_count");
        return data;
    }

    public static PersistentData getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getOverworld().getPersistentStateManager();

        PersistentData state = persistentStateManager.getOrCreate(PersistentData::createFromNbt, PersistentData::new, Grimoire.MOD_ID);

        state.markDirty();

        return state;
    }

    public static PersistentData INSTANCE;

    public static void init(PersistentData persistentData) {
        INSTANCE = persistentData;
    }

    public static PersistentData get() {
        return INSTANCE;
    }
}
