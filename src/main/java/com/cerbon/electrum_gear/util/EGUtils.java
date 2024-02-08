package com.cerbon.electrum_gear.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class EGUtils {

    public static boolean hasFullSuitOfArmorOn(Player player) {
        ItemStack helmet = player.getInventory().getArmor(3);
        ItemStack chestPlate = player.getInventory().getArmor(2);
        ItemStack leggings = player.getInventory().getArmor(1);
        ItemStack boots = player.getInventory().getArmor(0);

        return !helmet.isEmpty() && !chestPlate.isEmpty() &&
               !boots.isEmpty()  && !leggings.isEmpty();
    }

    public static boolean hasCorrectArmorOn(ArmorMaterial material, Player player) {
        for (ItemStack armor : player.getInventory().armor)
            if (!(armor.getItem() instanceof ArmorItem)) return false;

        ArmorItem helmet = ((ArmorItem) player.getInventory().getArmor(3).getItem());
        ArmorItem chestPlate = ((ArmorItem) player.getInventory().getArmor(2).getItem());
        ArmorItem leggings = ((ArmorItem) player.getInventory().getArmor(1).getItem());
        ArmorItem boots = ((ArmorItem) player.getInventory().getArmor(0).getItem());

        return helmet.getMaterial() == material && chestPlate.getMaterial() == material &&
               boots.getMaterial() == material && leggings.getMaterial() == material;
    }
}
