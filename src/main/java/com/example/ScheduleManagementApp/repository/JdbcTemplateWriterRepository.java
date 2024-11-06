package com.example.ScheduleManagementApp.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcTemplateWriterRepository implements WriterRepository {

    // Jdbc 연결
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateWriterRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long saveWriter(String name, String email, LocalDateTime createDateTime) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        jdbcInsert.withTableName("writers").usingGeneratedKeyColumns("id");

        Map<String, Object> paramsWriter = new HashMap<>();

        paramsWriter.put("name", name);
        paramsWriter.put("email", email);
        paramsWriter.put("enroll_date", createDateTime);
        paramsWriter.put("modify_date", createDateTime);

        return jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(paramsWriter)).longValue();
    }

    @Override
    public Long findScheduleByNameAndEmail(String name, String email) {
        return jdbcTemplate.query("select id from writers where name = ? and email = ?", (rs, num) -> rs.getLong("id"), name, email).stream()
                .findAny().orElse(null);
    }

    @Override
    public int updateWriterById(Long id, String name, LocalDateTime modifyDateTime) {
        return jdbcTemplate.update("update writers set name = ?, modify_date = ? where id = ?", name, modifyDateTime, id);
    }

}
