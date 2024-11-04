package com.example.ScheduleManagementApp.repository;

import com.example.ScheduleManagementApp.dto.ScheduleResponseDto;
import com.example.ScheduleManagementApp.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

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

    @Override
    public ScheduleResponseDto saveSchedule(String name, String pw, String schedule, LocalDateTime localDateTime) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        jdbcInsert.withTableName("schedules").usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();

        params.put("name", name);
        params.put("pw", pw);
        params.put("schedule", schedule);
        params.put("enroll_date", localDateTime);
        params.put("modify_date", localDateTime);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));

        return new ScheduleResponseDto(key.longValue(), name, schedule, localDateTime);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule(String name, String date) {

        return jdbcTemplate.query("select * from schedules where (name = ? OR ? IS NULL) AND (DATE_FORMAT(modify_date, '%Y-%m-%d') = ? OR ? IS NULL)",
                (rs, rowNum) ->
                        new ScheduleResponseDto(rs.getLong("id"), rs.getString("name"), rs.getString("schedule"), rs.getObject("modify_date", LocalDateTime.class)),
                name, name, date, date);
    }

    @Override
    public Schedule findScheduleById(Long id) {
        return jdbcTemplate.query("select * from schedules where id = ?", (rs, rowNum) -> new Schedule (
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("pw"),
                rs.getString("schedule"),
                rs.getObject("enroll_date", LocalDateTime.class),
                rs.getObject("modify_date", LocalDateTime.class)
        ), id).stream().findAny().orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }


    @Override
    public int updateScheduleById(Long id, String name, String schedule, LocalDateTime dateTime) {
        return jdbcTemplate.update("update schedules set name = ?, schedule = ?, modify_date = ? where id = ?", name, schedule, dateTime, id);
    }


    @Override
    public int deleteScheduleById(Long id) {
        return jdbcTemplate.update("delete from schedules where id = ?", id);
    }

}
