package com.mafuyu404.gallanttirelessaltruist.init;

import com.mafuyu404.gallanttirelessaltruist.GallantTirelessAltruist;
import com.mafuyu404.gallanttirelessaltruist.api.PostChainAccessor;
import com.mojang.blaze3d.shaders.AbstractUniform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceLocation;

public class EffectManager {
    public static void setUniform(String name, String key, float value) {
        Minecraft mc = Minecraft.getInstance();
        name = new ResourceLocation(GallantTirelessAltruist.MODID, name).toString();
        if (mc.gameRenderer.currentEffect() != null) {
            PostChainAccessor postChain = (PostChainAccessor) mc.gameRenderer.currentEffect();

            for (int i = 0; i < postChain.getPasses().size(); i++) {
                EffectInstance shader = postChain.getPasses().get(i).getEffect();
                if (shader.getName().equals(name)) {
                    AbstractUniform uniform = shader.safeGetUniform(key);
                    uniform.set(value);
                }
            }
        }
    }
    public static EffectInstance getEffect(String name) {
        Minecraft mc = Minecraft.getInstance();
        name = new ResourceLocation(GallantTirelessAltruist.MODID, name).toString();
        if (mc.gameRenderer.currentEffect() != null) {
            PostChainAccessor postChain = (PostChainAccessor) mc.gameRenderer.currentEffect();

            for (int i = 0; i < postChain.getPasses().size(); i++) {
                EffectInstance shader = postChain.getPasses().get(i).getEffect();
                if (shader.getName().equals(name)) {
                    return shader;
                }
            }
        }
        return null;
    }
}
