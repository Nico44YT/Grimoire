package nazario.grimoire.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.List;

public class GrimoireMainCommand {

    protected static List<ArgumentBuilder> subCommands = new ArrayList<>();

    public GrimoireMainCommand(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("grimoire");

        GrimoireMainCommand.registerSubCommand(GrimoirePocketDimensionCommand.create());

        subCommands.forEach(builder::then);

        dispatcher.register(builder);
    }
    public static void registerSubCommand(ArgumentBuilder<?, ?> argumentBuilder) {
        subCommands.add(argumentBuilder);
    }

}
