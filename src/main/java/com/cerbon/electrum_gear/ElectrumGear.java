package com.cerbon.electrum_gear;

import com.cerbon.electrum_gear.config.EGConfigs;
import com.cerbon.electrum_gear.item.EGCreativeModeTabs;
import com.cerbon.electrum_gear.item.EGItems;
import com.cerbon.electrum_gear.misc.EGRecipes;
import com.cerbon.electrum_gear.sound.EGSounds;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ElectrumGear.MOD_ID)
public class ElectrumGear {
    public static final String MOD_ID = "electrum_gear";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ElectrumGear() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        EGCreativeModeTabs.register(modEventBus);
        EGItems.register(modEventBus);

        EGRecipes.register(modEventBus);
        EGSounds.register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EGConfigs.SPEC, MOD_ID + ".toml");
    }
}
