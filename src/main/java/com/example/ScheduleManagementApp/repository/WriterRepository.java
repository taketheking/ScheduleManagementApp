package com.example.ScheduleManagementApp.repository;

import java.time.LocalDateTime;

public interface WriterRepository {
    Long saveWriter(String name, String email, LocalDateTime createDateTime);

    Number findScheduleByNameAndEmail(String name, String email);

    int updateWriterById(Long id, String name, LocalDateTime modifyDateTime);
}
