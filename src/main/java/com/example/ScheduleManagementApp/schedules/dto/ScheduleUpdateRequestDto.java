package com.example.ScheduleManagementApp.schedules.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleUpdateRequestDto {

    @NotBlank
    @Size(min = 1, max = 40)
    String name;

    @NotBlank
    @Size(min = 4, max = 40)
    String pw;

    @NotEmpty
    @Size(max = 200)
    String schedule;
}
