package org.sgx.golemtech.handler;



import net.minecraft.block.pattern.BlockPattern;
import org.sgx.golemtech.GolemTechMod;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

import com.google.common.collect.ImmutableMap;

import org.jetbrains.annotations.Nullable;
import org.sgx.golemtech.entity.CustomGolemEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

public class BlockHandler {
    private static final Map<Integer, CustomGolemEntity.Material> L_ARM_TO_MAT;
    private static final Map<Integer, CustomGolemEntity.Material> R_ARM_TO_MAT;
    private static final Map<Integer, CustomGolemEntity.Material> BODY_TO_MAT;
    private static final Map<Integer, CustomGolemEntity.Material> LEGS_TO_MAT;

    static {
        L_ARM_TO_MAT = ImmutableMap.of(1, CustomGolemEntity.Material.IRON,
                2, CustomGolemEntity.Material.DIAMOND,
                3, CustomGolemEntity.Material.GOLD);

    }

    static {
       R_ARM_TO_MAT = ImmutableMap.of(1, CustomGolemEntity.Material.IRON,
                2, CustomGolemEntity.Material.DIAMOND,
                3, CustomGolemEntity.Material.GOLD);

    }

    static {
        BODY_TO_MAT = ImmutableMap.of(1, CustomGolemEntity.Material.IRON,
                2, CustomGolemEntity.Material.DIAMOND,
                3, CustomGolemEntity.Material.GOLD);

    }

    static {
        LEGS_TO_MAT = ImmutableMap.of(1, CustomGolemEntity.Material.IRON,
                2, CustomGolemEntity.Material.DIAMOND,
                3, CustomGolemEntity.Material.GOLD);

    }
    public static void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack, CallbackInfo info) {
        if (state.getBlock() != Blocks.CARVED_PUMPKIN) {
            return;
        }
        String available = ".#*";
        int l_arm;
        int r_arm=0;
        int body=0;
        int legs=0;
        int width=0;
        int height=0;



        boolean found =false;
        BlockPattern.Result result = null;
        for(l_arm = 0; l_arm<3 && !found; l_arm++)
            for(r_arm = 0; r_arm<3 && !found; r_arm++)
                for(body = 0; body<3 && !found; body++)
                    for(legs = 0; legs < 3 && !found; legs++){
                        var pattern = BlockPatternBuilder.start().aisle("0^0", available.substring(r_arm,r_arm+1)
                                        + available.substring(body, body+1) + available.substring(l_arm, l_arm+1), "0"+ available.substring(legs, legs+1)+"0")
                                .where('^', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.CARVED_PUMPKIN)))
                                .where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.DIAMOND_BLOCK)))
                                .where('.', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK)))
                                .where('*', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.GOLD_BLOCK)))
                                .where('0', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.AIR)))
                                .build();
                        result = pattern.searchAround(world, pos);
                        if(result != null){
                            found = true;
                            width = pattern.getWidth();
                            height = pattern.getHeight();
                        }
                    }


        if (result != null) {
            for(var k = 0; k <width; ++k) {
                for(int l = 0; l < height; ++l) {
                    CachedBlockPosition cachedBlockPosition3 = result.translate(k, l, 0);
                    world.setBlockState(cachedBlockPosition3.getBlockPos(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
                    world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, cachedBlockPosition3.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition3.getBlockState()));
                }
            }

            BlockPos blockPos2 = result.translate(1, 2, 0).getBlockPos();
            CustomGolemEntity customGolemEntity = GolemTechMod.CUSTOM_GOLEM.create(world);
            customGolemEntity.setL_ARM_MAT(L_ARM_TO_MAT.get(l_arm));
            customGolemEntity.setR_ARM_MAT(R_ARM_TO_MAT.get(r_arm));
            customGolemEntity.setBODY_MAT(BODY_TO_MAT.get(body));
            customGolemEntity.setLEGS_MAT(LEGS_TO_MAT.get(legs));
            customGolemEntity.setMovementSpeed(legs*50f);
            customGolemEntity.refreshPositionAndAngles((double)blockPos2.getX() + 0.5D, (double)blockPos2.getY() + 0.05D, (double)blockPos2.getZ() + 0.5D, 0.0F, 0.0F);
            world.spawnEntity(customGolemEntity);

            var var6 = world.getNonSpectatingEntities(ServerPlayerEntity.class, customGolemEntity.getBoundingBox().expand(5.0D)).iterator();
//
            while(var6.hasNext()) {
                var serverPlayerEntity2 = (ServerPlayerEntity)var6.next();
                Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity2, customGolemEntity);
            }

            for(var m = 0; m < width; ++m) {
                for(int n = 0; n < height; ++n) {
                    CachedBlockPosition cachedBlockPosition4 = result.translate(m, n, 0);
                    world.updateNeighbors(cachedBlockPosition4.getBlockPos(), Blocks.AIR);
                }
            }

            info.cancel();
        }
    }
}
