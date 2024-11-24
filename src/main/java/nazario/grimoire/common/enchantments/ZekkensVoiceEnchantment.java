package nazario.grimoire.common.enchantments;

import nazario.grimoire.common.item.ZekkensVoiceItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class ZekkensVoiceEnchantment extends Enchantment {
    public ZekkensVoiceEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof ZekkensVoiceItem;
    }
}
