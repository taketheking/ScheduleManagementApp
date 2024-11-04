package com.example.ScheduleManagementApp.controller;

import com.example.ScheduleManagementApp.dto.ScheduleRequestDto;
import com.example.ScheduleManagementApp.dto.ScheduleResponseDto;
import com.example.ScheduleManagementApp.service.ScheduleService;
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
     * @param scheduleRequestDto : {@link ScheduleRequestDto} 할일 생성 요청 객체
     * @return : {@link ResponseEntity<ScheduleResponseDto>} JSON 응답
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {

        return new ResponseEntity<>(scheduleService.createSchedule(scheduleRequestDto), HttpStatus.CREATED);
    }

    /**
     * 할일 전체 목록 조회 API
     * @param writerId 작성자명
     * @param date 최종 수정일
     * @return : {@link List<ScheduleResponseDto>} JSON 응답
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) Long writerId,
            @RequestParam(required = false)  String date) {
        return new ResponseEntity<>(scheduleService.findAllSchedule(writerId, date, page, size), HttpStatus.OK);
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
     * @param scheduleRequestDto {@link ScheduleRequestDto} 할일 생성 요청 객체
     * @return : {@link ResponseEntity<ScheduleResponseDto>} JSON 응답
     */
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto scheduleRequestDto) {
        return new ResponseEntity<>(scheduleService.updateScheduleById(id, scheduleRequestDto), HttpStatus.OK);
    }

    /**
     * 할일 단건 삭제 API
     * @param  id 식별자
     * @param scheduleRequestDto 비밀번호
     * @return : {@link ResponseEntity<Void>} JSON 응답
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto scheduleRequestDto) {

        scheduleService.deleteScheduleById(id, scheduleRequestDto);

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
