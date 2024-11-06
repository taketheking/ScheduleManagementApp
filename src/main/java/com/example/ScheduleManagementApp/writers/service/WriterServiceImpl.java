package com.example.ScheduleManagementApp.writers.service;

import com.example.ScheduleManagementApp.validation.Validation;
import com.example.ScheduleManagementApp.writers.dto.WriterCreateRequestDto;
import com.example.ScheduleManagementApp.writers.dto.WriterDeleteRequestDto;
import com.example.ScheduleManagementApp.writers.dto.WriterResponseDto;
import com.example.ScheduleManagementApp.writers.dto.WriterUpdateRequestDto;
import com.example.ScheduleManagementApp.writers.repository.WriterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class WriterServiceImpl implements WriterService {

    Validation validation;

    WriterRepository writerRepository;

    WriterServiceImpl(WriterRepository writerRepository, Validation validation) {
        this.writerRepository = writerRepository;
        this.validation = validation;
    }

    @Override
    public WriterResponseDto createWriter(WriterCreateRequestDto requestDto) {

        // 데이터베이스에서 일정 생성
        return writerRepository.saveWriter(requestDto.getName(), requestDto.getEmail(), requestDto.getPw(), getCurrentTime());
    }

    @Override
    public WriterResponseDto findWriterById(Long id) {

        // 단건 조회하기
        return writerRepository.findWriterById(id);
    }

    @Override
    public WriterResponseDto updateWriterById(Long id, WriterUpdateRequestDto writerUpdateRequestDto) {

        // pw 확인 검사
        //validation.ValidatePw(writerRepository, id, writerUpdateRequestDto.getPw());

        // 데이터베이스 일정 수정
        int updateRow = writerRepository.updateWriterById(id, writerUpdateRequestDto.getName(), getCurrentTime());

        // id 확인 검사
        validation.ValidateNotFoundId(updateRow, id);

        // 수정한 데이터 가져오기
        return writerRepository.findWriterById(id);

    }

    @Transactional
    @Override
    public void deleteWriterById(Long id, WriterDeleteRequestDto writerDeleteRequestDto) {
        // pw 확인 검사
        //validation.ValidatePw(writerRepository, id, writerDeleteRequestDto.getPw());

        // 데이터베이스에서 일정 삭제
        int deleteRow = writerRepository.deleteWriterById(id);

        // id 확인 검사
        validation.ValidateNotFoundId(deleteRow, id);
    }

    // 현재 시각 가져오기
    public LocalDateTime getCurrentTime(){

        return LocalDateTime.now(ZoneId.of("Asia/Seoul")).withNano(0);
    }


}
