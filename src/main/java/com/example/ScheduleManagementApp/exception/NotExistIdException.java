package com.example.ScheduleManagementApp.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotExistIdException extends RuntimeException {

    public NotExistIdException(String message) {
        super(message);
    }
}
