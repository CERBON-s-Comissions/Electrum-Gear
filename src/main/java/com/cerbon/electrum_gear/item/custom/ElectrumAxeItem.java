package com.cerbon.electrum_gear.item.custom;

import com.cerbon.electrum_gear.capability.TimerProvider;
import com.cerbon.electrum_gear.config.EGConfigs;
import com.cerbon.electrum_gear.sound.EGSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ElectrumAxeItem extends AxeItem {
    MobEffectInstance oldEffect;

    public ElectrumAxeItem(Tier tier, float attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        stack.getCapability(TimerProvider.TIMER).ifPresent(timer -> {
            if (stack.getOrCreateTag().getBoolean("IsActive") && timer.getCurrentTime() > 0)
                timer.decrease();

            else if (timer.getCurrentTime() <= 0 && stack.getOrCreateTag().getBoolean("IsActive")) {
                stack.getOrCreateTag().putBoolean("IsActive", false);

                if (entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(MobEffects.DIG_SPEED)) {
                    MobEffectInstance currentEffect = this.oldEffect;
                    if (currentEffect == null) return;

                    livingEntity.removeEffect(MobEffects.DIG_SPEED);
                    livingEntity.addEffect(currentEffect);
                }
            }
        });

        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity livingEntity) {
        if (!stack.getOrCreateTag().getBoolean("IsActive") && level.random.nextFloat() <= EGConfigs.HASTE_CHANCE.get()) {

            MobEffectInstance currentEffect = livingEntity.getEffect(MobEffects.DIG_SPEED);
            if (currentEffect != null)
                this.oldEffect = new MobEffectInstance(currentEffect.getEffect(), currentEffect.getDuration(), currentEffect.getAmplifier());

            int duration = currentEffect != null && currentEffect.getDuration() > EGConfigs.HASTE_DURATION.get() ? currentEffect.getDuration() : EGConfigs.HASTE_DURATION.get();
            int amplifier = currentEffect != null && currentEffect.getAmplifier() >= EGConfigs.HASTE_AMPLIFIER.get() ? currentEffect.getAmplifier() + 1 : EGConfigs.HASTE_AMPLIFIER.get();

            level.playSound(null, livingEntity.blockPosition(), EGSounds.ELECTRIC_SOUND1.get(), SoundSource.PLAYERS);
            livingEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, duration, amplifier));

            stack.getOrCreateTag().putBoolean("IsActive", true);
            stack.getCapability(TimerProvider.TIMER).ifPresent(timer -> timer.setTimer(EGConfigs.HASTE_DURATION.get()));
        }

        return super.mineBlock(stack, level, state, pos, livingEntity);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("IsActive") || super.isFoil(stack);
    }
}
