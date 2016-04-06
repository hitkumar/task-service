package com.tradeshift.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class TaskDAO {
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
        String sql = "CREATE table IF NOT EXISTS tradeshift " +
                "(task_id serial PRIMARY KEY, createdby varchar(50) references users(user_id), " +
                "assignedto varchar(50) references users(user_id), name text, status varchar(10))";
        this.jdbcTemplate.execute(sql);
    }

    public int saveTask(final String name, final String createdBy) throws DataAccessException {
        String sql = "INSERT INTO tradeshift (createdby, assignedto, name, status) VALUES (?, ?, ?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(new PreparedStatementCreator() {

            String sql = "INSERT INTO tradeshift (createdby, assignedto, name, status) VALUES (?, ?, ?, ?)";
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, createdBy);
                ps.setString(2, null);
                ps.setString(3, name);
                ps.setString(4, String.valueOf(TaskStatus.NEW));
                return ps;
            }
        }, holder);
        return (Integer)holder.getKeys().get("task_id");
    }

    public int updateTask(int taskId, String assigned, String status) throws DataAccessException {
        String sql = "UPDATE tradeshift SET assignedto = ?, status = ? WHERE task_id = ?";
        return this.jdbcTemplate.update(sql, assigned, status.toString(), taskId);
    }

    public int updateTaskStatus(int taskId, String status) throws DataAccessException {
        String sql = "UPDATE tradeshift SET status = ? WHERE task_id = ?";
        return this.jdbcTemplate.update(sql, status.toString(), taskId);
    }

    public int updateTaskAssignedto(int taskId, String assignedTo) throws DataAccessException {
        String sql = "UPDATE tradeshift SET assignedto = ? WHERE task_id = ?";
        return this.jdbcTemplate.update(sql, assignedTo, taskId);
    }

    public void deleteTask(int taskId) throws SQLException {
        String sql = "DELETE FROM tradeshift where task_id = ?";
        this.jdbcTemplate.update(sql, taskId);
    }

    public TaskCreateInput getTask(int taskId) throws DataAccessException {
        String sql = "SELECT * FROM tradeshift WHERE task_id = ?";
        TaskCreateInput task = (TaskCreateInput)this.jdbcTemplate.queryForObject(
                sql, new Object[] { taskId },
                new TaskRowMapper());
        return task;
    }
}
