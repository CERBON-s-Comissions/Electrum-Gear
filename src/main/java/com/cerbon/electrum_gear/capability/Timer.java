package com.cerbon.electrum_gear.capability;

public class Timer {
    int i = Integer.MAX_VALUE;

    public void setTimer(int value) {
        i = value;
    }

    public void decrease() {
        i--;
    }

    public int getCurrentTime() {
        return i;
    }
}
