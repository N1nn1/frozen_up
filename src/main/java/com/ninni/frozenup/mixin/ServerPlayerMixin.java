package com.ninni.frozenup.mixin;

import com.ninni.frozenup.client.screen.ReindeerInventoryMenu;
import com.ninni.frozenup.entity.ReindeerEntity;
import com.ninni.frozenup.network.FrozenUpNetworkHandler;
import com.ninni.frozenup.network.OpenReindeerScreenPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraftforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @Inject(at = @At("HEAD"), method = "openHorseInventory", cancellable = true)
    private void SO$openHorseInventory(AbstractHorse pHorse, Container pInventory, CallbackInfo ci) {
        if (pHorse instanceof ReindeerEntity reindeer) {
            ci.cancel();
            ServerPlayer $this = (ServerPlayer) (Object) this;
            if ($this.containerMenu != $this.inventoryMenu) {
                $this.closeContainer();
            }

            $this.nextContainerCounter();
            FrozenUpNetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> pHorse), new OpenReindeerScreenPacket($this.containerCounter, pInventory.getContainerSize(), reindeer.getId()));
            $this.containerMenu = new ReindeerInventoryMenu($this.containerCounter, $this.getInventory(), pInventory, reindeer);
            $this.initMenu($this.containerMenu);
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.player.PlayerContainerEvent.Open($this, $this.containerMenu));
        }
    }

}
