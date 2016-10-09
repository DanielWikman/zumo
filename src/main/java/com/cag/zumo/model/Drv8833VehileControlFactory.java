package com.cag.zumo.model;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Created by dawi on 2016-10-09.
 */
public class Drv8833VehileControlFactory {

    private GpioController controller;
    private boolean slowDecay;

    public static Drv8833VehileControlFactory with(GpioController controller) {
        Drv8833VehileControlFactory factory = new Drv8833VehileControlFactory();
        factory.setController(controller);
        return factory;
    }

    private void setController(GpioController controller) {
        this.controller = controller;
    }

    public Drv8833VehileControlFactory decayMode(boolean slowDecay) {
        this.slowDecay = slowDecay;
        return this;
    }

    public VehicleControl build() {
        GpioPinPwmOutput leftMotor1 = controller.provisionPwmOutputPin(RaspiPin.GPIO_01);
        GpioPinPwmOutput leftMotor2 = controller.provisionPwmOutputPin(RaspiPin.GPIO_23);
        GpioPinPwmOutput rightMotor1 = controller.provisionPwmOutputPin(RaspiPin.GPIO_24);
        GpioPinPwmOutput rightMotor2 = controller.provisionPwmOutputPin(RaspiPin.GPIO_26);
        Drv8833MotorControl left = new Drv8833MotorControl(leftMotor1, leftMotor2);
        Drv8833MotorControl right = new Drv8833MotorControl(rightMotor1, rightMotor2);
        if (slowDecay) {
            left.setSlowDecay(true);
            right.setSlowDecay(true);
        }
        VehicleControl vehicleControl = new VehicleControl(left, right);
        return vehicleControl;
    }
}