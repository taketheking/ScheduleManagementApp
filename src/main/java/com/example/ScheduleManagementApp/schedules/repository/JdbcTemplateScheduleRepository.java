package com.example.ScheduleManagementApp.schedules.repository;

import com.example.ScheduleManagementApp.schedules.dto.ScheduleResponseDto;
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


    @Transactional
    @Override
    public ScheduleResponseDto saveSchedule(String name, String pw, String schedule, LocalDateTime createDateTime) {

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        simpleJdbcInsert.withTableName("schedules").usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();

        params.put("pw", pw);
        params.put("schedule", schedule);
        params.put("enroll_date", createDateTime);
        params.put("modify_date", createDateTime);

        Number scheduleId = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));

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
    public Integer findScheduleByNameAndEmail(String name, String email) {
        return jdbcTemplate.queryForObject("select count(*) from schedules where name = ? and email = ?",Integer.class, name, email);
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
