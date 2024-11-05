package com.example.ScheduleManagementApp.repository;

import com.example.ScheduleManagementApp.dto.ScheduleResponseDto;
import com.example.ScheduleManagementApp.exception.NotExistIdException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    // Jdbc 연결
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Number JdbcInsertWriter(String name, String email, LocalDateTime createDateTime){

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        jdbcInsert.withTableName("writers").usingGeneratedKeyColumns("id");

        Map<String, Object> paramsWriter = new HashMap<>();

        paramsWriter.put("name", name);
        paramsWriter.put("email", email);
        paramsWriter.put("enroll_date", createDateTime);
        paramsWriter.put("modify_date", createDateTime);

        return jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(paramsWriter));
    }

    public Number JdbcInsertSchedule(String pw, String schedule, LocalDateTime createDateTime, Long writerId){

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        jdbcInsert.withTableName("schedules").usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();

        params.put("pw", pw);
        params.put("schedule", schedule);
        params.put("enroll_date", createDateTime);
        params.put("modify_date", createDateTime);
        params.put("writer_id", writerId);

        return jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
    }

    @Transactional
    @Override
    public ScheduleResponseDto saveSchedule(String name, String email, String pw, String schedule, LocalDateTime createDateTime) {

        // writer 저장
        Number writerId = JdbcInsertWriter(name, email, createDateTime);

        // schedule 저장
        Number scheduleId = JdbcInsertSchedule(pw, schedule, createDateTime, writerId.longValue());

        return new ScheduleResponseDto(scheduleId.longValue(), name, schedule, createDateTime);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule(String name, String date, Integer page, Integer size) {

        return jdbcTemplate.query("select s.id, w.name, s.schedule, s.modify_date from schedules as s join writers as w on s.writer_id = w.id " +
                        "where (w.name = ? OR ? IS NULL) AND (DATE_FORMAT(s.modify_date, '%Y-%m-%d') = ? OR ? IS NULL) " +
                        "ORDER BY s.modify_date DESC LIMIT ? OFFSET ?",
                (rs, rowNum) ->
                        new ScheduleResponseDto(rs.getLong("id"), rs.getString("name"), rs.getString("schedule"), rs.getObject("modify_date", LocalDateTime.class)),
                name, name, date, date, size, (page-1)*size);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        return jdbcTemplate.query("select s.id, w.name, s.schedule, s.modify_date from schedules as s join writers as w on s.writer_id = w.id where s.id = ?",
                (rs, rowNum) -> new ScheduleResponseDto (
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("schedule"),
                rs.getObject("modify_date", LocalDateTime.class)
        ), id).stream().findAny().orElseThrow(()-> new NotExistIdException("[id = " + id + "] 에 해당하는 정보가 존재하지 않습니다."));
    }

    @Override
    public String findSchedulePassWordById(Long id) {
        return jdbcTemplate.query("select pw from schedules where id = ?",
                (rs, rowNum) -> rs.getString("pw")
                , id).stream().findAny().orElseThrow(()-> new NotExistIdException("[id = " + id + "] 에 해당하는 정보가 존재하지 않습니다."));

    }

    @Override
    public int updateScheduleById(Long id, String name, String schedule, LocalDateTime modifyDateTime) {
        return jdbcTemplate.update("update schedules as s join writers as w on s.writer_id = w.id " +
                "set w.name = ?, s.schedule = ?, s.modify_date = ?, w.modify_date = ? " +
                "where s.id = ?", name, schedule, modifyDateTime, modifyDateTime, id);
    }



    @Override
    public int deleteScheduleById(Long id) {
        return jdbcTemplate.update("delete from schedules where id = ?", id);
    }




}
