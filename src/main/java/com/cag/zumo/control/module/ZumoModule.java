package com.cag.zumo.control.module;

import com.cag.zumo.joystick.JoystickEventHandler;
import com.cag.zumo.joystick.JoystickStandbyHandler;
import com.cag.zumo.control.DecayMode;
import com.cag.zumo.control.JoystickVehicleControl;
import com.cag.zumo.control.LeftMotorProvider;
import com.cag.zumo.control.MotorControl;
import com.cag.zumo.control.RightMotorProvider;
import com.cag.zumo.control.S03615Servo;
import com.cag.zumo.control.VehicleControl;
import com.cag.zumo.control.managed.DeadMansHand;
import com.cag.zumo.control.managed.GpioControl;
import com.cag.zumo.control.managed.JoystickControl;
import com.cag.zumo.control.providers.ServoAProvider;
import com.cag.zumo.control.providers.ServoBProvider;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

/**
 * Created by dawi on 2016-10-22.
 */
public class ZumoModule extends AbstractModule {

    protected void configure() {
            bind(MotorControl.class)
                    .annotatedWith(Names.named("Left"))
                    .toProvider(LeftMotorProvider.class);
            bind(MotorControl.class)
                    .annotatedWith(Names.named("Right"))
                    .toProvider(RightMotorProvider.class);
            bind(S03615Servo.class)
                    .annotatedWith(Names.named("ServoA"))
                    .toProvider(ServoAProvider.class);
            bind(S03615Servo.class)
                .annotatedWith(Names.named("ServoB"))
                .toProvider(ServoBProvider.class);
            bind(JoystickStandbyHandler.class);
            bind(JoystickControl.class);
            bind(VehicleControl.class).asEagerSingleton();
            bind(DecayMode.class).toInstance(DecayMode.SLOW);
            bind(DeadMansHand.class);
            bind(GpioControl.class);
            bind(GpioController.class).toInstance(GpioFactory.getInstance());
            bind(JoystickEventHandler.class).asEagerSingleton();
            bind(JoystickVehicleControl.class)
            ;
    }
}
