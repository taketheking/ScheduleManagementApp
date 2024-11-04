package com.example.ScheduleManagementApp.repository;


import com.example.ScheduleManagementApp.dto.ScheduleResponseDto;
import com.example.ScheduleManagementApp.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(String name, String pw, String schedule, LocalDateTime localDateTime);

    List<ScheduleResponseDto> findAllSchedule(String name, String date);

    Schedule findScheduleById(Long id);

    int updateScheduleById(Long id, String name, String schedule, LocalDateTime modifyDate);

    int deleteScheduleById(Long id);
}
