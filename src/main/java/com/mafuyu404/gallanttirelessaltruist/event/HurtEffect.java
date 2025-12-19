package com.mafuyu404.gallanttirelessaltruist.event;

import com.mafuyu404.gallanttirelessaltruist.Config;
import com.mafuyu404.gallanttirelessaltruist.GallantTirelessAltruist;
import com.mafuyu404.gallanttirelessaltruist.init.ShaderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = GallantTirelessAltruist.MOD_ID, value = Dist.CLIENT)
public class HurtEffect {
    private static final int total = 7;
    private static final int transitionS = 2;
    private static final int transitionE = 4;
    private static int ticksRemaining = 0;

    public static void HurtEffect() {
        if (!Config.ENABLE_HURTEFFECT.get()) return;
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;
        ShaderManager.loadEffect("hurt", "shaders/post/hurt.json");
        if (ticksRemaining == 0) {
            ticksRemaining = player.tickCount;
        } else {
            ticksRemaining = player.tickCount - transitionE;
        }
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        if (player.tickCount < 20) ticksRemaining = 0;

        if (ticksRemaining > 0) {
            int progress = player.tickCount - ticksRemaining;

            float intensityAmount;
            if (progress < transitionS) {
                intensityAmount = 1f * progress / transitionS;
            } else if (progress > total - transitionE) {
                intensityAmount = 1f * (total - progress) / transitionE;
            } else {
                intensityAmount = 1.0F;
            }

            intensityAmount *= Config.HURTEFFECT_INTENSITY.get();

            float finalIntensityAmount = intensityAmount;
            ShaderManager.getEffect("hurt").forEach(postPass -> {
                EffectInstance effect = postPass.getEffect();
                if (effect.getName().equals("gallanttirelessaltruist:hurt")) {
                    effect.safeGetUniform("IntensityAmount").set(finalIntensityAmount);
                }
            });

            if (progress == total) {
                ticksRemaining = 0;
                ShaderManager.clean("hurt");
            }
        }
    }
}
