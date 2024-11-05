package com.example.ScheduleManagementApp.service;


import com.example.ScheduleManagementApp.dto.ScheduleDeleteRequestDto;
import com.example.ScheduleManagementApp.dto.ScheduleRequestDto;
import com.example.ScheduleManagementApp.dto.ScheduleResponseDto;
import com.example.ScheduleManagementApp.dto.ScheduleUpdateRequestDto;

import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto);

    List<ScheduleResponseDto> findAllSchedule(String name, String date, Integer page, Integer size);

    ScheduleResponseDto findScheduleById(Long id);

    ScheduleResponseDto updateScheduleById(Long id, ScheduleUpdateRequestDto scheduleUpdateRequestDto);

    void deleteScheduleById(Long id, ScheduleDeleteRequestDto scheduleDeleteRequestDto);
}
