package com.ninni.frozenup.mixin;

import com.ninni.frozenup.entity.ReindeerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {

    @Shadow public ServerPlayerEntity player;

    @Shadow private int vehicleFloatingTicks;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getControllingPassenger()Lnet/minecraft/entity/LivingEntity;"), method = "tick")
    private void FU$tick(CallbackInfo ci) {
        Entity entity = this.player.getRootVehicle();
        Optional.of(entity).filter(ReindeerEntity.class::isInstance).map(Entity::getControllingPassenger).ifPresent(livingEntity -> {
            this.vehicleFloatingTicks = 0;
        });
    }

}
