package com.example.ScheduleManagementApp.writers.repository;

import com.example.ScheduleManagementApp.exception.NotExistIdException;
import com.example.ScheduleManagementApp.writers.dto.WriterResponseDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcTemplateWriterRepository implements WriterRepository {

    // Jdbc 연결
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateWriterRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional
    @Override
    public WriterResponseDto saveWriter(String name, String email, String pw, LocalDateTime createDateTime) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        jdbcInsert.withTableName("writers").usingGeneratedKeyColumns("id");

        Map<String, Object> paramsWriter = new HashMap<>();

        paramsWriter.put("name", name);
        paramsWriter.put("email", email);
        paramsWriter.put("enroll_date", createDateTime);
        paramsWriter.put("modify_date", createDateTime);

        Number writerId =  jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(paramsWriter));

        return new WriterResponseDto(writerId.longValue(), name, email, createDateTime);
    }


    @Override
    public WriterResponseDto findWriterById(Long id) {
        return jdbcTemplate.query("select id, name, email, modify_date from writers where id = ?",
                (rs, rowNum) -> new WriterResponseDto (
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getObject("modify_date", LocalDateTime.class)
        ), id).stream().findAny().orElseThrow(()-> new NotExistIdException("[id = " + id + "] 에 해당하는 작성자 정보가 존재하지 않습니다."));
    }

    @Override
    public Integer findWriterByNameAndEmail(String name, String email) {
        return jdbcTemplate.queryForObject("select count(*) from writers where name = ? and email = ?",Integer.class, name, email);
    }


    @Override
    public String findWriterPassWordById(Long id) {
        return jdbcTemplate.query("select pw from writers where id = ?",
                (rs, rowNum) -> rs.getString("pw")
                , id).stream().findAny().orElseThrow(()-> new NotExistIdException("[id = " + id + "] 에 해당하는 작성자 정보가 존재하지 않습니다."));

    }

    @Override
    public int updateWriterById(Long id, String name, LocalDateTime modifyDateTime) {
        return jdbcTemplate.update("update writers set name = ?, modify_date = ? where id = ?", name, modifyDateTime, id);
    }

    @Override
    public int deleteWriterById(Long id) {
        return jdbcTemplate.update("delete from writers where id = ?", id);
    }




}
