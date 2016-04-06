package com.tradeshift.users;

import com.tradeshift.tasks.TaskCreateInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserService {

    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void createUser(String username) {
        try {
            userDAO.saveUser(username);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public List<TaskCreateInput> getTasksForUser(String uid) {
        try {
            return userDAO.getTasksForUser(uid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
