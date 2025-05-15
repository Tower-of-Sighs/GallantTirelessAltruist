package com.mafuyu404.gallanttirelessaltruist.network;

import com.mafuyu404.gallanttirelessaltruist.event.KillEffect;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.message.SimpleMessage;

import java.util.function.Supplier;

public class KillEffectPacket extends SimpleMessage {
    public static void encode(KillEffectPacket msg, FriendlyByteBuf buffer) {}
    public static KillEffectPacket decode(FriendlyByteBuf buffer) { return new KillEffectPacket(); }
    public static void handle(KillEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(KillEffect::killEffect);
        ctx.get().setPacketHandled(true);
    }
}
