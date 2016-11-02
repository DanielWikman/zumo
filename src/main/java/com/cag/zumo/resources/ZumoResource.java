package com.cag.zumo.resources;

import com.cag.zumo.boundary.VehicleSpeed;
import com.cag.zumo.model.VehicleControl;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by dawi on 2016-10-06.
 */

@Path("/zumo")
public class ZumoResource {

    @Inject
    public ZumoResource(VehicleControl control) {
        vehicleControl = control;
    }

    private VehicleControl vehicleControl;

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
