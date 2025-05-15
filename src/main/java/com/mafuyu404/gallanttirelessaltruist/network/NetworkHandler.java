package com.mafuyu404.gallanttirelessaltruist.network;

import com.mafuyu404.gallanttirelessaltruist.GallantTirelessAltruist;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL = "1.0";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(GallantTirelessAltruist.MODID, "sync_data"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals
    );

    // 注册数据包
    public static void register() {
        int packetId = 0;
        CHANNEL.registerMessage(packetId++, KillEffectPacket.class, KillEffectPacket::encode, KillEffectPacket::decode, KillEffectPacket::handle);
        CHANNEL.registerMessage(packetId++, HurtEffectPacket.class, HurtEffectPacket::encode, HurtEffectPacket::decode, HurtEffectPacket::handle);

    }

    // 发送数据包到客户端
    public static void sendToClient(ServerPlayer player, Object packet) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }
}