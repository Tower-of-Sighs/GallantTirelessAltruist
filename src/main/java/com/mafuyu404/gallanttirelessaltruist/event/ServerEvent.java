package com.mafuyu404.gallanttirelessaltruist.event;

import com.mafuyu404.gallanttirelessaltruist.GallantTirelessAltruist;
import com.mafuyu404.gallanttirelessaltruist.network.HurtEffectPacket;
import com.mafuyu404.gallanttirelessaltruist.network.KillEffectPacket;
import com.mafuyu404.gallanttirelessaltruist.network.NetworkHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GallantTirelessAltruist.MODID)
public class ServerEvent {
    @SubscribeEvent
    public static void onKill(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            NetworkHandler.sendToClient((ServerPlayer) player, new KillEffectPacket());
        }
    }
    @SubscribeEvent
    public static void onHurt(LivingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            NetworkHandler.sendToClient((ServerPlayer) player, new HurtEffectPacket());
        }
    }
}
