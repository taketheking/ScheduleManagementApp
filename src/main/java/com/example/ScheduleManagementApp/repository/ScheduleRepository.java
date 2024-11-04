package com.example.ScheduleManagementApp.repository;


import com.example.ScheduleManagementApp.dto.ScheduleResponseDto;
import com.example.ScheduleManagementApp.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(String pw, String schedule, LocalDateTime localDateTime, Long writerId);

    List<ScheduleResponseDto> findAllSchedule(Long writerId, String date, Integer page, Integer size);

    Schedule findScheduleById(Long id);

    int updateScheduleById(Long id, String schedule, LocalDateTime modifyDate);

    int deleteScheduleById(Long id);
}
