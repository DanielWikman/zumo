package com.cag.zumo.control;

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
     * GPIO_26 = 32
     * GPIO_23 = 33
     * @return
     */
    public MotorControl get() {
        Drv8833MotorControl motorControl = new Drv8833MotorControl(controller, RaspiPin.GPIO_23, RaspiPin.GPIO_24);
        motorControl.setDecay(decayMode);
        return motorControl;
    }
}
