package com.mafuyu404.gallanttirelessaltruist.event;

import com.mafuyu404.gallanttirelessaltruist.GallantTirelessAltruist;
import com.mafuyu404.gallanttirelessaltruist.network.HurtEffectPacket;
import com.mafuyu404.gallanttirelessaltruist.network.KillEffectPacket;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = GallantTirelessAltruist.MOD_ID)
public class ServerEvent {
    @SubscribeEvent
    public static void onKill(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player) {
            player.connection.send(new KillEffectPacket());
        }
    }

    @SubscribeEvent
    public static void onHurt(LivingDamageEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            player.connection.send(new HurtEffectPacket());
        }
    }
}
