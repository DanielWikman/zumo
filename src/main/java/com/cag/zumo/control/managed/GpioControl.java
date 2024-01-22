package com.cag.zumo.control.managed;

import com.cag.zumo.control.VehicleControl;
import com.google.inject.Inject;
import com.pi4j.io.gpio.GpioController;
import io.dropwizard.lifecycle.Managed;
import lombok.extern.slf4j.Slf4j;


/**
 * Created by dawi on 2016-10-09.
 */
@Slf4j
public class GpioControl implements Managed {

    private GpioController controller;
    private VehicleControl vehicleControl;

    @Inject
    public GpioControl(VehicleControl vehicleControl, GpioController controller) {
        this.vehicleControl = vehicleControl;
        this.controller = controller;
    }

    @Override
    public void start() {
        log.info("start");
    }

    @Override
    public void stop() {
        log.info("stop");
        vehicleControl.stop();
        controller.shutdown();
    }

    public VehicleControl getVehicleControl() {
        return vehicleControl;
    }
}
