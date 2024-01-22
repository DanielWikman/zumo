package com.cag.zumo.joystick;

import com.cag.zumo.control.JoystickVehicleControl;
import com.google.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

@Slf4j
public class JoystickStandbyHandler implements Runnable {

    @Inject
    private JoystickVehicleControl activator;

    @Getter
    @Setter
    private boolean joystickConnected;

    @Override
    public void run() {
        log.info("Standby Joystick Thread started");
        Event event = new Event();
        /* Get the available controllers */
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        while(joystickConnected) {
            for (int i = 0; i < controllers.length; i++) {
                /* Remember to poll each one */
                boolean poll = controllers[i].poll();
                /* Get the controllers event queue */
                EventQueue queue = controllers[i].getEventQueue();

                if (poll) {
                    /* For each object in the queue */
                    while (queue.getNextEvent(event)) {
                        /* Get event component */
                        Component comp = event.getComponent();
                        if (comp.getName().equals("Start")) {
                            activator.arm();
                        }
                    }
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        log.info("Standby Joystick Thread stopped");
        activator.disarm();
    }

}
