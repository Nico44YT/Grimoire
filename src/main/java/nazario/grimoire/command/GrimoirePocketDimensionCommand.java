package nazario.grimoire.command;


import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import nazario.grimoire.data.PocketDimensionDataManager;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

public class GrimoirePocketDimensionCommand {
    public static ArgumentBuilder<ServerCommandSource, ?> create() {
        return CommandManager.literal("pocketdimension")
                .then(CommandManager.literal("teleport")
                        .then(CommandManager.argument("player", EntityArgumentType.entity())
                                .executes(GrimoirePocketDimensionCommand::executeTeleport)));
    }

    private static int executeTeleport(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if(context.getArgument("player", EntitySelector.class).getEntity(context.getSource()) instanceof PlayerEntity dimensionOwner) {
            context.getSource().sendFeedback(() -> Text.literal("Teleported to pocket dimension of ").append(dimensionOwner.getDisplayName()), false);

            MinecraftServer server = context.getSource().getServer();
            ServerWorld world = PocketDimensionDataManager.get().getServerWorld(server, dimensionOwner.getUuid());

            context.getSource().getPlayer().teleport(world, 0, 0, 0, 0, 0);

            return 0;
        }
        return 1;
    }

}
