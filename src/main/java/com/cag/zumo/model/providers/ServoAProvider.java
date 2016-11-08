package com.cag.zumo.model.providers;

import com.cag.zumo.model.DecayMode;
import com.cag.zumo.model.MotorControl;
import com.cag.zumo.model.S03615Servo;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Created by dawi on 2016-11-05.
 */
public class ServoAProvider implements Provider<S03615Servo> {

    private GpioController controller;

    @Inject
    public ServoAProvider(GpioController controller) {
        this.controller = controller;
    }

    @Override
    public S03615Servo get() {
        return new S03615Servo(controller, RaspiPin.GPIO_27, RaspiPin.GPIO_28, RaspiPin.GPIO_29);
    }
}
