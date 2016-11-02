package com.cag.zumo.model;

import com.google.inject.Inject;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by dawi on 2016-10-09.
 * See https://www.pololu.com/file/0J534/drv8833.pdf
 */
@Slf4j
public class Drv8833MotorControl extends MotorControl {

    private GpioController controller;
    private Pin xIn1;
    private Pin xIn2;
    private GpioPinPwmOutput pwmOutput;
    private GpioPinDigitalOutput digitalOutput;
    private boolean softwarePin;
    private DecayMode decayMode;

    public Drv8833MotorControl(GpioController controller, Pin xIn1, Pin xIn2) {
        this.controller = controller;
        this.xIn1 = xIn1;
        this.xIn2 = xIn2;
        pwmOutput = controller.provisionPwmOutputPin(xIn1);
        digitalOutput = controller.provisionDigitalOutputPin(xIn2);
    }

    public void setSoftwarePin(boolean isSoftwarePin) {
        this.softwarePin = softwarePin;
    }

    @Inject
    public void setDecay(DecayMode decayMode) {
        this.decayMode = decayMode;
    }

    /**
     * Set new speed, assuming this is a hardware pin.
     * @param newSpeed
     * @return
     */
    public boolean motorSpeed(@Max(100) @Min(-100) int  newSpeed) {

        if (newSpeed == 0) {
            pwmOutput.setPwm(0);
            digitalOutput.setState(decayMode.getMode());
        } else {
            if (newSpeed > 0 && getSpeed() <= 0) {
                controller.setMode(PinMode.PWM_OUTPUT, controller.getProvisionedPin(xIn1));
                controller.setMode(PinMode.DIGITAL_OUTPUT, controller.getProvisionedPin(xIn2));
                pwmOutput = (GpioPinPwmOutput) controller.getProvisionedPin(xIn1);
                digitalOutput = (GpioPinDigitalOutput) controller.getProvisionedPin(xIn2);
            }
            else if (newSpeed < 0 && getSpeed() >= 0) {
                controller.setMode(PinMode.DIGITAL_OUTPUT, controller.getProvisionedPin(xIn1));
                controller.setMode(PinMode.PWM_OUTPUT, controller.getProvisionedPin(xIn2));
                digitalOutput = (GpioPinDigitalOutput) controller.getProvisionedPin(xIn1);
                pwmOutput = (GpioPinPwmOutput) controller.getProvisionedPin(xIn2);
            }
            digitalOutput.setState(decayMode.getMode());
            pwmOutput.setPwm(getPwmValue(Math.abs(newSpeed)));
        }
        setSpeed(newSpeed);
        return true;
    }

    private int getPwmValue(int speed) {
        if (speed == 0) {
            return speed;
        }
        if (softwarePin) {
            return speed;
        }
        else {
            int pwm = 4 * speed + 624;
            log.info("PWM: {}", pwm);
            return pwm;
        }
    }

}

