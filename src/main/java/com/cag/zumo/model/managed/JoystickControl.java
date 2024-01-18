package com.cag.zumo.model.managed;

import com.cag.zumo.joystick.JoystickStandbyHandler;
import com.google.inject.Inject;
import io.dropwizard.lifecycle.Managed;
import lombok.extern.slf4j.Slf4j;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.ControllerEvent;
import net.java.games.input.ControllerListener;

import java.util.Objects;

@Slf4j
public class JoystickControl implements Managed, ControllerListener {

    private int joysticks = 0;
    @Inject
    private JoystickStandbyHandler joystickStandbyHandler;

    @Override
    public void start() {
        log.info("Registering Listener for Joysticks");
        ControllerEnvironment.getDefaultEnvironment().addControllerListener(this);
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        if (controllers.length == 0) {
            log.info("No Controllers found");
            System.exit(99);
        }
        for (Controller c: ControllerEnvironment.getDefaultEnvironment().getControllers()) {
            controllerAdded(new ControllerEvent(c));
        }
    }

    @Override
    public void stop() {
        log.info("De-Registering Listener for Joysticks");
        ControllerEnvironment.getDefaultEnvironment().addControllerListener(this);
    }

    @Override
    public void controllerRemoved(ControllerEvent ev) {
        joysticks--;
        log.info("Controller removed {}", joysticks);
        if (joysticks <= 0 && Objects.nonNull(joystickStandbyHandler)) {
            joystickStandbyHandler.setJoystickConnected(false);
        }
    }

    @Override
    public void controllerAdded(ControllerEvent ev) {
        joysticks++;
        log.info("Controller added {}", joysticks);
        if (! joystickStandbyHandler.isJoystickConnected()) {
            startJoystickThread();
        }
    }

    private void startJoystickThread() {
        joystickStandbyHandler.setJoystickConnected(true);
        Thread t = new Thread(joystickStandbyHandler);
        t.start();
    }
}
