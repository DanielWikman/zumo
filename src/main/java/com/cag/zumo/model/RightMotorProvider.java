package com.cag.zumo.model;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Created by dawi on 2016-10-22.
 */
public class RightMotorProvider implements Provider<MotorControl> {

    private GpioController controller;
    private DecayMode decayMode;

    @Inject
    public RightMotorProvider(GpioController controller, DecayMode decayMode) {
        this.controller = controller;
        this.decayMode = decayMode;
    }

    /**
     * GPIO_26 = 32 35 GPIO_24
     * GPIO_24 = 35 33 GPIO_23
     * @return
     */
    public MotorControl get() {
        Drv8833MotorControl motorControl = new Drv8833MotorControl(controller, RaspiPin.GPIO_23, RaspiPin.GPIO_24);
        motorControl.setDecay(decayMode);
        return motorControl;
    }
}
