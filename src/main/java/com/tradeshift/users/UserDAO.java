package com.tradeshift.users;

import com.tradeshift.tasks.TaskCreateInput;
import com.tradeshift.tasks.TaskRowMapper;
import com.tradeshift.tasks.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class UserDAO {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        createSchema();
    }

    private void createSchema() {
        String sql = "CREATE table IF NOT EXISTS users " +
                "(user_id varchar(50) PRIMARY KEY, username varchar(30) UNIQUE)";
        this.jdbcTemplate.execute(sql);
    }

    public void saveUser(String username) throws DataAccessException {
        String uuid = String.valueOf(UUID.randomUUID());
        String sql = "INSERT INTO users (user_id, username) VALUES (?, ?)";
        this.jdbcTemplate.update(sql, uuid, username);
    }

    public List<TaskCreateInput> getTasksForUser(String uid) throws DataAccessException {
        String sql = "SELECT * from tradeshift where assignedto =?";
        List<TaskCreateInput> tasks = this.jdbcTemplate.query(sql, new Object[] {uid}, new TaskRowMapper());
        List<TaskCreateInput> tasksToReturn = new ArrayList<TaskCreateInput>();

        for (TaskCreateInput t : tasks) {
            TaskCreateInput taskCreateInput = new TaskCreateInput();
            taskCreateInput.setStatus(t.getStatus());
            taskCreateInput.setName(t.getName());
            taskCreateInput.setAssignedto(t.getAssignedto());
            taskCreateInput.setCreatedby(t.getCreatedby());

            tasksToReturn.add(taskCreateInput);
        }
        System.out.println("tasks are " + tasksToReturn);
        return tasks;
    }
}
