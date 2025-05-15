package com.mafuyu404.gallanttirelessaltruist.network;

import com.mafuyu404.gallanttirelessaltruist.event.HurtEffect;
import com.mafuyu404.gallanttirelessaltruist.event.KillEffect;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.message.SimpleMessage;

import java.util.function.Supplier;

public class HurtEffectPacket extends SimpleMessage {
    public static void encode(HurtEffectPacket msg, FriendlyByteBuf buffer) {}
    public static HurtEffectPacket decode(FriendlyByteBuf buffer) { return new HurtEffectPacket(); }
    public static void handle(HurtEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(HurtEffect::HurtEffect);
        ctx.get().setPacketHandled(true);
    }
}
