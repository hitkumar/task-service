package com.tradeshift.tasks;

import java.io.Serializable;

public class TaskCreateInput implements Serializable{

    private int task_id;

    private String name;

    private String createdby;

    private String assignedto;

    private TaskStatus status;

    public int getId() {
        return task_id;
    }

    public void setId(int id) {
        this.task_id = id;
    }

    public TaskCreateInput() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getAssignedto() {
        return assignedto;
    }

    public void setAssignedto(String assignedto) {
        this.assignedto = assignedto;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("{\"Name\":%s, \"id\": %d, \"created_by\": %s, \"assigned_to\": %s, \"status\": %s}",
                name, task_id, createdby, assignedto, status.toString());
    }
}
