package com.cag.zumo.boundary;

import java.util.Objects;

/**
 * Created by dawi on 2016-10-09.
 */
public class VehicleSpeed {

    public VehicleSpeed() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleSpeed that = (VehicleSpeed) o;
        return leftSpeed == that.leftSpeed &&
                rightSpeed == that.rightSpeed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftSpeed, rightSpeed);
    }


    public boolean isNotStill() {
        return leftSpeed != 0 || rightSpeed != 0;
    }
}
