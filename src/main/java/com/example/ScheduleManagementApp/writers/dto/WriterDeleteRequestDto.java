package com.example.ScheduleManagementApp.writers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class WriterDeleteRequestDto {

    @NotBlank
    @Size(min = 4, max = 40)
    String pw;
}
