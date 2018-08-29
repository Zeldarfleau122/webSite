package com.persistence;

import com.services.Challenge;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcChallengeRepository {
    JdbcTemplate jdbcTemplate ;

    /**
     * Constructeur
     *
     * @param jdbcTemplate jdbcTemplate a la BD
     */
    public JdbcChallengeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate ;
    }

    public List<Challenge> findAll() {
        String query = "SELECT * FROM CHALLENGE ;";
        List<Challenge> challenges = jdbcTemplate.query(query, new ChallengeMapper());

        return challenges ;
    }

    class ChallengeMapper implements RowMapper<Challenge> {
        @Override
        public Challenge mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("ID") ;
            String title = rs.getString("TITLE");
            String content = rs.getString("CONTENT") ;

            return new Challenge(id, title, content);
        }
    }
}
