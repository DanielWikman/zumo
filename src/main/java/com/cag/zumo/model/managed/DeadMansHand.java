package com.cag.zumo.model.managed;

import com.cag.zumo.boundary.VehicleSpeed;
import com.cag.zumo.model.VehicleControl;
import com.google.inject.Inject;
import io.dropwizard.lifecycle.Managed;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by dawi on 2016-10-23.
 */
public class DeadMansHand implements Managed, Runnable {

    private VehicleControl vehicleControl;
    private Thread monitoringThread;
    private boolean running = true;
    private VehicleSpeed lastSpeed;
    private long lastSpeedAt;
    private long allowedDelay = 1500;

    public void setAllowedDelay(long maxAllowedDelay) {
        this.allowedDelay = maxAllowedDelay;
    }

    @Inject
    public DeadMansHand(VehicleControl control) {
        this.vehicleControl = control;
    }

    public void start() throws Exception {
        running = true;
        monitoringThread = new Thread(this);
        monitoringThread.start();
    }

    public void stop() throws Exception {
        running = false;
    }

    public void run() {
        while (running) {
            VehicleSpeed speed = vehicleControl.getSpeed();
            if (! Objects.equals(lastSpeed, speed)) {
                lastSpeed = speed;
                lastSpeedAt = System.currentTimeMillis();
            }
            else {
                if (lastSpeed.isNotStill()) {
                    long now = System.currentTimeMillis();
                    if (now - lastSpeedAt > allowedDelay) {
                        lastSpeed = vehicleControl.speed(0, 0);
                        lastSpeedAt = now;
                    }
                }
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
        }
    }
}
