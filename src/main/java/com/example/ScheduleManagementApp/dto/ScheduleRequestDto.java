package com.example.ScheduleManagementApp.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {

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

    @NotNull
    @Size(max = 200)
    String schedule;

}
