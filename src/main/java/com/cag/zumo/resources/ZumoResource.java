package com.cag.zumo.resources;

import com.cag.zumo.boundary.VehicleSpeed;
import com.cag.zumo.model.S03615Servo;
import com.cag.zumo.model.VehicleControl;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Created by dawi on 2016-10-06.
 */

@Path("/zumo")
public class ZumoResource {

    @Inject
    public ZumoResource(VehicleControl control, @Named("ServoA") S03615Servo servoA,  @Named("ServoB") S03615Servo servoB) {
        vehicleControl = control;
        this.servoA = servoA;
        this.servoB = servoB;
    }

    private S03615Servo servoA;
    private S03615Servo servoB;
    private VehicleControl vehicleControl;

    @Timed
    @GET
    @Path("stop")
    @Produces(MediaType.APPLICATION_JSON)
    public VehicleSpeed stop() {
        return vehicleControl.speed(0, 0);
    }

    @Timed
    @GET
    @Path("drive")
    @Produces(MediaType.APPLICATION_JSON)
    public VehicleSpeed drive(@QueryParam("leftSpeed") int left, @QueryParam("rightSpeed") int right) {
        return vehicleControl.speed(left, right);
    }

    @Timed
    @GET
    @Path("servoA")
    @Produces(MediaType.APPLICATION_JSON)
    public Response servoA(@QueryParam("black") boolean black,
                               @QueryParam("red") boolean red,
                               @QueryParam("white") boolean white) {
        servoA.toggle(black, red, white);
        return Response.ok().build();
    }

    @Timed
    @GET
    @Path("servoB")
    @Produces(MediaType.APPLICATION_JSON)
    public Response servoB(@QueryParam("black") boolean black,
                           @QueryParam("red") boolean red,
                           @QueryParam("white") boolean white) {
        servoB.toggle(black, red, white);
        return Response.ok().build();
    }

}
