package com.ninni.frozenup.network;

import com.ninni.frozenup.util.ClientEventsHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenReindeerScreenPacket {
    private final int containerId;
    private final int size;
    private final int entityId;

    public OpenReindeerScreenPacket(int containerIdIn, int sizeIdIn, int entityIdIn) {
        this.containerId = containerIdIn;
        this.size = sizeIdIn;
        this.entityId = entityIdIn;
    }

    public static OpenReindeerScreenPacket read(FriendlyByteBuf buf) {
        int containerId = buf.readUnsignedByte();
        int size = buf.readVarInt();
        int entityId = buf.readInt();
        return new OpenReindeerScreenPacket(containerId, size, entityId);
    }

    public static void write(OpenReindeerScreenPacket packet, FriendlyByteBuf buf) {
        buf.writeByte(packet.containerId);
        buf.writeVarInt(packet.size);
        buf.writeInt(packet.entityId);
    }

    public static void handle(OpenReindeerScreenPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> ClientEventsHandler.openReindeerInventoryScreen(packet));
        ctx.get().setPacketHandled(true);
    }

    public int getContainerId() {
        return this.containerId;
    }

    public int getSize() {
        return this.size;
    }

    public int getEntityId() {
        return this.entityId;
    }
}
