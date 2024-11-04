package com.example.ScheduleManagementApp.dto;

import com.example.ScheduleManagementApp.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    Long id;

    String name;

    String schedule;

    LocalDateTime modifyDate;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();

        this.name = schedule.getName();

        this.schedule = schedule.getSchedule();

        this.modifyDate = schedule.getModifyDate();
    }
}
