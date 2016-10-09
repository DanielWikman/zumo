package com.cag.zumo.model;

/**
 * Created by dawi on 2016-10-09.
 */
public class VehicleSpeed {

    public VehicleSpeed(int leftSpeed, int rightSpeed) {
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
    }

    private int leftSpeed;
    private int rightSpeed;

    public int getLeftSpeed() {
        return leftSpeed;
    }

    public void setLeftSpeed(int leftSpeed) {
        this.leftSpeed = leftSpeed;
    }

    public int getRightSpeed() {
        return rightSpeed;
    }

    public void setRightSpeed(int rightSpeed) {
        this.rightSpeed = rightSpeed;
    }
}
