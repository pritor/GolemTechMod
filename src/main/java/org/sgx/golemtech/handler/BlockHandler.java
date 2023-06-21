package org.sgx.golemtech.handler;



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

import org.jetbrains.annotations.Nullable;
import org.sgx.golemtech.entity.CustomGolemEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class BlockHandler {
    public static void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack, CallbackInfo info) {
        if (state.getBlock() != Blocks.CARVED_PUMPKIN) {
            return;
        }

        var pattern = BlockPatternBuilder.start().aisle("0^0", ".#.", "0#0")
                .where('^', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.CARVED_PUMPKIN)))
                .where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.COPPER_BLOCK)))
                .where('.', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK)))
                .where('0', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.AIR)))
                .build();

        var result = pattern.searchAround(world, pos);
        if (result != null) {
            for(var k = 0; k < pattern.getWidth(); ++k) {
                for(int l = 0; l < pattern.getHeight(); ++l) {
                    CachedBlockPosition cachedBlockPosition3 = result.translate(k, l, 0);
                    world.setBlockState(cachedBlockPosition3.getBlockPos(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
                    world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, cachedBlockPosition3.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition3.getBlockState()));
                }
            }

            BlockPos blockPos2 = result.translate(1, 2, 0).getBlockPos();
            CustomGolemEntity customGolemEntity = GolemTechMod.CUSTOM_GOLEM.create(world);
            customGolemEntity.refreshPositionAndAngles((double)blockPos2.getX() + 0.5D, (double)blockPos2.getY() + 0.05D, (double)blockPos2.getZ() + 0.5D, 0.0F, 0.0F);
            world.spawnEntity(customGolemEntity);
            var var6 = world.getNonSpectatingEntities(ServerPlayerEntity.class, customGolemEntity.getBoundingBox().expand(5.0D)).iterator();
//
            while(var6.hasNext()) {
                var serverPlayerEntity2 = (ServerPlayerEntity)var6.next();
                Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity2, customGolemEntity);
            }

            for(var m = 0; m < pattern.getWidth(); ++m) {
                for(int n = 0; n < pattern.getHeight(); ++n) {
                    CachedBlockPosition cachedBlockPosition4 = result.translate(m, n, 0);
                    world.updateNeighbors(cachedBlockPosition4.getBlockPos(), Blocks.AIR);
                }
            }

            info.cancel();
        }
    }
}
