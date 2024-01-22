package com.cag.zumo.control;

import com.cag.zumo.boundary.VehicleSpeed;
import com.cag.zumo.joystick.DriveSide;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by dawi on 2016-10-09.
 */
@Slf4j
public class VehicleControl {

    private MotorControl leftMotor;
    private MotorControl rightMotor;

    @Inject
    public VehicleControl(@Named("Left") MotorControl leftMotor, @Named("Right") MotorControl rightMotor) {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
    }

    public VehicleSpeed getSpeed() {
        return new VehicleSpeed(leftMotor.getSpeed(), rightMotor.getSpeed());
    }

    public VehicleSpeed speed(@Min(-100) @Max(100) int leftSpeed, @Min(-100) @Max(100) int rightSpeed) {
        leftMotor.motorSpeed(leftSpeed);
        rightMotor.motorSpeed(rightSpeed);
        return new VehicleSpeed(leftMotor.getSpeed(), rightMotor.getSpeed());
    }

    public VehicleSpeed stop() {
        leftMotor.setSpeed(0);
        rightMotor.setSpeed(0);
        return new VehicleSpeed(leftMotor.getSpeed(), rightMotor.getSpeed());
    }

    public VehicleSpeed speed(DriveSide side, @Min(-100) @Max(100) int speed) {
        //log.info("Speed {} {}", side, speed);
        if (DriveSide.LEFT.equals(side)) {
            leftMotor.motorSpeed(speed);
        } else {
            rightMotor.motorSpeed(speed);
        }
        return new VehicleSpeed(leftMotor.getSpeed(), rightMotor.getSpeed());
    }
}
