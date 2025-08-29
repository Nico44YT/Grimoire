package nazario.grimoire.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import nazario.grimoire.GrimoireMain;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.border.WorldBorderListener;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.UnmodifiableLevelProperties;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PocketDimensionDataManager {
    private static final String FILE_HANDLE = "data/grimoire/pocket_dimensions.json";
    public static RegistryKey<DimensionOptions> POCKETDIMENSION_OPTIONS_KEY = RegistryKey.of(RegistryKeys.DIMENSION, GrimoireMain.id("pocketdimension"));

    private static PocketDimensionDataManager instance;

    private final Set<UUID> pocketDimensions = new HashSet<>();

    private PocketDimensionDataManager() {}

    public static PocketDimensionDataManager get() {
        if (instance == null) {
            instance = new PocketDimensionDataManager();
        }
        return instance;
    }

    public void saveToFile(MinecraftServer server) {
        Path path = server.session.directory.path()
                .resolve(FILE_HANDLE);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray json = new JsonArray();
        pocketDimensions.forEach(uuid -> json.add(uuid.toString()));

        try {
            // Ensure parent directories exist
            Files.createDirectories(path.getParent());

            try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                gson.toJson(json, writer);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save pocket dimensions", e);
        }
    }

    public void readFromFile(MinecraftServer server) {
        Path path = server.session.directory.path()
                .resolve(FILE_HANDLE);

        if (!Files.exists(path)) {
            return; // Nothing to load
        }

        Gson gson = new Gson();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            JsonArray array = gson.fromJson(reader, JsonArray.class);
            pocketDimensions.clear();
            array.forEach(element -> pocketDimensions.add(UUID.fromString(element.getAsString())));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read pocket dimensions", e);
        }
    }

    public boolean hasPocketDimension(UUID uuid){
        return this.getPocketDimensions().contains(uuid);
    }

    public Set<UUID> getPocketDimensions() {
        return Collections.unmodifiableSet(pocketDimensions);
    }

    public void addPocketDimension(UUID uuid) {
        pocketDimensions.add(uuid);
    }

    public void removePocketDimension(UUID uuid) {
        pocketDimensions.remove(uuid);
    }

    public ServerWorld getServerWorld(MinecraftServer server, UUID uuid) {
        RegistryKey<World> pocketDimensionKey = RegistryKey.of(RegistryKeys.WORLD, GrimoireMain.id("pocketdimension/pd_" + uuid));
        if(!hasPocketDimension(uuid) || server.getWorld(pocketDimensionKey) == null) {
            createNewWorld(server, pocketDimensionKey);
            addPocketDimension(uuid);
        }
        return server.getWorld(pocketDimensionKey);
    }

    private static ServerWorld createNewWorld(MinecraftServer server, RegistryKey<World> registryKey) {
        // Get base dimension settings from your template dimension
        Registry<DimensionOptions> registry = server.getCombinedDynamicRegistries()
                .getCombinedRegistryManager()
                .get(RegistryKeys.DIMENSION);

        DimensionOptions dimensionOptions = registry.get(POCKETDIMENSION_OPTIONS_KEY);
        if (dimensionOptions == null) {
            throw new IllegalStateException("Pocket dimension template not registered!");
        }

        // Wrap world properties so it persists separately but shares global settings
        ServerWorldProperties mainProps = server.getSaveProperties().getMainWorldProperties();
        UnmodifiableLevelProperties props = new UnmodifiableLevelProperties(server.getSaveProperties(), mainProps);

        // Progress listener
        WorldGenerationProgressListener listener = server.worldGenerationProgressListenerFactory.create(11);

        // Seed hashing
        long seed = server.getOverworld().getSeed();
        long hashedSeed = BiomeAccess.hashSeed(seed);

        // Create the world
        ServerWorld pocketWorld = new ServerWorld(
                server,
                server.workerExecutor,
                server.session,
                props,
                registryKey,
                dimensionOptions,
                listener,
                false,
                hashedSeed,
                List.of(), // no special spawners
                false,
                server.getOverworld().getRandomSequences()
        );

        // World border sync
        server.getOverworld().getWorldBorder()
                .addListener(new WorldBorderListener.WorldBorderSyncer(pocketWorld.getWorldBorder()));

        // Register it
        server.worlds.put(registryKey, pocketWorld);

        return pocketWorld;
    }

    public void applyToServer(MinecraftServer server) {
        this.pocketDimensions.forEach(uuid -> {
            RegistryKey<World> pocketDimensionKey = RegistryKey.of(RegistryKeys.WORLD, GrimoireMain.id("pocketdimension/pd_" + uuid));
            createNewWorld(server, pocketDimensionKey);
        });
    }
}
