package com.example.ScheduleManagementApp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@AllArgsConstructor
public class ExceptionResponse {
    private LocalDateTime dateTime;
    private HttpStatus httpStatus;
    private int statusCode;
    private Map<String, String> errors;

}
