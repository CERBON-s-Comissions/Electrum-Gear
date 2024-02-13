package com.cerbon.electrum_gear.capability;

import net.minecraft.nbt.CompoundTag;

public class Timer {
    private int time;

    public int getCurrentTime() {
        return time;
    }

    public void decrease(int amount) {
        time = Math.max(time - amount, 0);
    }

    public void setTimer(int value) {
        time = value;
    }

    public void saveNBTData(CompoundTag tag) {
        tag.putInt("Timer", time);
    }

    public void loadNBTData(CompoundTag tag) {
        time = tag.getInt("Timer");
    }
}
