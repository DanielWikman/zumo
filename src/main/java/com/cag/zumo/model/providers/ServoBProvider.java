package com.cag.zumo.model.providers;

import com.cag.zumo.model.S03615Servo;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Created by dawi on 2016-11-05.
 */
public class ServoBProvider implements Provider<S03615Servo> {
    private GpioController controller;

    @Inject
    public ServoBProvider(GpioController controller) {
        this.controller = controller;
    }

    @Override
    public S03615Servo get() {
        return new S03615Servo(controller, RaspiPin.GPIO_00, RaspiPin.GPIO_02, RaspiPin.GPIO_03);
    }
}
