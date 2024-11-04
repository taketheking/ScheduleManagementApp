package com.example.ScheduleManagementApp.service;

import com.example.ScheduleManagementApp.dto.ScheduleRequestDto;
import com.example.ScheduleManagementApp.dto.ScheduleResponseDto;
import com.example.ScheduleManagementApp.entity.Schedule;
import com.example.ScheduleManagementApp.repository.ScheduleRepository;
import com.example.ScheduleManagementApp.service.validation.Validation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
@Service
public class ScheduleServiceImpl implements ScheduleService {

    Validation validation;

    ScheduleRepository scheduleRepository;

    ScheduleServiceImpl(ScheduleRepository scheduleRepository, Validation validation) {
        this.scheduleRepository = scheduleRepository;
        this.validation = validation;
    }

    @Override
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        // 일정 생성 시점 가져오기
        LocalDateTime localDateTime = getCurrentTime();

        // 데이터베이스에서 일정 생성
        return scheduleRepository.saveSchedule(requestDto.getName(), requestDto.getPw(), requestDto.getSchedule(), localDateTime);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule(String name, String date) {
        // 필수 값 검증
//        validation.ValidateRequiredValues(name, date);

        return scheduleRepository.findAllSchedule(name, date);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {

        // 단건 조회하기
        Schedule schedule = scheduleRepository.findScheduleById(id);

        // response dto 에 담아 전달하기
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateScheduleById(Long id, ScheduleRequestDto scheduleRequestDto) {

        // 필수값 검증
        validation.ValidateRequiredValues(scheduleRequestDto.getName(), scheduleRequestDto.getSchedule());

        // pw 확인 검사
        validation.ValidatePw(scheduleRepository, id, scheduleRequestDto.getPw());

        // 데이터베이스 일정 수정
        int updateRow = scheduleRepository.updateScheduleById(id, scheduleRequestDto.getName(), scheduleRequestDto.getSchedule(), getCurrentTime());

        // id 확인 검사
        validation.ValidateNotFoundId(updateRow, id);

        // 수정한 데이터 가져오기
        Schedule schedule = scheduleRepository.findScheduleById(id);

        // response dto 에 담아 전달하기
        return new ScheduleResponseDto(schedule);

    }

    @Override
    public void deleteScheduleById(Long id, ScheduleRequestDto scheduleRequestDto) {
        // pw 확인 검사
        validation.ValidatePw(scheduleRepository, id, scheduleRequestDto.getPw());

        // 데이터베이스에서 일정 삭제
        int deleteRow = scheduleRepository.deleteScheduleById(id);

        // id 확인 검사
        validation.ValidateNotFoundId(deleteRow, id);
    }


    public LocalDateTime getCurrentTime(){

        return LocalDateTime.now(ZoneId.of("Asia/Seoul")).withNano(0);
    }
}
