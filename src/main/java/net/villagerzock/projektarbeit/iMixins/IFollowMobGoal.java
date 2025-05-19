package net.villagerzock.projektarbeit.iMixins;

import net.minecraft.entity.mob.MobEntity;

public interface IFollowMobGoal {
    void setTarget(MobEntity entity);
}
