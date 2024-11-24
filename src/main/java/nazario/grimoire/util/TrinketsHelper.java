package nazario.grimoire.util;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class TrinketsHelper {

    public static List<ItemStack> findAllEquippedBy(LivingEntity entity) {

        List<ItemStack> stacks = new ArrayList<>();

        List<TrinketComponent> list = TrinketsApi.getTrinketComponent(entity).stream().toList();

        list.forEach(comp -> {
            comp.getAllEquipped().forEach(pair -> {
                stacks.add(pair.getRight());
            });
        });

        return stacks;
    }
}