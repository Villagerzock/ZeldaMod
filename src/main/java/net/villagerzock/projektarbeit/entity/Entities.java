package net.villagerzock.projektarbeit.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.villagerzock.projektarbeit.Main;
import net.villagerzock.projektarbeit.annotations.HasAttributes;
import net.villagerzock.projektarbeit.annotations.HasAttributesHelper;
import net.villagerzock.projektarbeit.entity.custom.BokoblinEntity;


public class Entities {
    public static final EntityType<BokoblinEntity> BOKOBLIN = register("bokoblin",BokoblinEntity.class,FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BokoblinEntity::new).dimensions(EntityDimensions.fixed(1f,1f)).build());
    public static <T extends LivingEntity> EntityType<T> register(String name, Class<T> clazz, EntityType<T> type){
        Registry.register(Registries.ENTITY_TYPE,
                Identifier.of(Main.MODID,name),type);
        if (clazz.isAnnotationPresent(HasAttributes.class)){
            FabricDefaultAttributeRegistry.register(type, HasAttributesHelper.create(clazz.getAnnotation(HasAttributes.class)));
        }
        return type;
    }
}
