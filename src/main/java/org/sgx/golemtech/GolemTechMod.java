package org.sgx.golemtech;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.sgx.golemtech.entity.CustomGolemEntity;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.sgx.golemtech.handler.BlockHandler;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;



public class GolemTechMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MODID = "golemtech";
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public static final EntityType<CustomGolemEntity> CUSTOM_GOLEM = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier("golemtech", "custom_golem"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, CustomGolemEntity::new).dimensions(EntityDimensions.fixed(1.4f, 2.7f)).build()
	);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		FabricDefaultAttributeRegistry.register(CUSTOM_GOLEM, CustomGolemEntity.createMobAttributes());

		LOGGER.info("Hello Fabric world!");
	}
}
