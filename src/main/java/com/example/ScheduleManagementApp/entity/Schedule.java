package com.example.ScheduleManagementApp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

    private Long id;

    private String name;

    private String pw;

    private String schedule;

    private LocalDateTime enrollDate;

    private LocalDateTime modifyDate;

    private Long writerId;

}
