package com.cerbon.electrum_gear.util;

import com.cerbon.electrum_gear.ElectrumGear;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class EGTags {
    public static final TagKey<Item> ELECTRUM_GEAR_REPAIR = ItemTags.create(new ResourceLocation(ElectrumGear.MOD_ID, "electrum_gear_repair"));
    public static final TagKey<Block> NEEDS_ELECTRUM_TOOL = BlockTags.create(new ResourceLocation(ElectrumGear.MOD_ID, "needs_electrum_tool"));
}
