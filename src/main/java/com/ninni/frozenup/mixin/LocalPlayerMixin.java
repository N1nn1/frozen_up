package com.ninni.frozenup.mixin;

import com.ninni.frozenup.entity.ReindeerEntity;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @Shadow public Input input;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/Input;tick(ZF)V", shift = At.Shift.AFTER), method = "aiStep", locals = LocalCapture.CAPTURE_FAILSOFT)
    private void SO$TickMovement(CallbackInfo info, boolean flag) {
        LocalPlayer $this = (LocalPlayer)(Object)this;
        Entity ridingEntity = $this.getVehicle();
        if (ridingEntity instanceof ReindeerEntity reindeer) {
            BlockPos blockPos = reindeer.blockPosition();
            Level world = reindeer.level;
            if (reindeer.hasCloudJumpData()) {
                if (!reindeer.isOnGround() && reindeer.hasCloudJumper(reindeer.getItemBySlot(EquipmentSlot.CHEST)) && reindeer.hasCloudJumpData() && !world.getBlockState(blockPos.below(64)).is(Blocks.AIR) && world.getBlockState(blockPos.below(3)).is(Blocks.AIR)) {
                    if (!flag && this.input.jumping) {
                        Vec3 velocity = reindeer.getDeltaMovement();
                        reindeer.lerpMotion(velocity.x, velocity.y + 0.35D, velocity.z);
                    }
                }
            }
        }
    }

}
