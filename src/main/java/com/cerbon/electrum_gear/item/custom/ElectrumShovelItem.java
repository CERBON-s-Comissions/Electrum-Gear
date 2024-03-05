package com.cerbon.electrum_gear.item.custom;

import com.cerbon.electrum_gear.capability.TimerProvider;
import com.cerbon.electrum_gear.config.EGConfigs;
import com.cerbon.electrum_gear.sound.EGSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ElectrumShovelItem extends ShovelItem {
    private final String IS_ACTIVE_TAG = "IsActive";
    private final String OLD_AMPLIFIER_TAG = "OldAmplifier";

    private final MobEffect digSpeed = MobEffects.DIG_SPEED;

    public ElectrumShovelItem(Tier tier, float attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        stack.getCapability(TimerProvider.TIMER).ifPresent(timer -> {
            CompoundTag tag = stack.getOrCreateTag();

            if (tag.getBoolean(IS_ACTIVE_TAG) && timer.getCurrentTime() > 0)
                timer.decrease(1);

            else if (timer.getCurrentTime() <= 0 && tag.getBoolean(IS_ACTIVE_TAG)) {
                tag.putBoolean(IS_ACTIVE_TAG, false);

                if (entity instanceof Player player) {
                    MobEffectInstance currentEffect = player.getEffect(digSpeed);
                    if (currentEffect == null) return;

                    player.removeEffect(digSpeed);
                    player.addEffect(new MobEffectInstance(digSpeed, currentEffect.getDuration(), tag.getInt(OLD_AMPLIFIER_TAG)));
                }
            }
        });

        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        if (context.getPlayer() == null) return super.useOn(context);

        CompoundTag tag = context.getItemInHand().getOrCreateTag();
        if (!tag.getBoolean(IS_ACTIVE_TAG) && context.getLevel().random.nextFloat() <= EGConfigs.HASTE_CHANCE.get()) {

            MobEffectInstance oldEffect = context.getPlayer().getEffect(digSpeed);
            if (oldEffect != null)
                tag.putInt(OLD_AMPLIFIER_TAG, oldEffect.getAmplifier());

            int duration = oldEffect != null && oldEffect.getDuration() > EGConfigs.HASTE_DURATION.get() ? oldEffect.getDuration() : EGConfigs.HASTE_DURATION.get();
            int amplifier = oldEffect != null && oldEffect.getAmplifier() >= EGConfigs.HASTE_AMPLIFIER.get() ? oldEffect.getAmplifier() + 1 : EGConfigs.HASTE_AMPLIFIER.get();

            context.getPlayer().addEffect(new MobEffectInstance(digSpeed, duration, amplifier));
            context.getLevel().playSound(null, context.getPlayer().blockPosition(), EGSounds.ELECTRIC_SOUND1.get(), SoundSource.PLAYERS);

            context.getItemInHand().getOrCreateTag().putBoolean(IS_ACTIVE_TAG, true);
            context.getItemInHand().getCapability(TimerProvider.TIMER).ifPresent(timer -> timer.setTimer(EGConfigs.HASTE_DURATION.get()));
        }

        return super.useOn(context);
    }

    @Override
    public boolean canBeHurtBy(DamageSource damageSource) {
        return !damageSource.is(DamageTypeTags.IS_LIGHTNING) && super.canBeHurtBy(damageSource);
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity livingEntity) {
        if (!(livingEntity instanceof Player player)) return super.mineBlock(stack, level, state, pos, livingEntity);

        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.getBoolean(IS_ACTIVE_TAG) && level.random.nextFloat() <= EGConfigs.HASTE_CHANCE.get()) {

            MobEffectInstance oldEffect = player.getEffect(digSpeed);
            if (oldEffect != null)
                tag.putInt(OLD_AMPLIFIER_TAG, oldEffect.getAmplifier());

            int duration = oldEffect != null && oldEffect.getDuration() > EGConfigs.HASTE_DURATION.get() ? oldEffect.getDuration() : EGConfigs.HASTE_DURATION.get();
            int amplifier = oldEffect != null && oldEffect.getAmplifier() >= EGConfigs.HASTE_AMPLIFIER.get() ? oldEffect.getAmplifier() + 1 : EGConfigs.HASTE_AMPLIFIER.get();

            player.addEffect(new MobEffectInstance(digSpeed, duration, amplifier));
            level.playSound(null, player.blockPosition(), EGSounds.ELECTRIC_SOUND1.get(), SoundSource.PLAYERS);

            stack.getOrCreateTag().putBoolean(IS_ACTIVE_TAG, true);
            stack.getCapability(TimerProvider.TIMER).ifPresent(timer -> timer.setTimer(EGConfigs.HASTE_DURATION.get()));
        }
        return super.mineBlock(stack, level, state, pos, livingEntity);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return stack.getOrCreateTag().getBoolean(IS_ACTIVE_TAG) || super.isFoil(stack);
    }
}
