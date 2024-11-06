package com.example.ScheduleManagementApp.writers.controller;

import com.example.ScheduleManagementApp.writers.dto.WriterCreateRequestDto;
import com.example.ScheduleManagementApp.writers.dto.WriterDeleteRequestDto;
import com.example.ScheduleManagementApp.writers.dto.WriterResponseDto;
import com.example.ScheduleManagementApp.writers.dto.WriterUpdateRequestDto;
import com.example.ScheduleManagementApp.writers.service.WriterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/writers")
public class WriterController {

    WriterService writerService;

    WriterController(WriterService writerService) {
        this.writerService = writerService;
    }

    /**
     * 작성자 생성 API
     * @param writerCreateRequestDto : {@link WriterCreateRequestDto} 작성자 생성 요청 객체
     * @return : {@link ResponseEntity<WriterResponseDto>} JSON 응답
     */
    @PostMapping
    public ResponseEntity<WriterResponseDto> createWriter(@RequestBody @Valid WriterCreateRequestDto writerCreateRequestDto) {

        return new ResponseEntity<>(writerService.createWriter(writerCreateRequestDto), HttpStatus.CREATED);
    }

    /**
     * 작성자 조회 API
     * @param  id 식별자
     * @return : {@link ResponseEntity<WriterResponseDto>} JSON 응답
     */
    @GetMapping("/{id}")
    public ResponseEntity<WriterResponseDto> findWriterById(@PathVariable Long id) {

        return new ResponseEntity<>(writerService.findWriterById(id), HttpStatus.OK);
    }

    /**
     * 작성자 수정 API
     * @param  id 식별자
     * @param writerUpdateRequestDto {@link WriterCreateRequestDto} 작성자 생성 요청 객체
     * @return : {@link ResponseEntity<WriterResponseDto>} JSON 응답
     */
    @PutMapping("/{id}")
    public ResponseEntity<WriterResponseDto> updateWriter(@PathVariable Long id, @RequestBody @Valid WriterUpdateRequestDto writerUpdateRequestDto) {
        return new ResponseEntity<>(writerService.updateWriterById(id, writerUpdateRequestDto), HttpStatus.OK);
    }

    /**
     * 작성자 삭제 API
     * @param  id 식별자
     * @param writerDeleteRequestDto 비밀번호
     * @return : {@link ResponseEntity<Void>} JSON 응답
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWriter(@PathVariable Long id, @RequestBody @Valid WriterDeleteRequestDto writerDeleteRequestDto) {

        writerService.deleteWriterById(id, writerDeleteRequestDto);

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
