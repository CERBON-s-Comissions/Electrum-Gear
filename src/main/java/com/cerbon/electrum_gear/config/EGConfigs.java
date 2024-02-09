package com.cerbon.electrum_gear.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class EGConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Float> HASTE_CHANCE, SPEED_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Integer> HASTE_DURATION, HASTE_AMPLIFIER, SPEED_DURATION, SPEED_AMPLIFIER;
    public static final ForgeConfigSpec.ConfigValue<Integer> ARMOR_THUNDER_DAMAGE, SHIELD_THUNDER_DAMAGE, HITS_TO_CHARGE_SHIELD;

    static {
        BUILDER.push("Electrum Gear Configs");

        HASTE_CHANCE = BUILDER.define("Haste Chance", 0.3f);
        SPEED_CHANCE = BUILDER.define("Speed Chance", 0.3f);

        HASTE_DURATION = BUILDER.define("Haste Duration", 60);
        HASTE_AMPLIFIER = BUILDER.define("Haste Amplifier", 2);

        SPEED_DURATION = BUILDER.define("Speed Duration", 90);
        SPEED_AMPLIFIER = BUILDER.define("Speed Amplifier", 1);

        ARMOR_THUNDER_DAMAGE = BUILDER.define("Armor Thunder Damage", 7);
        SHIELD_THUNDER_DAMAGE = BUILDER.define("Shield Thunder Damage", 7);

        HITS_TO_CHARGE_SHIELD = BUILDER.define("Hits To Charge Shield", 3);

        SPEC = BUILDER.build();
    }
}
