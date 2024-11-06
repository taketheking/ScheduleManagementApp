package com.example.ScheduleManagementApp.schedules.service;


import com.example.ScheduleManagementApp.schedules.dto.ScheduleDeleteRequestDto;
import com.example.ScheduleManagementApp.schedules.dto.ScheduleCreateRequestDto;
import com.example.ScheduleManagementApp.schedules.dto.ScheduleResponseDto;
import com.example.ScheduleManagementApp.schedules.dto.ScheduleUpdateRequestDto;

import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto createSchedule(ScheduleCreateRequestDto scheduleCreateRequestDto);

    List<ScheduleResponseDto> findAllSchedule(String name, String date, Integer page, Integer size);

    ScheduleResponseDto findScheduleById(Long id);

    ScheduleResponseDto updateScheduleById(Long id, ScheduleUpdateRequestDto scheduleUpdateRequestDto);

    void deleteScheduleById(Long id, ScheduleDeleteRequestDto scheduleDeleteRequestDto);
}
