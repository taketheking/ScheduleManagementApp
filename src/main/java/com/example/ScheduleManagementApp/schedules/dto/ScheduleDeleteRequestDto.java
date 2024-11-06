package com.example.ScheduleManagementApp.schedules.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleDeleteRequestDto {

    @NotBlank
    @Size(min = 4, max = 40)
    String pw;
}
