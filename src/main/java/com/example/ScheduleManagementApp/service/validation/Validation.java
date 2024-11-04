package com.example.ScheduleManagementApp.service.validation;

import com.example.ScheduleManagementApp.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Component
public class Validation {
    public void ValidatePw(ScheduleRepository scheduleRepository, Long id, String pw){
        if(!Objects.equals(scheduleRepository.findScheduleById(id).getPw(), pw)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong password");
        }
    }

    public void ValidateRequiredValues(String valueFirst, String valueSecond) {
        // 필수값 검증
        if (valueFirst == null || valueSecond == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The name and schedule are required values.");
        }
    }

    public void ValidateNotFoundId(int row, Long id){
        if (row == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found by id: " + id);
        }
    }
}
