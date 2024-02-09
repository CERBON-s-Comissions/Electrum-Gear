package com.cerbon.electrum_gear.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

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
}
