package com.cag.zumo.model;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Created by dawi on 2016-10-22.
 */

public class LeftMotorProvider implements Provider<MotorControl> {

    private GpioController controller;
    private DecayMode decayMode;

    @Inject
    public LeftMotorProvider(GpioController controller, DecayMode decayMode) {
        this.controller = controller;
        this.decayMode = decayMode;
    }

    /**
     * GPIO_01 == 12
     * GPIO_23 == 33 32 GPIO_26
     * @return
     */
    public MotorControl get() {
        Drv8833MotorControl motorControl = new Drv8833MotorControl(controller, RaspiPin.GPIO_26, RaspiPin.GPIO_01);
        motorControl.setDecay(decayMode);
        return motorControl;
    }
}
