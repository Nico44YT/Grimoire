package nazario.grimoire.misc;

import nazario.grimoire.GrimoireMain;
import nazario.liby.api.registry.auto.LibyAutoRegisterMethod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class ModDamageTypes {

    public static final RegistryKey<DamageType> OATHBREAKER_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, GrimoireMain.id("oathbreaking"));
    public static final RegistryKey<DamageType> OATHBREAKER_DIRECT_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, GrimoireMain.id("oathbreaking_direct"));

    public static DamageSource OATHBREAKER = null;
    public static DamageSource OATHBREAKER_DIRECT = null;

    public static void register() {

    }

    public static DamageSource playerOathbreaker(LivingEntity livingEntity) {
        if(OATHBREAKER == null) {
            OATHBREAKER = new DamageSource(livingEntity.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(ModDamageTypes.OATHBREAKER_TYPE));
        }
        return OATHBREAKER;
    }

    public static DamageSource playerOathbreakerDirect(LivingEntity livingEntity) {
        if(OATHBREAKER_DIRECT == null) {
            OATHBREAKER_DIRECT = new DamageSource(livingEntity.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(ModDamageTypes.OATHBREAKER_DIRECT_TYPE));
        }
        return OATHBREAKER_DIRECT;
    }
}
