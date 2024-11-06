package com.example.ScheduleManagementApp.writers.repository;


import com.example.ScheduleManagementApp.writers.dto.WriterResponseDto;

import java.time.LocalDateTime;

public interface WriterRepository {
    WriterResponseDto saveWriter(String name, String email, String pw, LocalDateTime createDateTime);

    WriterResponseDto findWriterById(Long id);

    Integer findWriterByNameAndEmail(String name, String email);

    String findWriterPassWordById(Long id);

    int updateWriterById(Long id, String name, LocalDateTime modifyDate);

    int deleteWriterById(Long id);
}
