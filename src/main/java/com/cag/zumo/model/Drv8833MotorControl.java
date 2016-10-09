package com.cag.zumo.model;

import com.pi4j.io.gpio.GpioPinPwmOutput;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by dawi on 2016-10-09.
 * See https://www.pololu.com/file/0J534/drv8833.pdf
 */
public class Drv8833MotorControl extends MotorControl {


    private GpioPinPwmOutput xIn1;
    private GpioPinPwmOutput xIn2;
    private boolean softwarePin;
    private boolean slowDecay;

    public Drv8833MotorControl(GpioPinPwmOutput xIn1, GpioPinPwmOutput xIn2) {
        this.xIn1 = xIn1;
        this.xIn2 = xIn2;
    }

    public void setSoftwarePin(boolean isSoftwarePin) {
        this.softwarePin = softwarePin;
    }

    public void setSlowDecay(boolean slowDecay) {
        this.slowDecay = slowDecay;
    }

    /**
     * Set new speed, assuming this is a hardware pin.
     * @param newSpeed
     * @return
     */
    public boolean motorSpeed(@Max(100) @Min(-100) int newSpeed) {
        if (newSpeed == 0) {
            xIn1.setPwm(0);
            xIn2.setPwm(0);
        } else {
            if (slowDecay) {
                slowDecayControlMotor(getPwmValue(newSpeed));
            } else {
                fastDecayControlMotor(getPwmValue(newSpeed));
            }
        }
        setSpeed(newSpeed);
        return true;
    }

    private int getPwmValue(int speed) {
        if (softwarePin) {
            return speed;
        } {
            return speed * 1024 / 100;
        }
    }

    private boolean slowDecayControlMotor(int speed) {
        int newSpeed = Math.abs(speed);
        if (speed > 0) {
            xIn1.setPwm(1);
            xIn2.setPwm(newSpeed);
        } else {
            xIn1.setPwm(newSpeed);
            xIn2.setPwm(1);
        }
        return true;
    }

    private boolean fastDecayControlMotor(int speed) {
        int newSpeed = Math.abs(speed);
        if (speed > 0) {
            xIn1.setPwm(newSpeed);
            xIn2.setPwm(0);
        } else {
            xIn1.setPwm(0);
            xIn2.setPwm(newSpeed);
        }
        return true;
    }

}

