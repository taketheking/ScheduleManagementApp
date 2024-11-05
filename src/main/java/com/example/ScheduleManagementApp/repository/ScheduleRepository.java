package com.example.ScheduleManagementApp.repository;


import com.example.ScheduleManagementApp.dto.ScheduleResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(String name, String email, String pw, String schedule, LocalDateTime createDateTime);

    List<ScheduleResponseDto> findAllSchedule(String name, String date, Integer page, Integer size);

    ScheduleResponseDto findScheduleById(Long id);

    String findSchedulePassWordById(Long id);

    int updateScheduleById(Long id, String name, String schedule, LocalDateTime modifyDate);

    int deleteScheduleById(Long id);
}
