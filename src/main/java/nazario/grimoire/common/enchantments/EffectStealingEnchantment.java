package nazario.grimoire.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.List;

public class EffectStealingEnchantment extends Enchantment {
    public EffectStealingEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof SwordItem;
    }

    public static void logic(DamageSource source, LivingEntity victim) {
        Random rand = source.getAttacker().getWorld().getRandom();

        if(rand.nextBetween(0, 1) == 0) {
            List<StatusEffectInstance> effects = new ArrayList<>(victim.getStatusEffects());

            int index = rand.nextBetween(0, effects.size()-1);
            int duration = rand.nextBetween(20, 5*20);

            if(source.getAttacker() instanceof LivingEntity livingEntity) {
                livingEntity.addStatusEffect(new StatusEffectInstance(effects.get(index).getEffectType(), duration));
            }
        }
    }
}
