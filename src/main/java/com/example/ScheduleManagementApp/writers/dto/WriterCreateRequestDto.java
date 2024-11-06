package com.example.ScheduleManagementApp.writers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class WriterCreateRequestDto {

    @NotBlank
    @Size(min = 1, max = 40)
    String name;

    @NotBlank
    @Email
    @Size(max = 40)
    String email;

    @NotBlank
    @Size(min = 4, max = 40)
    String pw;

}
