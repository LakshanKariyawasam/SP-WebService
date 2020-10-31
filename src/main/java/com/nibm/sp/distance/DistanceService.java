/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nibm.sp.distance;

import com.nibm.sp.entity.Distance;
import java.sql.SQLException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Lakshan Kariyawasam
 * @version 1.0 15/10/2020
 */
@Path("/DistanceService")
public class DistanceService {

    @GET
    @Path("/getDistanceDetails")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getDistanceDetails() throws SQLException {
        DistanceController dc = new DistanceController();
        return Response.status(Response.Status.OK).entity(dc.getDistanceDetails()).build();
    }

    @POST
    @Path("/addDistance")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addDistance(Distance distance) {
        DistanceController dc = new DistanceController();
        return Response.status(Response.Status.OK).entity(dc.addDistance(distance)).build();
    }

    @PUT
    @Path("/editDistance")
    @Produces({MediaType.APPLICATION_JSON})
    public Response editDistance(Distance distance) {
        DistanceController dc = new DistanceController();
        return Response.status(Response.Status.OK).entity(dc.editDistance(distance)).build();
    }

    @PUT
    @Path("/deleteDistance")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteDistance(Distance distance) {
        DistanceController dc = new DistanceController();
        return Response.status(Response.Status.OK).entity(dc.deleteDistance(distance)).build();
    }

}
