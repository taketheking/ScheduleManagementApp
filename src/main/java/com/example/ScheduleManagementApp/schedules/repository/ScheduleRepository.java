package com.example.ScheduleManagementApp.schedules.repository;


import com.example.ScheduleManagementApp.schedules.dto.ScheduleResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(String name,  String pw, String schedule, LocalDateTime createDateTime);

    List<ScheduleResponseDto> findAllSchedule(String name, String date, Integer page, Integer size);

    ScheduleResponseDto findScheduleById(Long id);

    Integer findScheduleByNameAndEmail(String name, String email);

    String findSchedulePassWordById(Long id);

    int updateScheduleById(Long id, String name, String schedule, LocalDateTime modifyDate);

    int deleteScheduleById(Long id);
}
