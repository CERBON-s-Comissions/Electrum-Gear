package com.cerbon.electrum_gear.datagen;

import com.cerbon.electrum_gear.ElectrumGear;
import com.cerbon.electrum_gear.item.EGItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class EGItemTagProvider extends ItemTagsProvider {

    public EGItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, ElectrumGear.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(EGItems.ELECTRUM_HELMET.get())
                .add(EGItems.ELECTRUM_CHESTPLATE.get())
                .add(EGItems.ELECTRUM_LEGGINGS.get())
                .add(EGItems.ELECTRUM_BOOTS.get());
    }
}
