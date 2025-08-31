package nazario.grimoire.animations;

import nazario.grimoire.GrimoireMain;
import nazario.grimoire.animations.custom.AvengingSoulmouldShockwaveAnimation;
import nazario.grimoire.animations.custom.banning.BanningAnimation;
import nazario.grimoire.animations.custom.banning.LockingAnimation;
import nazario.grimoire.entity.avenging_soulmould.AvengingSoulmouldEntity;
import nazario.liby.api.animation.v2.registry.LibyAnimationRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class ModAnimations {
    public static final LibyAnimationRegistry REGISTRY = LibyAnimationRegistry.of(GrimoireMain.MOD_ID);

    public static final Identifier GRIMOIRE_LOCKING = REGISTRY.registerAnimation("entity/locking", LockingAnimation::new, LivingEntity.class);
    public static final Identifier GRIMOIRE_BANNING = REGISTRY.registerAnimation("entity/banning", BanningAnimation::new, LivingEntity.class);

    public static final Identifier SOULMOULD_SHOCKWAVE = REGISTRY.registerAnimation("avenging_soulmould/shockwave", AvengingSoulmouldShockwaveAnimation::new, (animatable -> animatable instanceof AvengingSoulmouldEntity));

    public static void register() {

    }
}
