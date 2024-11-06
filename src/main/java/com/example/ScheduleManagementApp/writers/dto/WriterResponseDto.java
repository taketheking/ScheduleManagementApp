package com.example.ScheduleManagementApp.writers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class WriterResponseDto {

    private Long id;

    private String name;

    private String email;

    private LocalDateTime modifyDate;

}
