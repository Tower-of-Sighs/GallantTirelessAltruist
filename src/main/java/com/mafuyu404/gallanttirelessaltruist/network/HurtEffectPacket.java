package com.mafuyu404.gallanttirelessaltruist.network;

import com.mafuyu404.gallanttirelessaltruist.GallantTirelessAltruist;
import com.mafuyu404.gallanttirelessaltruist.event.HurtEffect;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record HurtEffectPacket() implements CustomPacketPayload {
    public static final Type<HurtEffectPacket> TYPE = new Type<>(GallantTirelessAltruist.id("hurt_effect"));
    public static final StreamCodec<FriendlyByteBuf, HurtEffectPacket> CODEC = StreamCodec.ofMember(HurtEffectPacket::write, HurtEffectPacket::new);

    public HurtEffectPacket(FriendlyByteBuf friendlyByteBuf) {
        this();
    }

    private void write(FriendlyByteBuf buf) {
    }

    public static void execute(HurtEffectPacket payload, IPayloadContext context) {
        HurtEffect.HurtEffect();
    }

    @Override
    public @NotNull CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
