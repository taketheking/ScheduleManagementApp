package com.example.ScheduleManagementApp.schedules.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class ScheduleCreateRequestDto {

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
