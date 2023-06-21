package org.sgx.golemtech;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.sgx.golemtech.model.CustomGolemModel;
import org.sgx.golemtech.renderer.CustomGolemRenderer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;

import net.minecraft.client.render.entity.model.EntityModelLayer;
@Environment(EnvType.CLIENT)
public class GolemTechClient implements ClientModInitializer{
    public static final String ID = "golemtech";
    public static final EntityModelLayer MODEL_GOLEM_LAYER = new EntityModelLayer(new Identifier("minecraft", "custom_golem"), "custom_golem");
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(GolemTechMod.CUSTOM_GOLEM, (context)-> new CustomGolemRenderer(context,
                new CustomGolemModel(context.getPart(GolemTechClient.MODEL_GOLEM_LAYER)), 0.5f));

        EntityModelLayerRegistry.registerModelLayer(MODEL_GOLEM_LAYER, CustomGolemModel::getTexturedModelData);
        this.registerResourcePack();
    }

    private void registerResourcePack() {
        FabricLoader.getInstance().getModContainer(ID).ifPresent(container -> {
            var added = ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(ID, "golemtech"), container, ResourcePackActivationType.ALWAYS_ENABLED);
            if (!added) {
                GolemTechMod.LOGGER.error("Failed to add default 'golemtech' resource pack!");
            }
        });
    }
}
