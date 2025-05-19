package net.villagerzock.projektarbeit.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.villagerzock.projektarbeit.annotations.HasAttributes;
import net.villagerzock.projektarbeit.entity.custom.goals.FollowEntityGoal;

@HasAttributes
public class BokoblinEntity extends HostileEntity {
    public BokoblinEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new FollowEntityGoal(this,PlayerEntity.class,getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED),1.0f, (float) getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)));
        this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, (float) getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)));
    }
}
