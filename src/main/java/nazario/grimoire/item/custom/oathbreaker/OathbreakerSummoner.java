package nazario.grimoire.item.custom.oathbreaker;

import nazario.grimoire.ModMixinFlags;
import nazario.grimoire.entity.oathbreaker_projectile.OathbreakerProjectileEntity;
import nazario.grimoire.item.CustomTooltipBackground;
import nazario.grimoire.misc.ModSounds;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class OathbreakerSummoner extends Item implements GeoItem, CustomTooltipBackground {

    boolean isCharging = false;

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        String[] textDesc = Text.translatable("item.grimoire.oathbreaker.lore").getString().split("\n");
        for(int i = 0;i<textDesc.length;i++) {
            tooltip.add(i + 1, Text.literal(textDesc[i]));
        }
        tooltip.add(textDesc.length + 1, Text.translatable("item.grimoire.grimoire.soul_bound"));
    }

    public OathbreakerSummoner(Settings settings) {
        super(settings);
    }

    // Allow the item to be used continuously like a bow
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    // How long the item can be used (charge time, max is 72000 like the bow)
    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    // What happens when the player right-clicks
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        this.isCharging = true;
        user.setCurrentHand(hand);
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    // Called when the player stops using the item (releases right-click)
    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity player)) return;

        this.isCharging = false;

        if (!world.isClient) {
            OathbreakerProjectileEntity projectile = OathbreakerProjectileEntity.create(world, player);
            world.spawnEntity(projectile);
        }

        player.getItemCooldownManager().set(this, 15); // Optional cooldown
        world.playSound(null, user.getBlockPos(), ModSounds.OATHBREAKER_FIRE, SoundCategory.PLAYERS, 1, 1);
    }


    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);


    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {

            OathbreakerSummonerRenderer renderer;

            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                if(renderer == null) renderer = new OathbreakerSummonerRenderer();
                return renderer;
            }
        });
    }
    @Override
    public Supplier<Object> getRenderProvider() {
        return this.renderProvider;
    }

    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    protected static final RawAnimation CHARGE_ANIM = RawAnimation.begin().thenPlay("charge");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        // Idle controller - always playing
        controllerRegistrar.add(new AnimationController<>(this, "idle_controller", 0, this::idlePredicate));

        // Charge controller - conditional overlay
        controllerRegistrar.add(new AnimationController<>(this, "charge_controller", 0, this::chargePredicate));
    }

    private PlayState idlePredicate(AnimationState<GeoAnimatable> state) {
        state.setAnimation(IDLE_ANIM);
        return PlayState.CONTINUE;
    }

    private PlayState chargePredicate(AnimationState<GeoAnimatable> state) {
        if (this.isCharging) {
            state.setAnimation(CHARGE_ANIM);
            return PlayState.CONTINUE;
        }

        state.getController().forceAnimationReset();

        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableInstanceCache;
    }
}
