package com.cerbon.electrum_gear.item;

import com.cerbon.electrum_gear.ElectrumGear;
import com.cerbon.electrum_gear.item.custom.*;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EGItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ElectrumGear.MOD_ID);

    public static final RegistryObject<Item> ELECTRUM_SWORD = ITEMS.register("electrum_sword",
            () -> new ElectrumSwordItem(EGToolTiers.ELECTRUM, 3, -2.4F, new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> ELECTRUM_PICKAXE = ITEMS.register("electrum_pickaxe",
            () -> new ElectrumPickaxeItem(EGToolTiers.ELECTRUM, 1, -2.8F, new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> ELECTRUM_AXE = ITEMS.register("electrum_axe",
            () -> new ElectrumAxeItem(EGToolTiers.ELECTRUM, 5.5f, -3.1f, new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> ELECTRUM_SHOVEL = ITEMS.register("electrum_shovel",
            () -> new ElectrumShovelItem(EGToolTiers.ELECTRUM, 1.5f, -3.0F, new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> ELECTRUM_HOE = ITEMS.register("electrum_hoe",
            () -> new ElectrumHoeItem(EGToolTiers.ELECTRUM, -2, -1, new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> ELECTRUM_SHIELD = ITEMS.register("electrum_shield",
            () -> new ElectrumShieldItem(new Item.Properties().durability(400).fireResistant()));

    public static final RegistryObject<Item> ELECTRUM_HELMET = ITEMS.register("electrum_helmet",
            () -> new ElectrumArmorItem(EGArmorMaterials.ELECTRUM, ArmorItem.Type.HELMET, new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> ELECTRUM_CHESTPLATE = ITEMS.register("electrum_chestplate",
            () -> new ElectrumArmorItem(EGArmorMaterials.ELECTRUM, ArmorItem.Type.CHESTPLATE, new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> ELECTRUM_LEGGINGS = ITEMS.register("electrum_leggings",
            () -> new ElectrumArmorItem(EGArmorMaterials.ELECTRUM, ArmorItem.Type.LEGGINGS, new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> ELECTRUM_BOOTS = ITEMS.register("electrum_boots",
            () -> new ElectrumArmorItem(EGArmorMaterials.ELECTRUM, ArmorItem.Type.BOOTS, new Item.Properties().fireResistant()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
