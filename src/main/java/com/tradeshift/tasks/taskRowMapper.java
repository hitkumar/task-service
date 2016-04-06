package com.tradeshift.tasks;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRowMapper implements RowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        TaskCreateInput taskCreateInput = new TaskCreateInput();
        taskCreateInput.setName(rs.getString("name"));
        taskCreateInput.setStatus(TaskStatus.valueOf(rs.getString("status")));
        taskCreateInput.setCreatedby(rs.getString("createdby"));
        taskCreateInput.setAssignedto(rs.getString("assignedto"));
        taskCreateInput.setId(rs.getInt("task_id"));
        return taskCreateInput;
    }
}
