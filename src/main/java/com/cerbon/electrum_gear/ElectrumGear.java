package com.cerbon.electrum_gear;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ElectrumGear.MOD_ID)
public class ElectrumGear {
    public static final String MOD_ID = "electrum_gear";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ElectrumGear() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    }
}
