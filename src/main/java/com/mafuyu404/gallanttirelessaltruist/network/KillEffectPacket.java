package com.mafuyu404.gallanttirelessaltruist.network;

import com.mafuyu404.gallanttirelessaltruist.GallantTirelessAltruist;
import com.mafuyu404.gallanttirelessaltruist.event.KillEffect;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record KillEffectPacket() implements CustomPacketPayload {
    public static final Type<KillEffectPacket> TYPE = new Type<>(GallantTirelessAltruist.id("kill_effect"));
    public static final StreamCodec<FriendlyByteBuf, KillEffectPacket> CODEC = StreamCodec.ofMember(KillEffectPacket::write, KillEffectPacket::new);

    public KillEffectPacket(FriendlyByteBuf friendlyByteBuf) {
        this();
    }

    private void write(FriendlyByteBuf buf) {
    }

    public static void execute(KillEffectPacket payload, IPayloadContext context) {
        KillEffect.killEffect();
    }

    @Override
    public @NotNull CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
