/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nibm.sp.branch;

import com.nibm.sp.entity.Branch;
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
@Path("/BranchService")
public class BranchService {

    @GET
    @Path("/getBranchDetails")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBranchDetails() throws SQLException {
        BranchController bc = new BranchController();
        return Response.status(Response.Status.OK).entity(bc.getBranchDetails()).build();
    }

    @POST
    @Path("/addBranch")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addUser(Branch branch) {
        BranchController bc = new BranchController();
        return Response.status(Response.Status.OK).entity(bc.addBranch(branch)).build();
    }

    @PUT
    @Path("/editBranch")
    @Produces({MediaType.APPLICATION_JSON})
    public Response editBranch(Branch branch) {
        BranchController bc = new BranchController();
        return Response.status(Response.Status.OK).entity(bc.editBranch(branch)).build();
    }

    @PUT
    @Path("/deleteBranch")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteBranch(Branch branch) {
        BranchController bc = new BranchController();
        return Response.status(Response.Status.OK).entity(bc.deleteBranch(branch)).build();
    }

}
