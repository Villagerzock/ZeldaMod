package net.villagerzock.projektarbeit.entity.custom.goals;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.mob.MobEntity;
import net.villagerzock.projektarbeit.iMixins.IFollowMobGoal;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class FollowEntityGoal extends FollowMobGoal {
    private final MobEntity mob;
    @Nullable
    private MobEntity target;
    private final float maxDistance;
    private final float minDistance;
    private final Predicate<MobEntity> targetPredicate;
    public FollowEntityGoal(MobEntity mob, Class<? extends Entity> targetType, double speed, float minDistance, float maxDistance) {
        super(mob, speed, minDistance, maxDistance);
        this.mob = mob;
        this.maxDistance = maxDistance;
        this.minDistance = minDistance;
        this.targetPredicate = target -> target != null && mob.getClass() != target.getClass();
    }

    @Override
    public boolean canStart() {
        List<MobEntity> list = this.mob
                .getWorld()
                .getEntitiesByClass(MobEntity.class, this.mob.getBoundingBox().expand(this.maxDistance), this.targetPredicate);
        if (!list.isEmpty()) {
            for (MobEntity mobEntity : list) {
                if (!mobEntity.isInvisible()) {
                    if (this instanceof IFollowMobGoal goal){
                        goal.setTarget(mobEntity);
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
