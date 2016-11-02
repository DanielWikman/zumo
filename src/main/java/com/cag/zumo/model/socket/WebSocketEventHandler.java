package com.cag.zumo.model.socket;

import com.cag.zumo.model.VehicleControl;
import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
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
        session.getAsyncRemote().sendText(message);
    }

    @OnClose
    public void myOnClose(final Session session, CloseReason cr) {
        log.info("websocket: onClose");
        buzy = false;
    }

    @OnError
    public void onError(final Session session, Throwable e) {
        log.info("websocket: onError: " + e.getMessage());
        buzy = false;
        try {
            session.close();
        } catch (IOException e1) {
            log.warn("Failed to close session.");
        }
    }
}
