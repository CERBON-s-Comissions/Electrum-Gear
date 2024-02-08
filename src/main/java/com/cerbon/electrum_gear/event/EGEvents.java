package com.cerbon.electrum_gear.event;

import com.cerbon.electrum_gear.ElectrumGear;
import com.cerbon.electrum_gear.datagen.EGBlockTagProvider;
import com.cerbon.electrum_gear.datagen.EGItemModelProvider;
import com.cerbon.electrum_gear.datagen.EGItemTagProvider;
import com.cerbon.electrum_gear.item.EGCreativeModeTabs;
import com.cerbon.electrum_gear.item.EGItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ElectrumGear.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EGEvents {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new EGItemModelProvider(packOutput, existingFileHelper));

        EGBlockTagProvider blockTagProvider = generator.addProvider(event.includeServer(), new EGBlockTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new EGItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter(), existingFileHelper));
    }

    @SubscribeEvent
    protected static void addCreativeTab(@NotNull BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == EGCreativeModeTabs.ELECTRUM_GEAR.get())
            EGItems.ITEMS.getEntries().forEach(event::accept);
    }
}
