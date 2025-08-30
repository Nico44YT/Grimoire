package nazario.grimoire.item.custom.grimoire_book;

import nazario.grimoire.GrimoireMain;
import nazario.grimoire.animations.ModAnimations;
import nazario.grimoire.item.CustomTooltipBackground;
import nazario.grimoire.item.custom.SoulboundItem;
import nazario.grimoire.item.custom.grimoire_book.client.GrimoireBookItemRenderer;
import nazario.liby.api.animation.v2.LibyAnimationPlayState;
import nazario.liby.api.animation.v2.LibyEntityAnimation;
import nazario.liby.api.item.LibyItemEntityHurtListeners;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GrimoireBookItem extends Item implements LibyItemEntityHurtListeners, GeoItem, CustomTooltipBackground, SoulboundItem {
    public GrimoireBookItem(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        Text name = super.getName(stack);
        return Text.literal("").append(name).setStyle(name.getStyle().withFont((GrimoireMain.FONT)));
    }

    @Override
    public void onEntityDamage(LivingEntity victim, DamageSource source, float amount, Hand hand, CallbackInfoReturnable<Boolean> cir) {
        if(hand == Hand.MAIN_HAND) {
            Optional<LibyEntityAnimation> optional = victim.getLibyAnimation();

            if(optional.isPresent() && optional.get().liby$getId().equals(ModAnimations.GRIMOIRE_LOCKING)) {
                PlayerEntity player = (PlayerEntity)source.getAttacker();

                assert player != null;
                if(player.isSneaking()) {
                    victim.stopLibyAnimation();
                    cir.setReturnValue(false);
                    return;
                }

                victim.startLibyAnimation(ModAnimations.GRIMOIRE_BANNING);

                cir.setReturnValue(false);
            }
        }
    }

    @Override
    public void onEntityDeathDamage(LivingEntity victim, DamageSource source, float amount, Hand hand, CallbackInfoReturnable<Boolean> cir) {
        if(victim.getLibyAnimationState() == LibyAnimationPlayState.NO_ANIMATION || victim.getLibyAnimationState() == LibyAnimationPlayState.STOPPED) {
            victim.setHealth(1);

            victim.startLibyAnimation(ModAnimations.GRIMOIRE_LOCKING);

            cir.cancel();
        }
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(1, Text.translatable("item.grimoire.grimoire.desc").setStyle(Style.EMPTY.withFont((GrimoireMain.FONT))));
        tooltip.add(2, Text.translatable("item.grimoire.grimoire.soul_bound").setStyle(Style.EMPTY.withFont((GrimoireMain.FONT))));
    }

    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    private AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {

            GrimoireBookItemRenderer renderer;

            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                if(renderer == null) renderer = new GrimoireBookItemRenderer();
                return renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return this.renderProvider;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableInstanceCache;
    }
}
