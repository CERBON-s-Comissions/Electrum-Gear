package com.cerbon.electrum_gear.item.custom;

import com.cerbon.electrum_gear.item.EGArmorMaterials;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ElectrumArmorItem extends ArmorItem {

    public ElectrumArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        if (stack.getItem() instanceof ArmorItem armorItem && armorItem.getMaterial() == EGArmorMaterials.ELECTRUM)
            return true;

        return super.makesPiglinsNeutral(stack, wearer);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("IsCharged") || super.isFoil(stack);
    }
}
