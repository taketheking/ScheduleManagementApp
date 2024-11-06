package com.example.ScheduleManagementApp.repository;


import com.example.ScheduleManagementApp.dto.ScheduleResponseDto;
import com.example.ScheduleManagementApp.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository {

    Schedule saveSchedule(String pw, String schedule, LocalDateTime createDateTime, Long writerId);

    List<ScheduleResponseDto> findAllSchedule(String name, String date, Integer page, Integer size);

    ScheduleResponseDto findScheduleById(Long id);

    String findSchedulePassWordById(Long id);

    Long getWriterIdByScheduleId(Long id);

    int updateScheduleById(Long id, String name, String schedule, LocalDateTime modifyDate);

    int deleteScheduleById(Long id);
}
