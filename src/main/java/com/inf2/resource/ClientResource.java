package com.inf2.resource;

import com.inf2.domain.Client;
import com.inf2.dto.user.UserCreateRequest;
import com.inf2.dto.user.UserUpdateRequest;
import com.inf2.service.ClientService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("client")
public class ClientResource {

    // step 4 : where I define the paths to interact with the server
    @Inject
    private ClientService clientService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createClient(UserCreateRequest user) {
        try {
            clientService.createClient(user);
            return Response.status(Response.Status.CREATED).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClient(@PathParam("id") UUID id) {
        try {
            Client fetchedClient = clientService.getClientById(id);
            return Response.status(Response.Status.OK).entity(fetchedClient).build();
        }catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteClient(@PathParam("id") UUID id) {
        try {
            clientService.deleteClient(id);
            return Response.status(Response.Status.OK).build();
        }catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateClient(@PathParam("id") UUID id, UserUpdateRequest userUpdateRequest) {
        try {
            clientService.updateClient(id, userUpdateRequest);
            return Response.status(Response.Status.OK).build();
        }catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
