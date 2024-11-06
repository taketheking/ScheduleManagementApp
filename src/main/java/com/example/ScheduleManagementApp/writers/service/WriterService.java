package com.example.ScheduleManagementApp.writers.service;

import com.example.ScheduleManagementApp.writers.dto.WriterCreateRequestDto;
import com.example.ScheduleManagementApp.writers.dto.WriterDeleteRequestDto;
import com.example.ScheduleManagementApp.writers.dto.WriterResponseDto;
import com.example.ScheduleManagementApp.writers.dto.WriterUpdateRequestDto;


public interface WriterService {
    WriterResponseDto createWriter(WriterCreateRequestDto writerCreateRequestDto);

    WriterResponseDto findWriterById(Long id);

    WriterResponseDto updateWriterById(Long id, WriterUpdateRequestDto writerUpdateRequestDto);

    void deleteWriterById(Long id, WriterDeleteRequestDto writerDeleteRequestDto);
}
