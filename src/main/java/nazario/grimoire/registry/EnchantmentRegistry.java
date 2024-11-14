package nazario.grimoire.registry;

import nazario.grimoire.Grimoire;
import nazario.grimoire.enchantments.BruteForceEnchantment;
import nazario.grimoire.enchantments.EffectStealingEnchantment;
import nazario.grimoire.enchantments.GolemShroudEnchantment;
import nazario.grimoire.enchantments.TotemDenialEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;

public class EnchantmentRegistry {
    public static final Enchantment DEATHBOUND_ENCHANTMENT = Registry.register(Registry.ENCHANTMENT, Grimoire.id("deathbound"), new TotemDenialEnchantment());
    public static final Enchantment SHARED_BLOOD_ENCHANTMENT = Registry.register(Registry.ENCHANTMENT, Grimoire.id("shared_blood"), new EffectStealingEnchantment());
    public static final Enchantment BRUTE_FORCE_ENCHANTMENT = Registry.register(Registry.ENCHANTMENT, Grimoire.id("brute_force"), new BruteForceEnchantment());
    public static final Enchantment GOLEM_SHROUD_ENCHANTMENT = Registry.register(Registry.ENCHANTMENT, Grimoire.id("golem_shroud"), new GolemShroudEnchantment());

    public static void register() {}
}
