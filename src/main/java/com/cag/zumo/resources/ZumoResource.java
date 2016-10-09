package com.cag.zumo.resources;

import com.cag.zumo.model.Drv8833VehileControlFactory;
import com.cag.zumo.model.VehicleControl;
import com.cag.zumo.model.VehicleSpeed;
import com.codahale.metrics.annotation.Timed;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by dawi on 2016-10-06.
 */

@Singleton
@Path("/zumo")
public class ZumoResource {

    private VehicleControl vehicleControl;
    private GpioController controller;

    @PostConstruct
    public void init() {
        controller = GpioFactory.getInstance();
        vehicleControl = Drv8833VehileControlFactory
                .with(controller)
                .decayMode(true)
                .build();
    }

    @PreDestroy
    public void destroy() {
        controller.shutdown();
    }

    @Timed
    @GET
    @Path("stop")
    @Produces(MediaType.APPLICATION_JSON)
    public Response stop() {
        vehicleControl.speed(0, 0);
        return Response.ok().build();
    }

    @Timed
    @GET
    @Path("drive")
    @Produces(MediaType.APPLICATION_JSON)
    public VehicleSpeed drive(@QueryParam("leftSpeed") int left, @QueryParam("rightSpeed") int right) {
        return vehicleControl.speed(left, right);
    }

}
