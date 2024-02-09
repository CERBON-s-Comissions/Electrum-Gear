package com.cerbon.electrum_gear.client;

import com.cerbon.electrum_gear.item.EGItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class AnimationFeatures {

    public static void init() {
        ItemProperties.register(EGItems.ELECTRUM_SHIELD.get(), new ResourceLocation("blocking"),
                (itemStack, clientWorld, livingEntity, id) -> livingEntity != null && livingEntity.isUsingItem()
                        && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);
    }
}
