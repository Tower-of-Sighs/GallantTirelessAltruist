package com.mafuyu404.gallanttirelessaltruist.event;

import com.mafuyu404.gallanttirelessaltruist.GallantTirelessAltruist;
import com.mafuyu404.gallanttirelessaltruist.api.PostChainAccessor;
import com.mojang.blaze3d.shaders.AbstractUniform;
import com.mojang.blaze3d.shaders.Uniform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

@Mod.EventBusSubscriber(modid = GallantTirelessAltruist.MODID, value = Dist.CLIENT)
public class ClientEvent {
    private static final int total = 18;
    private static final int transitionS = 3;
    private static final int transitionE = 6;
    private static int ticksRemaining = 0;
    private static final ResourceLocation grayscale = new ResourceLocation(GallantTirelessAltruist.MODID, "shaders/post/grayscale.json");

    @SubscribeEvent
    public static void onMouseClick(InputEvent.MouseButton event) {
        if (event.getAction() == GLFW_PRESS && event.getButton() == GLFW_MOUSE_BUTTON_RIGHT) {
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            if (player == null) return;
            if (ticksRemaining == 0) {
                mc.gameRenderer.loadEffect(grayscale);
                ticksRemaining = player.tickCount;
            } else {
                ticksRemaining = player.tickCount - transitionE;
            }
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;
        if (ticksRemaining > 0) {
            // 计算进度（剩余时间到已用时间的转换）
            int progress = player.tickCount - ticksRemaining;

            // 计算过渡参数（0.0-1.0）
            float inverseAmount;
            if (progress < transitionS) {
                inverseAmount = 1f * progress / transitionS;
            } else if (progress > total - transitionE) {
                inverseAmount = 1f * (total - progress) / transitionE;
            }
            else {
                inverseAmount = 1.0F;
            }

            if (mc.gameRenderer.currentEffect() != null) {
                PostChainAccessor postChain = (PostChainAccessor) mc.gameRenderer.currentEffect();

                for (int i = 0; i < postChain.getPasses().size(); i++) {
                    EffectInstance shader = postChain.getPasses().get(i).getEffect();

                    AbstractUniform uniform = shader.safeGetUniform("InverseAmount");
                    uniform.set(inverseAmount);
                }
            }

            if (progress == total) {
                mc.gameRenderer.shutdownEffect();
                ticksRemaining = 0;
            }
        }
    }
}
