package com.tradeshift.users;

import com.tradeshift.tasks.TaskCreateInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("users")
public class UserResource {

    private UserService userService;

    @Autowired
    public UserResource(UserService s) {
        this.userService = s;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createTask(User user) {
        userService.createUser(user.getUsername());
    }

    @GET
    @Path("/{uid}/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksAssigned(@PathParam("uid") String uid) {
        if (uid == null || uid == "") {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        List<TaskCreateInput> tasks = userService.getTasksForUser(uid);
        return Response.status(200).entity(tasks).build();
    }
}
