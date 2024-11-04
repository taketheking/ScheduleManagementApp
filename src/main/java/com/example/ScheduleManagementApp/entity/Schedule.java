package com.example.ScheduleManagementApp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
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


    public Schedule(String name, String pw, String schedule, LocalDateTime enrollDate, LocalDateTime modifyDate) {
        this.name = name;
        this.pw = pw;
        this.schedule = schedule;
        this.enrollDate = enrollDate;
        this.modifyDate = modifyDate;
    }

}
