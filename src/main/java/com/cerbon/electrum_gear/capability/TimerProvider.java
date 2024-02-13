package com.cerbon.electrum_gear.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AutoRegisterCapability
public class TimerProvider implements ICapabilitySerializable<CompoundTag> {
    public static final Capability<Timer> TIMER = CapabilityManager.get(new CapabilityToken<>() {});

    private Timer timer;
    private final LazyOptional<Timer> optional = LazyOptional.of(this::createTimer);

    private Timer createTimer() {
        if(this.timer == null)
            this.timer = new Timer();

        return this.timer;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        if (timer != null)
            tag.putInt("Timer", timer.getCurrentTime());

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("Timer") && timer != null)
            timer.setTimer(tag.getInt("Timer"));
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == TIMER)
            return optional.cast();

        return LazyOptional.empty();
    }
}
