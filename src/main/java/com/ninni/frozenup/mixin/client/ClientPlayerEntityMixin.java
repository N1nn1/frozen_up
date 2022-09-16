package com.ninni.frozenup.mixin.client;

import com.ninni.frozenup.entity.ReindeerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Shadow public Input input;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/Input;tick(ZF)V", shift = At.Shift.AFTER), method = "tickMovement", locals = LocalCapture.CAPTURE_FAILSOFT)
    private void SO$TickMovement(CallbackInfo info, boolean flag) {
        ClientPlayerEntity $this = (ClientPlayerEntity)(Object)this;
        Entity ridingEntity = $this.getVehicle();
        if (ridingEntity instanceof ReindeerEntity reindeer) {
            BlockPos blockPos = reindeer.getBlockPos();
            World world = reindeer.world;
            if (!reindeer.isOnGround() && reindeer.hasCloudJumper(reindeer.getEquippedStack(EquipmentSlot.CHEST)) && reindeer.hasCloudJumpData() && !world.getBlockState(blockPos.down(64)).isOf(Blocks.AIR) && world.getBlockState(blockPos.down(3)).isOf(Blocks.AIR)) {
                if (!flag && this.input.jumping) {
                    Vec3d velocity = reindeer.getVelocity();
                    reindeer.setVelocityClient(velocity.x, velocity.y + 0.35D, velocity.z);
                }
            }
        }
    }

}
