package com.cerbon.electrum_gear.item.custom;

import com.cerbon.electrum_gear.capability.TimerProvider;
import com.cerbon.electrum_gear.config.EGConfigs;
import com.cerbon.electrum_gear.sound.EGSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ElectrumSwordItem extends SwordItem {
    private final String IS_ACTIVE_TAG = "IsActive";
    private final String OLD_AMPLIFIER_TAG = "OldAmplifier";
    private final String HIT_TIMES_TAG = "HitTimes";

    private final MobEffect digSpeed = MobEffects.DIG_SPEED;
    private final MobEffect movementSpeed = MobEffects.MOVEMENT_SPEED;

    public ElectrumSwordItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
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

                if (entity instanceof Player player && player.hasEffect(digSpeed)) {
                    MobEffectInstance currentEffect = player.getEffect(digSpeed);

                    player.removeEffect(digSpeed);
                    player.addEffect(new MobEffectInstance(digSpeed, currentEffect.getDuration(), tag.getInt(OLD_AMPLIFIER_TAG)));
                }

                if (entity instanceof Player player && player.hasEffect(movementSpeed)) {
                    MobEffectInstance currentEffect = player.getEffect(movementSpeed);

                    player.removeEffect(movementSpeed);
                    player.addEffect(new MobEffectInstance(movementSpeed, currentEffect.getDuration(), tag.getInt(OLD_AMPLIFIER_TAG)));
                }
            }
        });

        super.inventoryTick(stack, level, entity, slotId, isSelected);
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
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (!(attacker instanceof Player player)) return super.hurtEnemy(stack, target, attacker);

        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.getBoolean(IS_ACTIVE_TAG) && player.level().random.nextFloat() <= EGConfigs.SPEED_CHANCE.get()) {

            MobEffectInstance oldEffect = player.getEffect(movementSpeed);
            if (oldEffect != null)
                tag.putInt(OLD_AMPLIFIER_TAG, oldEffect.getAmplifier());

            int duration = oldEffect != null && oldEffect.getDuration() > EGConfigs.SPEED_DURATION.get() ? oldEffect.getDuration() : EGConfigs.SPEED_DURATION.get();
            int amplifier = oldEffect != null && oldEffect.getAmplifier() >= EGConfigs.SPEED_AMPLIFIER.get() ? oldEffect.getAmplifier() + 1 : EGConfigs.SPEED_AMPLIFIER.get();

            player.addEffect(new MobEffectInstance(movementSpeed, duration, amplifier));
            player.level().playSound(null, player.blockPosition(), EGSounds.ELECTRIC_SOUND1.get(), SoundSource.PLAYERS);

            stack.getOrCreateTag().putBoolean(IS_ACTIVE_TAG, true);
            stack.getCapability(TimerProvider.TIMER).ifPresent(timer -> timer.setTimer(EGConfigs.SPEED_DURATION.get()));
        }

        if (stack.getAllEnchantments().containsKey(Enchantments.CHANNELING)) {
            if (tag.getInt(HIT_TIMES_TAG) > EGConfigs.HITS_BEFORE_LIGHTINING_BOLT.get())
                if (player.level().random.nextFloat() <= EGConfigs.LIGHTINING_BOLT_CHANCE.get()) {
                    LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(player.level());

                    if (lightningBolt != null) {
                        lightningBolt.moveTo(target.position());
                        player.level().addFreshEntity(lightningBolt);
                    }

                    tag.putInt(HIT_TIMES_TAG, 0);
                } else
                    tag.putInt(HIT_TIMES_TAG, 0);

            tag.putInt(HIT_TIMES_TAG, tag.getInt(HIT_TIMES_TAG) + 1);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return stack.getOrCreateTag().getBoolean(IS_ACTIVE_TAG) || super.isFoil(stack);
    }
}
