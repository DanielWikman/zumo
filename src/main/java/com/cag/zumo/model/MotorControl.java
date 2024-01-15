package com.cag.zumo.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * Created by dawi on 2016-10-06.
 */
public abstract class MotorControl {

    @Max(100)
    @Min(0)
    private int speed;

    public MotorControl() {
        this.speed = 0;
    }

    public int getSpeed() {
        return speed;
    }

    protected void setSpeed(int speed) {
        this.speed = speed;
    }

    public abstract boolean motorSpeed(@Max(100) @Min(-100) int speed);
}
