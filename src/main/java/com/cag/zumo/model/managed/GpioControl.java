package com.cag.zumo.model.managed;

import com.cag.zumo.model.VehicleControl;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import io.dropwizard.lifecycle.Managed;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;

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

    public void start() {
        log.info("start");
    }

    public void stop() {
        log.info("stop");
        vehicleControl.stop();
        controller.shutdown();
    }

    public VehicleControl getVehicleControl() {
        return vehicleControl;
    }
}
