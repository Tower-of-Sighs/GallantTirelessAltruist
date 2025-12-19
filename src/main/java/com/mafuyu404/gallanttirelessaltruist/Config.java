package com.mafuyu404.gallanttirelessaltruist;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    public static final ModConfigSpec CONFIG_SPEC;

    public static final ModConfigSpec.BooleanValue ENABLE_KILLEFFECT;
    public static final ModConfigSpec.BooleanValue ENABLE_HURTEFFECT;
    public static final ModConfigSpec.BooleanValue DISABLE_HURTSHAKE;

    public static final ModConfigSpec.DoubleValue KILLEFFECT_INTENSITY;
    public static final ModConfigSpec.DoubleValue HURTEFFECT_INTENSITY;

    static {
        ModConfigSpec.Builder CONFIG_BUILDER = new ModConfigSpec.Builder();
        CONFIG_BUILDER.push("globalSetting");

        ENABLE_KILLEFFECT = CONFIG_BUILDER
                .define("enableKillEffect", true);
        ENABLE_HURTEFFECT = CONFIG_BUILDER
                .define("enableHurtEffect", true);
        DISABLE_HURTSHAKE = CONFIG_BUILDER
                .define("disableHurtEffect", true);

        CONFIG_BUILDER.pop();

        CONFIG_BUILDER.push("effectSetting");

        KILLEFFECT_INTENSITY = CONFIG_BUILDER
                .defineInRange("killEffectIntensity", 1.0f, 0f, 1f);
        HURTEFFECT_INTENSITY = CONFIG_BUILDER
                .defineInRange("effectIntensity", 1.0f, 0f, 1f);
        CONFIG_BUILDER.pop();
        CONFIG_SPEC = CONFIG_BUILDER.build();
    }
}
