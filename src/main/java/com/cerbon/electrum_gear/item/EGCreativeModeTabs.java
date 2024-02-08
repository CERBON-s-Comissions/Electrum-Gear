package com.cerbon.electrum_gear.item;

import com.cerbon.electrum_gear.ElectrumGear;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class EGCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ElectrumGear.MOD_ID);

    public static RegistryObject<CreativeModeTab> ELECTRUM_GEAR = CREATIVE_MODE_TABS.register("electrum_gear",
            ()-> CreativeModeTab.builder()
                    .icon(()-> new ItemStack(EGItems.ELECTRUM_CHESTPLATE.get()))
                    .title(Component.translatable("creativemodetab."  + ElectrumGear.MOD_ID + ".electrum_gear_tab"))
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
