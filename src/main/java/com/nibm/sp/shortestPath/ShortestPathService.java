/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nibm.sp.shortestPath;

import java.sql.SQLException;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Lakshan Kariyawasam
 * @version 1.0 15/10/2020
 */
@Path("/ShortestPathService")
public class ShortestPathService {

    @GET
    @Path("/getShortestPathDetails")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getShortestPathDetails(@QueryParam("from") @DefaultValue("0") String from, @QueryParam("to") @DefaultValue("0") String to) throws SQLException {
        ShortestPathController spc = new ShortestPathController();
        return Response.status(Response.Status.OK).entity(spc.getShortestPathDetails(from, to)).build();
    }
}
