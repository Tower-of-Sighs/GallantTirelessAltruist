package com.mafuyu404.gallanttirelessaltruist.event;

import com.mafuyu404.gallanttirelessaltruist.Config;
import com.mafuyu404.gallanttirelessaltruist.GallantTirelessAltruist;
import com.mafuyu404.gallanttirelessaltruist.init.EffectManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
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
    private static final ResourceLocation grayscale = new ResourceLocation(GallantTirelessAltruist.MODID, "shaders/post/grayscale.json");

    public static void killEffect() {
        if (!Config.ENABLE_KILLEFFECT.get()) return;
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;
        mc.gameRenderer.loadEffect(grayscale);
        PostChain postChain = mc.gameRenderer.currentEffect();
//        postChain.addPass(grayscale.toString(), postChain.getTempTarget("minecraft:main"), postChain.getTempTarget("swap"));
        if (ticksRemaining == 0) {
            ticksRemaining = player.tickCount;
        } else {
            ticksRemaining = player.tickCount - transitionE;
        }
    }

//    @SubscribeEvent
//    public static void onMouseClick(InputEvent.MouseButton event) {
//        if (event.getAction() == GLFW_PRESS && event.getButton() == GLFW_MOUSE_BUTTON_RIGHT) {
//            Minecraft mc = Minecraft.getInstance();
//        }
//    }

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

            EffectManager.setUniform("grayscale", "IntensityAmount", intensityAmount);

            if (progress == total) {
                ticksRemaining = 0;
                mc.gameRenderer.shutdownEffect();
            }
        }
    }
}
