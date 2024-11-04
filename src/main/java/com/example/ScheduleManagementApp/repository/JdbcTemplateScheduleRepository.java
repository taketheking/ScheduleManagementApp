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
    public ScheduleResponseDto saveSchedule(String pw, String schedule, LocalDateTime localDateTime, Long writerId) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        jdbcInsert.withTableName("schedules").usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();

        params.put("pw", pw);
        params.put("schedule", schedule);
        params.put("enroll_date", localDateTime);
        params.put("modify_date", localDateTime);
        params.put("writer_id", writerId);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));

        return new ScheduleResponseDto(key.longValue(), findWriterNameById(writerId), schedule, localDateTime);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule(Long writerId, String date, Integer page, Integer size) {

        return jdbcTemplate.query("select * from schedules where (writer_id = ? OR ? IS NULL) AND (DATE_FORMAT(modify_date, '%Y-%m-%d') = ? OR ? IS NULL) ORDER BY modify_date DESC LIMIT ? OFFSET ?",
                (rs, rowNum) ->
                        new ScheduleResponseDto(rs.getLong("id"), findWriterNameById(rs.getLong("writer_id")), rs.getString("schedule"), rs.getObject("modify_date", LocalDateTime.class)),
                writerId, writerId, date, date, size, (page-1)*size);
    }

    @Override
    public Schedule findScheduleById(Long id) {
        return jdbcTemplate.query("select * from schedules where id = ?", (rs, rowNum) -> new Schedule (
                rs.getLong("id"),
                findWriterNameById(rs.getLong("writer_id")),
                rs.getString("pw"),
                rs.getString("schedule"),
                rs.getObject("enroll_date", LocalDateTime.class),
                rs.getObject("modify_date", LocalDateTime.class),
                rs.getLong("writer_id")
        ), id).stream().findAny().orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }


    @Override
    public int updateScheduleById(Long id, String schedule, LocalDateTime dateTime) {
        return jdbcTemplate.update("update schedules set schedule = ?, modify_date = ? where id = ?", schedule, dateTime, id);
    }


    @Override
    public int deleteScheduleById(Long id) {
        return jdbcTemplate.update("delete from schedules where id = ?", id);
    }

    public String findWriterNameById(Long writerId) {
        return jdbcTemplate.query("select * from writers where id = ?", (rs, rowNum) ->
                        rs.getString("name"), writerId).stream().findFirst()
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + writerId));
    }

}
