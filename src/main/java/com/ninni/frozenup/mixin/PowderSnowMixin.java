package com.ninni.frozenup.mixin;

import com.ninni.frozenup.entity.ReindeerEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@SuppressWarnings("deprecation")
@Mixin(PowderSnowBlock.class)
public class PowderSnowMixin extends Block {
    public PowderSnowMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof ReindeerEntity)) {
            if (!(entity instanceof LivingEntity) || entity.getBlockStateAtPos().isOf(this)) {
                entity.slowMovement(state, new Vec3d(0.8999999761581421, 1.5, 0.8999999761581421));
                if (world.isClient) {
                    Random random = world.getRandom();
                    boolean bl = entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ();
                    if (bl && random.nextBoolean()) {
                        world.addParticle(ParticleTypes.SNOWFLAKE, entity.getX(), (pos.getY() + 1), entity.getZ(), (MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.083333336F), 0.05000000074505806, (MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.083333336F));
                    }
                }
            }
        }
        entity.setInPowderSnow(true);
        if (!world.isClient) {
            if (entity.isOnFire() && (world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) || entity instanceof PlayerEntity) && entity.canModifyAt(world, pos)) {
                world.breakBlock(pos, false);
            }

            entity.setOnFire(false);
        }

    }
}

