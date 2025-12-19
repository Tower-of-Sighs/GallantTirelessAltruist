package com.mafuyu404.gallanttirelessaltruist.network;

import com.mafuyu404.gallanttirelessaltruist.GallantTirelessAltruist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = GallantTirelessAltruist.MOD_ID)
public class NetworkHandler {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(GallantTirelessAltruist.MOD_ID);
        //s2c
        registrar.playToClient(HurtEffectPacket.TYPE, HurtEffectPacket.CODEC, HurtEffectPacket::execute);
        registrar.playToClient(KillEffectPacket.TYPE, KillEffectPacket.CODEC, KillEffectPacket::execute);

        //c2s

    }
}