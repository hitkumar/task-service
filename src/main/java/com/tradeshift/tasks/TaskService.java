package com.tradeshift.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class TaskService {

    private TaskDAO taskDAO;

    @Autowired
    public TaskService(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public int createTask(String name, String createdBy) {
        try {
            return taskDAO.saveTask(name, createdBy);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public TaskCreateInput updateTask(int taskId, String assignedTo, String status) {
        int rowsAffected = 0;
        try {
            if (assignedTo != "" && status != "") {
                rowsAffected = taskDAO.updateTask(taskId, assignedTo, status);
            } else if (assignedTo != "") {
                rowsAffected = taskDAO.updateTaskAssignedto(taskId, assignedTo);
            } else if (status != "") {
                rowsAffected = taskDAO.updateTaskStatus(taskId, status);
            } else {
                System.out.println("Error updating task, invalid parameters");
            }

            if (rowsAffected != 1) {
                return null;
            } else {
                return getTask(taskId);
            }
        } catch(DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteTask(int taskId) {
        try {
            taskDAO.deleteTask(taskId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public TaskCreateInput getTask(int taskId) {
        try {
            return taskDAO.getTask(taskId);
        } catch (DataAccessException e) {
            System.out.println("Exception caught");
            e.printStackTrace();
        }
        return null;
    }
}
