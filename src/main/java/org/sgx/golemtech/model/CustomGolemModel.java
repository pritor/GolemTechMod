package org.sgx.golemtech.model;


import net.minecraft.client.model.*;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.util.math.MathHelper;
import org.sgx.golemtech.entity.CustomGolemEntity;



public class CustomGolemModel<T extends CustomGolemEntity> extends SinglePartEntityModel<T>{
    private final ModelPart base;
    private final ModelPart body;
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart nose;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart leftArm;
    private final ModelPart rightArm;


    public CustomGolemModel(ModelPart modelPart){
        base = modelPart;

        this.body = modelPart.getChild(EntityModelPartNames.BODY);
        this.root = this.body.getChild(EntityModelPartNames.ROOT);
        this.head = this.body.getChild(EntityModelPartNames.HEAD);
        this.nose = this.head.getChild(EntityModelPartNames.NOSE);
        this.leftLeg = modelPart.getChild(EntityModelPartNames.LEFT_LEG);
        this.rightLeg = modelPart.getChild(EntityModelPartNames.RIGHT_LEG);
        this.leftArm = this.body.getChild(EntityModelPartNames.LEFT_ARM);
        this.rightArm = this.body.getChild(EntityModelPartNames.RIGHT_ARM);
    }

    @Override
    public ModelPart getPart() { return this.base; }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.pitch = headPitch * 0.017453292F;
        this.base.roll = 0.5F * MathHelper.wrap(limbAngle, 13.0F) * limbDistance;
        this.rightLeg.pitch = -1.5F * MathHelper.wrap(limbAngle, 13.0F) * limbDistance;
        this.leftLeg.pitch = 1.5F * MathHelper.wrap(limbAngle, 13.0F) * limbDistance;
        this.rightArm.pitch = -1.5F * MathHelper.wrap(limbAngle, 13.0F) * limbDistance;
        this.leftArm.pitch = 1.5F * MathHelper.wrap(limbAngle, 13.0F) * limbDistance;
        this.rightLeg.yaw = 0.0F;
        this.leftLeg.yaw = 0.0F;

    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        Dilation ex= new Dilation(0.5f);

        // Body has the limbs attached
        var body = modelPartData.addChild(EntityModelPartNames.BODY,
                ModelPartBuilder.create().uv(0, 40).cuboid(-9, -6, -5, 18, 12, 11),
                ModelTransform.pivot(0, -3, 0));

        body.addChild(EntityModelPartNames.RIGHT_ARM,
                ModelPartBuilder.create().uv(60, 21).cuboid(-4, -0.5f, -2, 4, 30, 6),
                ModelTransform.pivot(-9, -6, 0));

        body.addChild(EntityModelPartNames.LEFT_ARM,
                ModelPartBuilder.create().uv(60, 58).cuboid(0, -0.5f, -2, 4, 30, 6),
                ModelTransform.pivot(9, -6, 0));

        body.addChild(EntityModelPartNames.ROOT,
                ModelPartBuilder.create().uv(0,70).cuboid(-4.5f, 6f, -2, 9, 5, 6, ex , 1f, 1f),
                ModelTransform.pivot(0, 0, 0));



        var head = body.addChild(EntityModelPartNames.HEAD,
                ModelPartBuilder.create().uv(0, 0).cuboid(-4, -7, -6.5f, 8, 10, 8),
                ModelTransform.pivot(0, -9, 0));

        head.addChild(EntityModelPartNames.NOSE,
                ModelPartBuilder.create().uv(24, 0).cuboid(-1, 5, -1, 2, 4, 2),
                ModelTransform.pivot(0, -4, -7.5f));

        // Legs are not attached to the body, sounds like a horror movie
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG,
                ModelPartBuilder.create().uv(37, 0).cuboid(-3, 0, -2, 6, 16, 5),
                ModelTransform.pivot(-4.5f, 8, 0));
        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG,
                ModelPartBuilder.create().uv(60, 0).cuboid(-3, 0, -2, 6, 16, 5),
                ModelTransform.pivot(4.5f, 8, 0));

        return TexturedModelData.of(modelData, 128, 128);
    }
}
