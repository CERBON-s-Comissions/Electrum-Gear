package com.cerbon.electrum_gear.client;

import com.cerbon.electrum_gear.ElectrumGear;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ElectrumShieldRenderer extends BlockEntityWithoutLevelRenderer {

    public static final ElectrumShieldRenderer INSTANCE = new ElectrumShieldRenderer();

    private final ShieldModel shieldModel;

    public static final Material SHIELD_BASE = new Material(Sheets.SHIELD_SHEET, new ResourceLocation(ElectrumGear.MOD_ID, "entity/electrum_shield_base"));
    public static final Material SHIELD_BASE_NO_PATTERN = new Material(Sheets.SHIELD_SHEET, new ResourceLocation(ElectrumGear.MOD_ID, "entity/electrum_shield_base_nopattern"));

    public ElectrumShieldRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelLoader) {
        super(blockEntityRenderDispatcher, entityModelLoader);
        shieldModel = new ShieldModel(entityModelLoader.bakeLayer(ModelLayers.SHIELD));
    }

    public ElectrumShieldRenderer() {
        this(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(@NotNull ItemStack stack, @NotNull ItemDisplayContext mode, PoseStack poseStack, @NotNull MultiBufferSource vertexConsumers, int light, int overlay) {
        boolean bl = BlockItem.getBlockEntityData(stack) != null;
        poseStack.pushPose();
        poseStack.scale(1.0f, -1.0f, -1.0f);
        Material spriteRL = bl ? SHIELD_BASE : SHIELD_BASE_NO_PATTERN;
        VertexConsumer vertexConsumer = spriteRL.sprite().wrap(ItemRenderer.getFoilBufferDirect(vertexConsumers, this.shieldModel.renderType(spriteRL.atlasLocation()), true, stack.hasFoil()));
        this.shieldModel.handle().render(poseStack, vertexConsumer, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
        if (bl) {
            List<Pair<Holder<BannerPattern>, DyeColor>> list = BannerBlockEntity.createPatterns(ShieldItem.getColor(stack), BannerBlockEntity.getItemPatterns(stack));
            BannerRenderer.renderPatterns(poseStack, vertexConsumers, light, overlay, this.shieldModel.plate(), spriteRL, false, list, stack.hasFoil());
        } else {
            this.shieldModel.plate().render(poseStack, vertexConsumer, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
        }
        poseStack.popPose();
    }
}
