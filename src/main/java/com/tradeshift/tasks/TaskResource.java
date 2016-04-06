package com.tradeshift.tasks;

import io.swagger.jaxrs.PATCH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.print.attribute.standard.Media;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

@Component
@Path("tasks")
public class TaskResource {

    @Context
    UriInfo uriInfo;

    private TaskService taskService;

    @Autowired
    public TaskResource(TaskService taskService) {
        this.taskService = taskService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTask(TaskCreateInput taskCreateInput) {
        if (taskCreateInput.getName() == null || taskCreateInput.getCreatedby() == null ||
                taskCreateInput.getName() == "" || taskCreateInput.getCreatedby() == "") {
            return Response.status(Response.Status.BAD_REQUEST).entity("Task name or createdby user can't be null or empty").build();
        }

        int taskId = taskService.createTask(taskCreateInput.getName(), taskCreateInput.getCreatedby());
        if (taskId == -1) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal Server Error, Please try again").build();
        }
        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI taskURI = ub.
                path(String.valueOf(taskId)).
                build();
        return Response.created(taskURI).entity(taskURI.toString()).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateTask(@PathParam("id") int taskId, TaskCreateInput taskCreateInput) {
        System.out.println("Id is " + taskId );
        System.out.println(taskCreateInput.toString());
        if (taskCreateInput.getName() != null || taskCreateInput.getCreatedby() != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Not allowed to update name or created by of a task").build();
        }
        String taskStatus = "";
        if (taskCreateInput.getStatus() != null) {
            taskStatus = taskCreateInput.getStatus().toString();
            if (!TaskStatus.contains(taskStatus)) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid value of task status, can be NEW, INPROGRESS or COMPLETE").build();
            }
        }
        TaskCreateInput task = taskService.updateTask(taskId, taskCreateInput.getAssignedto(), taskStatus);
        if (task == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating task, please check that task exists").build();
        }
        return Response.status(Response.Status.OK).entity(task).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteTask(@PathParam("id") int taskId) {
        if (taskService.getTask(taskId) == null) {
            System.out.println("In delete");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        boolean result = taskService.deleteTask(taskId);
        if (result == true) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTask(@PathParam("id") int taskId) {
        TaskCreateInput task = taskService.getTask(taskId);
        if (task == null) {
            return Response.status(404).entity("The task with id " + taskId + " does not exist").build();
        } else {
            return Response.status(200).entity(task).build();
        }
    }
}
