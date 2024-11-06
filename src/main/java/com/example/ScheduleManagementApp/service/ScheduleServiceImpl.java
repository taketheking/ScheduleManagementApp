package com.example.ScheduleManagementApp.service;

import com.example.ScheduleManagementApp.dto.ScheduleDeleteRequestDto;
import com.example.ScheduleManagementApp.dto.ScheduleCreateRequestDto;
import com.example.ScheduleManagementApp.dto.ScheduleResponseDto;
import com.example.ScheduleManagementApp.dto.ScheduleUpdateRequestDto;
import com.example.ScheduleManagementApp.entity.Schedule;
import com.example.ScheduleManagementApp.repository.ScheduleRepository;
import com.example.ScheduleManagementApp.repository.WriterRepository;
import com.example.ScheduleManagementApp.validation.Validation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
@Service
public class ScheduleServiceImpl implements ScheduleService {

    Validation validation;

    ScheduleRepository scheduleRepository;

    WriterRepository writerRepository;

    ScheduleServiceImpl(WriterRepository writerRepository, ScheduleRepository scheduleRepository, Validation validation) {
        this.writerRepository = writerRepository;
        this.scheduleRepository = scheduleRepository;
        this.validation = validation;
    }

    @Transactional
    @Override
    public ScheduleResponseDto createSchedule(ScheduleCreateRequestDto requestDto) {
        // 생성 시점 생성
        LocalDateTime createDateTime = getCurrentTime();

        String name = requestDto.getName();

        // 작성자 존재 유무 판단
        Number writerId = writerRepository.findScheduleByNameAndEmail(name, requestDto.getEmail());
        if(writerId == null){
            // 작성자 존재하지 않을 시에 새로 생성
           writerId = writerRepository.saveWriter(name, requestDto.getEmail(), createDateTime);
        }
        // 일정 생성
        Schedule schedule = scheduleRepository.saveSchedule(requestDto.getPw(), requestDto.getSchedule(), createDateTime, writerId.longValue());

        return new ScheduleResponseDto(schedule.getId(), name, schedule.getSchedule(), schedule.getModifyDate());
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule(String name, String date, Integer page, Integer size) {

        // 페이지네이션 0값 체크 - 예외처리
        validation.ValidateZero(page);
        validation.ValidateZero(size);

        // 전체 조회하기
        return scheduleRepository.findAllSchedule(name, date, page, size);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {

        // 단건 조회하기
        return scheduleRepository.findScheduleById(id);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateScheduleAndWriterById(Long id, ScheduleUpdateRequestDto scheduleUpdateRequestDto) {

        // pw 확인 검사
        validation.ValidatePw(scheduleRepository, id, scheduleUpdateRequestDto.getPw());

        // 일정 수정
        int updateRow = scheduleRepository.updateScheduleById(id, scheduleUpdateRequestDto.getName(), scheduleUpdateRequestDto.getSchedule(), getCurrentTime());

        // 일정 id 예외 처리
        validation.ValidateNotFoundId(updateRow, id);

        // 작성자 id 가져오기
        Long writer_id = scheduleRepository.getWriterIdByScheduleId(id);

        // null 체크
        validation.ValidateNotNull(writer_id);

        // 작성자명 수정
        int updateWriterNameRow = writerRepository.updateWriterById(writer_id, scheduleUpdateRequestDto.getName(), getCurrentTime());

        // 작성자 id 예외 처리
        validation.ValidateNotFoundId(updateWriterNameRow, writer_id);

        // 수정한 데이터 가져오기
        return scheduleRepository.findScheduleById(id);
    }

    @Override
    public void deleteScheduleById(Long id, ScheduleDeleteRequestDto scheduleDeleteRequestDto) {

        // pw 확인 검사
        validation.ValidatePw(scheduleRepository, id, scheduleDeleteRequestDto.getPw());

        // 데이터베이스에서 일정 삭제
        int deleteRow = scheduleRepository.deleteScheduleById(id);

        // id 예외 처리
        validation.ValidateNotFoundId(deleteRow, id);
    }

    public LocalDateTime getCurrentTime(){

        return LocalDateTime.now(ZoneId.of("Asia/Seoul")).withNano(0);
    }


}
