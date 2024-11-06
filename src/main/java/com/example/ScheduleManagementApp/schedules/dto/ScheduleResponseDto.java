package com.example.ScheduleManagementApp.schedules.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long id;

    private String name;

    private String schedule;

    private LocalDateTime modifyDate;

}
