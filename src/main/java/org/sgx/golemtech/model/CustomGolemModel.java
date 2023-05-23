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
    private final ModelPart head;
    private final ModelPart nose;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart leftArm;
    private final ModelPart rightArm;

    public CustomGolemModel(ModelPart modelPart){
        base = modelPart;

        this.body = modelPart.getChild(EntityModelPartNames.BODY);
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
        this.rightLeg.pitch = -1.5F * MathHelper.wrap(limbAngle, 13.0F) * limbDistance;
        this.leftLeg.pitch = 1.5F * MathHelper.wrap(limbAngle, 13.0F) * limbDistance;
        this.rightLeg.yaw = 0.0F;
        this.leftLeg.yaw = 0.0F;

    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        // Body has the limbs attached
        var body = modelPartData.addChild(EntityModelPartNames.BODY,
                ModelPartBuilder.create().uv(0, 15).cuboid(-5, -6, -3, 10, 8, 6),
                ModelTransform.pivot(0, 18, 0));

        body.addChild(EntityModelPartNames.LEFT_ARM,
                ModelPartBuilder.create().uv(38, 25).cuboid(-2, 0, -2, 3, 9, 4),
                ModelTransform.pivot(-6, -6, 0));
        body.addChild(EntityModelPartNames.RIGHT_ARM,
                ModelPartBuilder.create().mirrored().uv(38, 25).cuboid(-1, 0, -2, 3, 9, 4),
                ModelTransform.pivot(6, -6, 0));

        var head = body.addChild(EntityModelPartNames.HEAD,
                ModelPartBuilder.create().uv(0, 0).cuboid(-5, -3, -4, 10, 6, 8),
                ModelTransform.pivot(0, -9, 0));

        head.addChild(EntityModelPartNames.NOSE,
                ModelPartBuilder.create().uv(44, 5).cuboid(-1, 0, -1, 2, 4, 2),
                ModelTransform.pivot(0, 0, -5));

        // Legs are not attached to the body, sounds like a horror movie
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG,
                ModelPartBuilder.create().mirrored().uv(0, 30).cuboid(-2, 0, -2, 4, 4, 4),
                ModelTransform.pivot(-2, 20, 0));
        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG,
                ModelPartBuilder.create().uv(0, 30).cuboid(-2, 0, -2, 4, 4, 4),
                ModelTransform.pivot(2, 20, 0));

        return TexturedModelData.of(modelData, 64, 64);
    }
}
