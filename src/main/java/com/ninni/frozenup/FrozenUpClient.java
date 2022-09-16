package com.ninni.frozenup;

import com.google.common.reflect.Reflection;
import com.ninni.frozenup.block.FrozenUpBlocks;
import com.ninni.frozenup.client.client.init.FrozenUpEntityModelLayers;
import com.ninni.frozenup.client.renderer.ChillooEntityRenderer;
import com.ninni.frozenup.client.renderer.PenguinEntityRenderer;
import com.ninni.frozenup.client.renderer.ReindeerEntityRenderer;
import com.ninni.frozenup.client.screen.ReindeerScreen;
import com.ninni.frozenup.client.screen.ReindeerScreenHandler;
import com.ninni.frozenup.entity.FrozenUpEntities;
import com.ninni.frozenup.entity.ReindeerEntity;
import com.ninni.frozenup.network.FrozenUpPacketIdentifiers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Optional;

@Environment(EnvType.CLIENT)
public class FrozenUpClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Reflection.initialize(FrozenUpEntityModelLayers.class);
        EntityRendererRegistry.register(FrozenUpEntities.REINDEER, ReindeerEntityRenderer::new);
        EntityRendererRegistry.register(FrozenUpEntities.PENGUIN, PenguinEntityRenderer::new);
        EntityRendererRegistry.register(FrozenUpEntities.CHILLOO, ChillooEntityRenderer::new);

        BlockRenderLayerMap.INSTANCE.putBlocks(
            RenderLayer.getCutout(),

            FrozenUpBlocks.EMPTY_MUG,
            FrozenUpBlocks.MUG_OF_MILK,
            FrozenUpBlocks.MUG_OF_CHOCOLATE_MILK,
            FrozenUpBlocks.MUG_OF_TRUFFLE_HOT_CHOCOLATE
        );

        ClientPlayNetworking.registerGlobalReceiver(FrozenUpPacketIdentifiers.OPEN_REINDEER_SCREEN, (client, handler, buf, responseSender) -> {
            int id = buf.readInt();
            World level = client.world;
            Optional.ofNullable(level).ifPresent(world -> {
                Entity entity = world.getEntityById(id);
                if (entity instanceof ReindeerEntity reindeer) {
                    int slotCount = buf.readInt();
                    int syncId = buf.readInt();
                    ClientPlayerEntity clientPlayerEntity = client.player;
                    SimpleInventory simpleInventory = new SimpleInventory(slotCount);
                    assert clientPlayerEntity != null;
                    ReindeerScreenHandler reindeerScreenHandler = new ReindeerScreenHandler(syncId, clientPlayerEntity.getInventory(), simpleInventory, reindeer);
                    clientPlayerEntity.currentScreenHandler = reindeerScreenHandler;
                    client.execute(() -> client.setScreen(new ReindeerScreen(reindeerScreenHandler, clientPlayerEntity.getInventory(), reindeer)));
                }
            });
        });
    }

    public static Identifier texture(String path) {
        return new Identifier(FrozenUp.MOD_ID, "textures/" + path + ".png");
    }
}
