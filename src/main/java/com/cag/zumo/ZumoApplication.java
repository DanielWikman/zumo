package com.cag.zumo;

import be.tomcools.dropwizard.websocket.WebsocketBundle;
import com.cag.zumo.health.ZumoHealth;
import com.cag.zumo.model.managed.DeadMansHand;
import com.cag.zumo.model.managed.GpioControl;
import com.cag.zumo.model.module.ZumoModule;
import com.cag.zumo.model.socket.WebSocketEventHandler;
import com.cag.zumo.model.socket.WebSocketEventHandlerConfigurator;
import com.cag.zumo.resources.ZumoResource;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by dawi on 2016-10-06.
 */
@Slf4j
public class ZumoApplication extends Application<ZumoConfiguration> {

    private final WebsocketBundle<ZumoConfiguration> websocket = new WebsocketBundle<>();

    private Injector injector;

    public static void main(String[] args) throws Exception {
        new ZumoApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<ZumoConfiguration> bootstrap) {
        injector = Guice.createInjector(new ZumoModule());
        WebSocketEventHandlerConfigurator.setInjector(injector);
        bootstrap.addBundle(websocket);
    }

    @Override
    public void run(ZumoConfiguration zumoConfiguration, Environment environment) throws Exception {
        websocket.addEndpoint(WebSocketEventHandler.class);
        ZumoResource zumoResource = injector.getInstance(ZumoResource.class);
        GpioControl gpioControl = injector.getInstance(GpioControl.class);
        ZumoHealth  health = injector.getInstance(ZumoHealth.class);
        DeadMansHand deadMansHand = injector.getInstance(DeadMansHand.class);
        log.info("add health");
        environment.healthChecks().register("zumo", health);
        log.info("manage control");
        environment.lifecycle().manage(gpioControl);
        environment.lifecycle().manage(deadMansHand);
        log.info("add resource");
        environment.jersey().register(zumoResource);
    }
}
