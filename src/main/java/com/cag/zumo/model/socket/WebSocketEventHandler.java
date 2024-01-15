package com.cag.zumo.model.socket;

import com.cag.zumo.boundary.VehicleSpeed;
import com.cag.zumo.model.VehicleControl;
import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Created by dawi on 2016-10-23.
 */
@Slf4j
@Metered
@Timed
@ExceptionMetered
@ServerEndpoint(value = "/vehicleControl", configurator = WebSocketEventHandlerConfigurator.class)
public class WebSocketEventHandler {

    private VehicleControl control;
    private static ObjectMapper mapper = new ObjectMapper();
    static boolean buzy = false;

    @Inject
    public WebSocketEventHandler(VehicleControl control) {
        this.control = control;
    }

    @OnOpen
    public void onOpen(final Session session) throws IOException {
        log.info("websocket: onOpen");
        if (! buzy) {
            buzy = true;
            session.getAsyncRemote().sendText("ZumoBot says Hi !!!!");
        }
        else {
            session.getAsyncRemote().sendText("Zumobot is buzy!!!!");
            session.close(new CloseReason(CloseReason.CloseCodes.RESERVED, "Buzy"));
        }
    }

    @OnMessage
    public void onMessage(final Session session, String message) {
        log.info("websocket: onMessage: {}", message);
        try {
            VehicleSpeed speed = mapper.readValue(message, VehicleSpeed.class);
            VehicleSpeed result = this.control.speed(speed.getLeftSpeed(), speed.getRightSpeed());
            session.getAsyncRemote().sendText(mapper.writeValueAsString(result));
        } catch (IOException e) {
            log.info("websocket: onMessage: threw error", e);
        }

    }

    @OnClose
    public void myOnClose(final Session session, CloseReason cr) {
        log.info("websocket: onClose");
        control.stop();
        buzy = false;
    }

    @OnError
    public void onError(final Session session, Throwable e) {
        log.info("websocket: onError: " + e.getMessage());
        control.stop();
        buzy = false;
        try {
            session.close();
        } catch (IOException e1) {
            log.warn("Failed to close session.");
        }
    }
}
