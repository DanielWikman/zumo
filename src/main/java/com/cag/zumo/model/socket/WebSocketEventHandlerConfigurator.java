package com.cag.zumo.model.socket;

import com.cag.zumo.model.module.ZumoModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import jakarta.websocket.server.ServerEndpointConfig;

/**
 * Created by dawi on 2016-10-23.
 */
public class WebSocketEventHandlerConfigurator extends ServerEndpointConfig.Configurator {

    private static Injector injector;

    public static void setInjector(Injector injector) {
        WebSocketEventHandlerConfigurator.injector = injector;
    }

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        return (T) injector.getInstance(WebSocketEventHandler.class);
    }
}
