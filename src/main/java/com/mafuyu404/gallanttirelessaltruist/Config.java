package com.mafuyu404.gallanttirelessaltruist;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static final ForgeConfigSpec SPEC;
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_KILLEFFECT;
    public static final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_HURTEFFECT;
    public static final ForgeConfigSpec.ConfigValue<Boolean> DISABLE_HURTSHAKE;

    static {
        BUILDER.push("Client Setting");

        ENABLE_KILLEFFECT = BUILDER
                .comment("Just enable KillEffect.")
                .define("enableKillEffect", true);
        ENABLE_HURTEFFECT = BUILDER
                .comment("Just enable HurtEffect.")
                .define("enableHurtEffect", true);
        DISABLE_HURTSHAKE = BUILDER
                .comment("Just disable vanilla HurtShake.")
                .define("disableHurtEffect", true);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
