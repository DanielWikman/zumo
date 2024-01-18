package com.cag.zumo.joystick;

import lombok.extern.slf4j.Slf4j;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

import java.util.function.Consumer;

@Slf4j
public class JoystickEventHandler {

    private boolean armed;
    private Thread controllerThread;

    public JoystickEventHandler() {
        this.armed = false;
    }

    public void arm(Consumer<JoystickEvent> consumer) {
        if (armed) {
            throw new IllegalArgumentException("Cannot arm when already armed");
        }
        log.info("Armed ----- ");
        armed = true;
        controllerThread = new Thread(new JoystickEventProvider(consumer));
        controllerThread.start();
    }

    public void disarm() {
        log.info("Disarmed -----");
        armed = false;
    }

    public class JoystickEventProvider implements Runnable {

        private final Consumer<JoystickEvent> consumer;

        public JoystickEventProvider(Consumer<JoystickEvent> consumer) {
            this.consumer = consumer;
        }

        public void run() {
            log.info("Joystick Vehicle Thread started");
            Event event = new Event();

            /* Get the available controllers */
            Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
            while (armed) {
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
                            if (comp.getName().equals("y")) {
                                consumer.accept(JoystickEvent.builder()
                                        .driveSide(DriveSide.LEFT)
                                        .value(Math.round(comp.getPollData() * 100.0f))
                                        .build());
                            }
                            else if (comp.getName().equals("ry")) {
                                consumer.accept(JoystickEvent.builder()
                                        .driveSide(DriveSide.RIGHT)
                                        .value(Math.round(comp.getPollData() * 100.0f))
                                        .build());
                            } else if (comp.getName().equals("Select")) {
                                armed = false;
                            }
                        }
                    }
                }
            }
            log.info("Joystick Vehicle Thread stopped");
        }
    }
}
