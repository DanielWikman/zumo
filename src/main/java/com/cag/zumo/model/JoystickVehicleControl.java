package com.cag.zumo.model;

import com.cag.zumo.joystick.JoystickEventHandler;
import com.cag.zumo.model.managed.DeadMansHand;
import com.google.inject.Inject;


public class JoystickVehicleControl {

    private final JoystickEventHandler joystickEventHandler;
    private final VehicleControl vehicleControl;
    private final DeadMansHand deadMansHand;

    @Inject
    public JoystickVehicleControl(JoystickEventHandler joystickEventHandler,
                                  VehicleControl vehicleControl,
                                  DeadMansHand deadMansHand) {
        this.joystickEventHandler = joystickEventHandler;
        this.vehicleControl = vehicleControl;
        this.deadMansHand = deadMansHand;
    }

    public boolean arm() {
        try {
            joystickEventHandler.arm(e -> vehicleControl.speed(e.driveSide(), e.value()));
            deadMansHand.setAllowedDelay(30000);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean disarm() {
        try {
            joystickEventHandler.disarm();
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
