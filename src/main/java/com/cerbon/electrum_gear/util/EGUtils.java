package com.cerbon.electrum_gear.util;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class EGUtils {

    public static boolean hasFullSuitOfArmorOn(Player player) {
        return player.getInventory().armor.stream().noneMatch(ItemStack::isEmpty);
    }

    public static boolean hasCorrectArmorOn(ArmorMaterial material, Player player) {
        return player.getInventory().armor.stream().allMatch(armorStack -> armorStack.getItem() instanceof ArmorItem armorItem && armorItem.getMaterial() == material);
    }

    public static boolean hasAnyCorrectArmonOn(ArmorMaterial material, Player player) {
        return player.getInventory().armor.stream().anyMatch(armorStack -> armorStack.getItem() instanceof ArmorItem armor && armor.getMaterial() == material);
    }

    public static boolean hasAnyCorrectItemsInHand(List<RegistryObject<Item>> items, Player player) {
        return items.stream().anyMatch(item -> player.getItemInHand(InteractionHand.MAIN_HAND).is(item.get()) || player.getItemInHand(InteractionHand.OFF_HAND).is(item.get()));
    }
}
