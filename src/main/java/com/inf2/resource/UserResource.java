package com.inf2.resource;

import com.inf2.dto.user.UserCreateRequest;
import com.inf2.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("user")
public class UserResource {

    // step 4 : where I define the paths to interact with the server
    @Inject
    private UserService userService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(UserCreateRequest user) {
        try {
            userService.createUser(user);
            return Response.status(Response.Status.CREATED).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
