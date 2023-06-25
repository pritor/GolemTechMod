package org.sgx.golemtech.renderer;



import com.google.common.collect.ImmutableMap;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import org.sgx.golemtech.entity.CustomGolemEntity;
import org.sgx.golemtech.model.CustomGolemModel;
import net.minecraft.util.Identifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.Map;


@Environment(EnvType.CLIENT)
public class CustomGolemRenderer extends MobEntityRenderer<CustomGolemEntity, CustomGolemModel<CustomGolemEntity>> {

    private static final Map<CustomGolemEntity.Material, String> MAT_TO_TEXTURE;




    static {
        MAT_TO_TEXTURE = ImmutableMap.of(CustomGolemEntity.Material.IRON, "i",
                                               CustomGolemEntity.Material.DIAMOND,"d",
                                                CustomGolemEntity.Material.GOLD, "g");
    }

    public CustomGolemRenderer(EntityRendererFactory.Context context, CustomGolemModel customGolemModel, float f) {
        super(context, customGolemModel, f);
    }

    @Override
    public Identifier getTexture(CustomGolemEntity entity) {
//        String var = BODY_TO_TEXTURE.get(entity.getBODY_MAT());
        return new Identifier("textures/entity/custom_golem/custom_golem_"+
                MAT_TO_TEXTURE.get(entity.getL_ARM_MAT())+
                MAT_TO_TEXTURE.get(entity.getR_ARM_MAT())+
                MAT_TO_TEXTURE.get(entity.getBODY_MAT()) +
                MAT_TO_TEXTURE.get(entity.getLEGS_MAT())+".png");
    }

}