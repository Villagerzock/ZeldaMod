package net.villagerzock.projektarbeit.annotations;

import net.minecraft.entity.attribute.DefaultAttributeContainer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HasAttributes {
    double movementSpeed() default 1.0d;
    double armor() default  0.0d;
    double armorToughness() default 0.0d;
    double attackDamage() default 1.0d;
    double knockback() default 1.0d;
    double attackSpeed() default 1.0d;
    double flyingSpeed() default 1.0d;
    double followRange() default 1.0d;
    double knockbackResistance() default 0.0d;
    double luck() default 0.0d;
    double maxHealth() default 20.0d;
    double jumpStrength() default 1.0d;
    double reinforcementChance() default 0.0d;
}
