package com.ninni.frozenup.util;

import com.ninni.frozenup.client.screen.ReindeerInventoryMenu;
import com.ninni.frozenup.client.screen.ReindeerInventoryScreen;
import com.ninni.frozenup.entity.ReindeerEntity;
import com.ninni.frozenup.network.OpenReindeerScreenPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;

public class ClientEventsHandler {

    public static void openReindeerInventoryScreen(OpenReindeerScreenPacket packet) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer clientPlayer = minecraft.player;
        Entity entity = null;
        if (clientPlayer != null) {
            entity = clientPlayer.level().getEntity(packet.getEntityId());
        }
        if (entity instanceof ReindeerEntity reindeer) {
            SimpleContainer inventory = new SimpleContainer(packet.getSize());
            Inventory playerInventory = clientPlayer.getInventory();
            ReindeerInventoryMenu reindeerContainer = new ReindeerInventoryMenu(packet.getContainerId(), playerInventory, inventory, reindeer);
            clientPlayer.containerMenu = reindeerContainer;
            ReindeerInventoryScreen reindeerScreen = new ReindeerInventoryScreen(reindeerContainer, playerInventory, reindeer);
            minecraft.setScreen(reindeerScreen);
        }
    }

}
