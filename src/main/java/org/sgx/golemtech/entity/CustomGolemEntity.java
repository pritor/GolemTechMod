package org.sgx.golemtech.entity;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.world.World;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;

public class CustomGolemEntity extends IronGolemEntity {

    protected static final TrackedData<Integer> L_ARM_MAT_ID;
    protected static final TrackedData<Integer> R_ARM_MAT_ID;
    protected static final TrackedData<Integer> BODY_MAT_ID;
    protected static final TrackedData<Integer> LEGS_MAT_ID;


    static{
        L_ARM_MAT_ID = DataTracker.registerData(CustomGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
        R_ARM_MAT_ID = DataTracker.registerData(CustomGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
        BODY_MAT_ID = DataTracker.registerData(CustomGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
        LEGS_MAT_ID = DataTracker.registerData(CustomGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);

    }
    public CustomGolemEntity(EntityType<? extends IronGolemEntity> entityType, World world){
        super(entityType, world);
    }
    @Override
    protected void initDataTracker() {
        super.initDataTracker();

        this.dataTracker.startTracking(L_ARM_MAT_ID, 0);
        this.dataTracker.startTracking(R_ARM_MAT_ID, 0);
        this.dataTracker.startTracking(BODY_MAT_ID, 0);
        this.dataTracker.startTracking(LEGS_MAT_ID, 0);

    }
    public static DefaultAttributeContainer.Builder createMobAttributes(){
        DefaultAttributeContainer.Builder temp = IronGolemEntity.createIronGolemAttributes();
        return temp;
    }

    public Integer getL_ARM_MAT_ID(){
        return this.dataTracker.get(L_ARM_MAT_ID);
    }
    public Material getL_ARM_MAT(){
        var l_arm_id = this.getL_ARM_MAT_ID();
        if(l_arm_id==0)
            return Material.IRON;
        else if (l_arm_id==1)
            return Material.DIAMOND;
        return Material.GOLD;
    }

    public void setL_ARM_MAT(Material new_mat){
        if (new_mat==Material.IRON)
            this.dataTracker.set(L_ARM_MAT_ID, 0);
        if (new_mat==Material.DIAMOND)
            this.dataTracker.set(L_ARM_MAT_ID, 1);
        if (new_mat==Material.GOLD)
            this.dataTracker.set(L_ARM_MAT_ID,2);
    }

    public Integer getR_ARM_MAT_ID(){
        return this.dataTracker.get(R_ARM_MAT_ID);
    }
    public Material getR_ARM_MAT(){
        var r_arm_id = this.getR_ARM_MAT_ID();
        if(r_arm_id==0)
            return Material.IRON;
        else if (r_arm_id==1)
            return Material.DIAMOND;
        return Material.GOLD;
    }

    public void setR_ARM_MAT(Material new_mat){
        if (new_mat==Material.IRON)
            this.dataTracker.set(R_ARM_MAT_ID, 0);
        if (new_mat==Material.DIAMOND)
            this.dataTracker.set(R_ARM_MAT_ID, 1);
        if (new_mat==Material.GOLD)
            this.dataTracker.set(R_ARM_MAT_ID,2);
    }

    public Integer getBODY_MAT_ID(){
        return this.dataTracker.get(BODY_MAT_ID);
    }
    public Material getBODY_MAT(){
        var body_id = this.getBODY_MAT_ID();
        if(body_id==0)
            return Material.IRON;
        else if (body_id==1)
            return Material.DIAMOND;
        return Material.GOLD;
    }

    public void setBODY_MAT(Material new_mat){
        if (new_mat==Material.IRON)
            this.dataTracker.set(BODY_MAT_ID, 0);
        if (new_mat==Material.DIAMOND)
            this.dataTracker.set(BODY_MAT_ID, 1);
        if (new_mat==Material.GOLD)
            this.dataTracker.set(BODY_MAT_ID,2);
    }

    public Integer getLEGS_MAT_ID(){
        return this.dataTracker.get(LEGS_MAT_ID);
    }
    public Material getLEGS_MAT(){
        var legs_id = this.getLEGS_MAT_ID();
        if(legs_id==0)
            return Material.IRON;
        else if (legs_id==1)
            return Material.DIAMOND;
        return Material.GOLD;
    }

    public void setLEGS_MAT(Material new_mat){
        if (new_mat==Material.IRON)
            this.dataTracker.set(LEGS_MAT_ID, 0);
        if (new_mat==Material.DIAMOND)
            this.dataTracker.set(LEGS_MAT_ID, 1);
        if (new_mat==Material.GOLD)
            this.dataTracker.set(LEGS_MAT_ID,2);
    }
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("L_arm_mat", this.getL_ARM_MAT_ID());
        nbt.putInt("R_arm_mat", this.getR_ARM_MAT_ID());
        nbt.putInt("Body_mat", this.getBODY_MAT_ID());
        nbt.putInt("Legs_mat", this.getLEGS_MAT_ID());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(L_ARM_MAT_ID, nbt.getInt("L_arm_mat"));
        this.dataTracker.set(R_ARM_MAT_ID, nbt.getInt("R_arm_mat"));
        this.dataTracker.set(BODY_MAT_ID, nbt.getInt("Body_mat"));
        this.dataTracker.set(LEGS_MAT_ID, nbt.getInt("Legs_mat"));
    }
    public enum Material{
        IRON,
        DIAMOND,
        GOLD;

    }

}
