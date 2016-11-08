package com.cag.zumo.model.module;

import com.cag.zumo.model.DecayMode;
import com.cag.zumo.model.LeftMotorProvider;
import com.cag.zumo.model.MotorControl;
import com.cag.zumo.model.RightMotorProvider;
import com.cag.zumo.model.S03615Servo;
import com.cag.zumo.model.VehicleControl;
import com.cag.zumo.model.managed.DeadMansHand;
import com.cag.zumo.model.managed.GpioControl;
import com.cag.zumo.model.providers.ServoAProvider;
import com.cag.zumo.model.providers.ServoBProvider;
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
            bind(VehicleControl.class).asEagerSingleton();
            bind(DecayMode.class).toInstance(DecayMode.SLOW);
            bind(DeadMansHand.class);
            bind(GpioControl.class);
            bind(GpioController.class).toInstance(GpioFactory.getInstance());
    }
}
