package net.villagerzock.projektarbeit.mixin;

import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.villagerzock.projektarbeit.iMixins.IFollowMobGoal;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FollowMobGoal.class)
public abstract class FollowMobGoalMixin extends Goal implements IFollowMobGoal {
    @Shadow private @Nullable MobEntity target;

    @Override
    public void setTarget(MobEntity entity) {
        target = entity;
    }
}
