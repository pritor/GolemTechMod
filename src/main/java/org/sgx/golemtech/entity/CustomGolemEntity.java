package org.sgx.golemtech.entity;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.world.World;

public class CustomGolemEntity extends GolemEntity {
    public CustomGolemEntity(EntityType<? extends GolemEntity> entityType, World world){
        super(entityType, world);
    }


}
