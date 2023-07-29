package com.ninni.frozenup.mixin;

import com.ninni.frozenup.entity.ReindeerEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListnerImplMixin {

    @Shadow
    public ServerPlayer player;

    @Shadow private int aboveGroundVehicleTickCount;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getControllingPassenger()Lnet/minecraft/world/entity/LivingEntity;"), method = "tick")
    private void FU$tick(CallbackInfo ci) {
        Entity entity = this.player.getRootVehicle();
        Optional.of(entity).filter(ReindeerEntity.class::isInstance).map(Entity::getControllingPassenger).ifPresent(livingEntity -> {
            this.aboveGroundVehicleTickCount = 0;
        });
    }

}
