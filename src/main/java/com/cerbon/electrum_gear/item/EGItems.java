package com.cerbon.electrum_gear.item;

import com.cerbon.electrum_gear.ElectrumGear;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EGItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ElectrumGear.MOD_ID);

    public static final RegistryObject<Item> ELECTRUM_HELMET = ITEMS.register("electrum_helmet",
            () -> new ArmorItem(EGArmorMaterials.ELECTRUM, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> ELECTRUM_CHESTPLATE = ITEMS.register("electrum_chestplate",
            () -> new ArmorItem(EGArmorMaterials.ELECTRUM, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> ELECTRUM_LEGGINGS = ITEMS.register("electrum_leggings",
            () -> new ArmorItem(EGArmorMaterials.ELECTRUM, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> ELECTRUM_BOOTS = ITEMS.register("electrum_boots",
            () -> new ArmorItem(EGArmorMaterials.ELECTRUM, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
