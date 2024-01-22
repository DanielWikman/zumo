package com.cag.zumo.control;

import com.pi4j.io.gpio.PinState;

/**
 * Created by dawi on 2016-10-10.
 */
public enum DecayMode {
    SLOW(PinState.LOW),
    FAST(PinState.HIGH);

    private PinState mode;
    DecayMode(PinState mode) {
        this.mode = mode;
    }

    public PinState getMode() {
        return mode;
    }
}
