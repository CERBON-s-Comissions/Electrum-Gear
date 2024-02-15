package com.cerbon.electrum_gear.item;

import com.cerbon.electrum_gear.ElectrumGear;
import com.cerbon.electrum_gear.util.EGTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class EGToolTiers {
    public static final Tier ELECTRUM = TierSortingRegistry.registerTier(
            new ForgeTier(2, 400, 12, 2.5f, 14, EGTags.NEEDS_ELECTRUM_TOOL, () -> Ingredient.of(EGTags.ELECTRUM_GEAR_REPAIR)),
            new ResourceLocation(ElectrumGear.MOD_ID, "electrum"), List.of(Tiers.DIAMOND), List.of()
    );
}
