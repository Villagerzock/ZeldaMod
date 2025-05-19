package net.villagerzock.projektarbeit.annotations;

import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;

public class HasAttributesHelper {
    public static DefaultAttributeContainer.Builder create(HasAttributes hasAttributes){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE,hasAttributes.followRange())
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED,hasAttributes.movementSpeed())
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS,hasAttributes.armorToughness())
                .add(EntityAttributes.GENERIC_ARMOR,hasAttributes.armor())
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE,hasAttributes.attackDamage())
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK,hasAttributes.knockback())
                .add(EntityAttributes.GENERIC_ATTACK_SPEED,hasAttributes.attackSpeed())
                .add(EntityAttributes.GENERIC_FLYING_SPEED,hasAttributes.flyingSpeed())
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,hasAttributes.knockbackResistance())
                .add(EntityAttributes.GENERIC_LUCK,hasAttributes.luck())
                .add(EntityAttributes.GENERIC_MAX_HEALTH,hasAttributes.maxHealth())
                .add(EntityAttributes.HORSE_JUMP_STRENGTH,hasAttributes.jumpStrength())
                .add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS,hasAttributes.reinforcementChance());
    }
}
