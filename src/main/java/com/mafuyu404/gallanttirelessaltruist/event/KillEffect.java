package com.mafuyu404.gallanttirelessaltruist.event;

import com.mafuyu404.gallanttirelessaltruist.Config;
import com.mafuyu404.gallanttirelessaltruist.GallantTirelessAltruist;
import com.mafuyu404.gallanttirelessaltruist.init.ShaderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GallantTirelessAltruist.MODID, value = Dist.CLIENT)
public class KillEffect {
    private static final int total = 17;
    private static final int transitionS = 3;
    private static final int transitionE = 6;
    private static int ticksRemaining = 0;

    public static void killEffect() {
        if (!Config.ENABLE_KILLEFFECT.get()) return;
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;
        ShaderManager.loadShader("grayscale", "shaders/post/grayscale.json");
        if (ticksRemaining == 0) {
            ticksRemaining = player.tickCount;
        } else {
            ticksRemaining = player.tickCount - transitionE;
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        if (ticksRemaining > 0) {
            int progress = player.tickCount - ticksRemaining;

            float intensityAmount;
            if (progress < transitionS) {
                intensityAmount = 1f * progress / transitionS;
            } else if (progress > total - transitionE) {
                intensityAmount = 1f * (total - progress) / transitionE;
            }
            else {
                intensityAmount = 1.0F;
            }

            ShaderManager.getShader("grayscale").forEach(postPass -> {
                EffectInstance effect = postPass.getEffect();
                if (effect.getName().equals("gallanttirelessaltruist:grayscale")) {
                    effect.safeGetUniform("IntensityAmount").set(intensityAmount);
                }
            });

            if (progress == total) {
                ticksRemaining = 0;
                ShaderManager.clean("grayscale");
            }
        }
    }
}
