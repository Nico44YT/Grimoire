package nazario.grimoire.entity.goal;

import nazario.grimoire.animations.ModAnimations;
import nazario.grimoire.entity.avenging_soulmould.AvengingSoulmouldEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;


public class SoulmouldShockwaveGoal extends Goal {
    private final AvengingSoulmouldEntity mould;

    public SoulmouldShockwaveGoal(AvengingSoulmouldEntity entity) {
        this.mould = entity;
        this.setControls(EnumSet.of(Control.JUMP, Control.TARGET, Control.MOVE, Control.LOOK));
    }

    @Override
    public void start() {
        mould.startLibyAnimation(ModAnimations.SOULMOULD_SHOCKWAVE);

        mould.shockwaveCooldown = mould.SHOCKWAVE_MAX_COOLDOWN;
    }

    @Override
    public boolean canStart() {
        LivingEntity target = this.mould.getTarget();
        return target != null && target.isAlive() && !this.mould.isDormant() && mould.shockwaveCooldown < 0;
    }
}
