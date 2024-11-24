package nazario.grimoire.registry;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class GrimGameruleRegistry {

    public static final GameRules.Key<GameRules.BooleanRule> SAVE_TRINKETS = GameRuleRegistry.register("grim_saveTrinkets", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(true));
    public static final GameRules.Key<GameRules.BooleanRule> CREATE_GRAVE = GameRuleRegistry.register("grim_createGraves", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(true));

    public static void register() {

    }
}
