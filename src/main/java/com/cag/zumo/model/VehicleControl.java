package com.cag.zumo.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by dawi on 2016-10-09.
 */
public class VehicleControl {

    private MotorControl leftMotor;
    private MotorControl rightMotor;

    public VehicleControl(MotorControl leftMotor, MotorControl rightMotor) {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
    }

    public VehicleSpeed speed(@Min(-100) @Max(100) int leftSpeed, @Min(-100) @Max(100) int rightSpeed) {
        leftMotor.motorSpeed(leftSpeed);
        rightMotor.motorSpeed(rightSpeed);
        return new VehicleSpeed(leftMotor.getSpeed(), rightMotor.getSpeed());
    }
}
