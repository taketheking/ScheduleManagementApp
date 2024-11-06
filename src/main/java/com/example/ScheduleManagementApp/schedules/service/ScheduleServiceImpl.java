package com.example.ScheduleManagementApp.schedules.service;

import com.example.ScheduleManagementApp.schedules.dto.ScheduleDeleteRequestDto;
import com.example.ScheduleManagementApp.schedules.dto.ScheduleCreateRequestDto;
import com.example.ScheduleManagementApp.schedules.dto.ScheduleResponseDto;
import com.example.ScheduleManagementApp.schedules.dto.ScheduleUpdateRequestDto;
import com.example.ScheduleManagementApp.schedules.repository.ScheduleRepository;
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

    ScheduleServiceImpl(ScheduleRepository scheduleRepository, Validation validation) {
        this.scheduleRepository = scheduleRepository;
        this.validation = validation;
    }

    @Override
    public ScheduleResponseDto createSchedule(ScheduleCreateRequestDto requestDto) {
        // 일정 생성 시점 가져오기
        LocalDateTime localDateTime = getCurrentTime();

        // 데이터베이스에서 일정 생성
        return scheduleRepository.saveSchedule(requestDto.getName(), requestDto.getPw(), requestDto.getSchedule(), localDateTime);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule(String name, String date, Integer page, Integer size) {

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
    public ScheduleResponseDto updateScheduleById(Long id, ScheduleUpdateRequestDto scheduleUpdateRequestDto) {

        // pw 확인 검사
        validation.ValidatePw(scheduleRepository, id, scheduleUpdateRequestDto.getPw());

        // 데이터베이스 일정 수정
        int updateRow = scheduleRepository.updateScheduleById(id, scheduleUpdateRequestDto.getName(), scheduleUpdateRequestDto.getSchedule(), getCurrentTime());

        // id 확인 검사
        validation.ValidateNotFoundId(updateRow, id);

        // 수정한 데이터 가져오기
        return scheduleRepository.findScheduleById(id);

    }

    @Transactional
    @Override
    public void deleteScheduleById(Long id, ScheduleDeleteRequestDto scheduleDeleteRequestDto) {
        // pw 확인 검사
        validation.ValidatePw(scheduleRepository, id, scheduleDeleteRequestDto.getPw());

        // 데이터베이스에서 일정 삭제
        int deleteRow = scheduleRepository.deleteScheduleById(id);

        // id 확인 검사
        validation.ValidateNotFoundId(deleteRow, id);
    }

    public LocalDateTime getCurrentTime(){

        return LocalDateTime.now(ZoneId.of("Asia/Seoul")).withNano(0);
    }


}
