package com.ninni.frozenup.mixin;


import com.ninni.frozenup.entity.ReindeerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;

@SuppressWarnings("deprecation")
@Mixin(PowderSnowBlock.class)
public class PowderSnowMixin extends Block {

    public PowderSnowMixin(Properties settings) {
        super(settings);
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (!(entity instanceof ReindeerEntity)) {
            if (!(entity instanceof LivingEntity) || entity.getFeetBlockState().is(this)) {
                entity.makeStuckInBlock(state, new Vec3(0.8999999761581421, 1.5, 0.8999999761581421));
                if (world.isClientSide()) {
                    RandomSource random = world.getRandom();
                    boolean bl = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
                    if (bl && random.nextBoolean()) {
                        world.addParticle(ParticleTypes.SNOWFLAKE, entity.getX(), (pos.getY() + 1), entity.getZ(), (Mth.randomBetween(random, -1.0F, 1.0F) * 0.083333336F), 0.05000000074505806, (Mth.randomBetween(random, -1.0F, 1.0F) * 0.083333336F));
                    }
                }
            }
        }
        entity.setIsInPowderSnow(true);
        if (!world.isClientSide()) {
            if (entity.isOnFire() && (world.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) || entity instanceof Player) && entity.mayInteract(world, pos)) {
                world.destroyBlock(pos, false);
            }

            entity.setSharedFlagOnFire(false);
        }

    }
}

