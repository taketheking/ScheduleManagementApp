package com.example.ScheduleManagementApp.dto;

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

}
