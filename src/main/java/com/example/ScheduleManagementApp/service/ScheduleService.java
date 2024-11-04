package com.example.ScheduleManagementApp.service;


import com.example.ScheduleManagementApp.dto.ScheduleRequestDto;
import com.example.ScheduleManagementApp.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto createSchedule(ScheduleRequestDto schedule);

    List<ScheduleResponseDto> findAllSchedule(String name, String date);

    ScheduleResponseDto findScheduleById(Long id);

    ScheduleResponseDto updateScheduleById(Long id, ScheduleRequestDto scheduleRequestDto);

    void deleteScheduleById(Long id, ScheduleRequestDto scheduleRequestDto);
}