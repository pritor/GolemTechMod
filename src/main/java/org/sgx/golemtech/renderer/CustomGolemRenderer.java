package org.sgx.golemtech.renderer;


import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import org.sgx.golemtech.entity.CustomGolemEntity;
import org.sgx.golemtech.model.CustomGolemModel;
import net.minecraft.util.Identifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class CustomGolemRenderer extends MobEntityRenderer<CustomGolemEntity, CustomGolemModel<CustomGolemEntity>> {
    public CustomGolemRenderer(EntityRendererFactory.Context context, CustomGolemModel customGolemModel, float f) {
        super(context, customGolemModel, f);
    }

    @Override
    public Identifier getTexture(CustomGolemEntity entity) {
        return new Identifier( "textures/entity/custom_golem/iron_golem.png");
    }
}
