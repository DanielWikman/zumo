package com.cag.zumo.model;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinState;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Future;

/**
 * Created by dawi on 2016-11-05.
 */
@Slf4j
public class S03615Servo {

    private GpioController controller;
    private Pin xIn1;
    private Pin xIn2;
    private Pin xIn3;
    private GpioPinDigitalOutput pinBlack;
    private GpioPinDigitalOutput pinRed;
    private GpioPinDigitalOutput pinWhite;
    private int duration = 400;

    public S03615Servo(GpioController controller, Pin xIn1, Pin xIn2, Pin xIn3) {
        this.controller = controller;
        this.xIn1 = xIn1;
        this.xIn2 = xIn2;
        this.xIn3 = xIn3;
        pinBlack = controller.provisionDigitalOutputPin(xIn1);
        pinRed = controller.provisionDigitalOutputPin(xIn2);
        pinWhite = controller.provisionDigitalOutputPin(xIn3);
        controller.setMode(PinMode.DIGITAL_OUTPUT, controller.getProvisionedPin(xIn1));
        controller.setMode(PinMode.DIGITAL_OUTPUT, controller.getProvisionedPin(xIn2));
        controller.setMode(PinMode.DIGITAL_OUTPUT, controller.getProvisionedPin(xIn3));
    }

    /**
     * Set new speed, assuming this is a hardware pin.
     * @param black
     * @param red
     * @param white
     * @return
     */
    public boolean toggle(boolean black, boolean red, boolean white) {
        log.info("Toggle b={} r={} w={}", black, red, white);
        if (black) {
            log.info("black-on");
            pinBlack.pulse(duration, PinState.HIGH, () -> {
                log.info("black-off");
                pinBlack.low();
                return null;
            });
        }
        if (red) {
            log.info("red-on");
            pinRed.pulse(duration, PinState.HIGH, () -> {
                log.info("red-off");
                pinRed.low();
                return null;
            });        }
        if (white) {
            log.info("white-on");
            pinWhite.pulse(duration, PinState.HIGH, () -> {
                log.info("white-off");
                pinWhite.low();
                return null;
            });
        }
        return true;
    }

}
