package com.example.ScheduleManagementApp.controller;

import com.example.ScheduleManagementApp.dto.ScheduleDeleteRequestDto;
import com.example.ScheduleManagementApp.dto.ScheduleCreateRequestDto;
import com.example.ScheduleManagementApp.dto.ScheduleResponseDto;
import com.example.ScheduleManagementApp.dto.ScheduleUpdateRequestDto;
import com.example.ScheduleManagementApp.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    ScheduleService scheduleService;

    ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * 할일 생성 API
     * @param scheduleCreateRequestDto : {@link ScheduleCreateRequestDto} 할일 생성 요청 객체
     * @return : {@link ResponseEntity<ScheduleResponseDto>} JSON 응답
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody @Valid ScheduleCreateRequestDto scheduleCreateRequestDto) {

        return new ResponseEntity<>(scheduleService.createSchedule(scheduleCreateRequestDto), HttpStatus.CREATED);
    }

    /**
     * 할일 전체 목록 조회 API
     * @param name 작성자명
     * @param date 최종 수정일
     * @param page 현재 페이지
     * @param size 한 페이지 당 일정 개수
     * @return : {@link List<ScheduleResponseDto>} JSON 응답
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false)  String date) {
        return new ResponseEntity<>(scheduleService.findAllSchedule(name, date, page, size), HttpStatus.OK);
    }

    /**
     * 할일 단건 조회 API
     * @param  id 식별자
     * @return : {@link ResponseEntity<ScheduleResponseDto>} JSON 응답
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {

        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    /**
     * 할일 단건 전체 수정 API
     * @param  id 식별자
     * @param scheduleUpdateRequestDto {@link ScheduleCreateRequestDto} 할일 생성 요청 객체
     * @return : {@link ResponseEntity<ScheduleResponseDto>} JSON 응답
     */
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @RequestBody @Valid ScheduleUpdateRequestDto scheduleUpdateRequestDto) {
        return new ResponseEntity<>(scheduleService.updateScheduleAndWriterById(id, scheduleUpdateRequestDto), HttpStatus.OK);
    }

    /**
     * 할일 단건 삭제 API
     * @param  id 식별자
     * @param scheduleDeleteRequestDto 비밀번호
     * @return : {@link ResponseEntity<Void>} JSON 응답
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, @RequestBody @Valid ScheduleDeleteRequestDto scheduleDeleteRequestDto) {

        scheduleService.deleteScheduleById(id, scheduleDeleteRequestDto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
