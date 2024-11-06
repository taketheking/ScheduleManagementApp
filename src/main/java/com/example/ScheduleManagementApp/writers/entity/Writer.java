package com.example.ScheduleManagementApp.writers.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Writer {

    private Long id;

    private String name;

    private String email;

    private String pw;

    private LocalDateTime enrollDate;

    private LocalDateTime modifyDate;
}
