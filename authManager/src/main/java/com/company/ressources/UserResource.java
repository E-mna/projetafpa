package com.company.ressources;


import com.company.entity.User;
import com.company.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "User Management",
        description = "Operations related to user management")
public class UserResource {
    @Inject
    UserService userService;

    @GET
    @RolesAllowed("ADMIN")
    @Operation(summary = "List all users",
            description = "Returns a list of all registered users.")
    @APIResponse(responseCode = "200",
            description = "List of users returned successfully")
    @APIResponse(responseCode = "204",
            description = "No users found")
    public Response listUsers() {
        //tesing service  : edit later
        return Response.ok(
                userService.findAll()
        ).build();


    }

    @POST
    public Response AddUsers(User user) {
        //tesing service  : edit later
        return Response.ok(
                userService.findAll()
        ).build();


    }

    @POST
    @Path("/authenticate")
    @Operation(summary = "Authenticate a user",
            description = "Authenticates the user with login and password.")
    @APIResponse(responseCode = "200",
            description = "User authenticated successfully")
    @APIResponse(responseCode = "401",
            description = "Invalid login or password")
    public Response authenticateUser(@HeaderParam("login") String login,
                                     @HeaderParam("password") String password) {
        //tesing service  : edit later
        return Response.ok(
                userService.findAll()
        ).build();


    }

    @POST
    @Path("/authenticate2FA")
    @Operation(summary = "Authenticate a user",
            description = "Authenticates the user with login and password.")
    @APIResponse(responseCode = "200",
            description = "User authenticated successfully")
    @APIResponse(responseCode = "401",
            description = "Invalid login or password")
    public Response authenticateUser2FA(
            @HeaderParam("login") String login,

            @HeaderParam("password") String password,

            @HeaderParam("pin") String pin) {
        //tesing service  : edit later
        return Response.ok(
                userService.findAll()
        ).build();


    }

    @GET
    @Path("/confirm/{code}")
    @Operation(summary = "Get user by login",
            description = "Retrieve details of a user based on the login ID.")
    @APIResponse(responseCode = "200",
            description = "User details found")
    @APIResponse(responseCode = "404",
            description = "User not found")
    public Response confirmUser(@PathParam("code") String code) {
        //tesing service  : edit later
        return Response.ok(
                userService.findAll()
        ).build();

    }

    @GET
    @Path("/{login}")
    @Operation(summary = "Get user by login",

            description = "Retrieve details of a user based on the login ID.")
    @APIResponse(responseCode = "200",
            description = "User details found")
    @APIResponse(responseCode = "404",
            description = "User not found")
    public Response getUserByLogin(@PathParam("login") String login) {
        //tesing service  : edit later
        return Response.ok(
                userService.findAll()
        ).build();


    }
}

