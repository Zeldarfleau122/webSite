package com.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcMissionRepository implements MissionRepository {
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean[] getMissionsStatus(String clientIP) {
        String query = "SELECT * FROM CHALLENGE WHERE ipAddress = '" + clientIP + "' ;";
        List<boolean[]> answer = jdbcTemplate.query(query, new MissionMapper());

        return answer.get(0) ;
    }

    class MissionMapper implements RowMapper<boolean[]> {

        @Override
        public boolean[] mapRow(ResultSet rs, int rowNum) throws SQLException {
            boolean[] res = new boolean[5] ;
            for (int i=1; i<=5; i++)
                res[i-1] = rs.getBoolean("mission" + i + "Achieved");
            return res ;
        }
    }
}
