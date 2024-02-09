package com.cerbon.electrum_gear.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ElectrumSwordItem extends SwordItem {
    boolean isActive = false;

    public ElectrumSwordItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (new Random().nextFloat() <= 0.3f)
            attacker.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 90, 1));

        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        isActive = player.hasEffect(MobEffects.DIG_SPEED) || player.hasEffect(MobEffects.MOVEMENT_SPEED);
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity livingEntity) {
        if (new Random().nextFloat() <= 0.3f) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 60, 2));
        }

        return super.mineBlock(stack, level, state, pos, livingEntity);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return isActive || super.isFoil(stack);
    }
}