package net.villagerzock.projektarbeit.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;

public class ChuchuEntity extends HostileEntity {
    protected ChuchuEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }
}
