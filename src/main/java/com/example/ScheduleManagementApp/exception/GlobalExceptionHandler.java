package com.example.ScheduleManagementApp.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // @valid 유효성 검사 예외처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        // 유효성 검사 오류 메시지를 필드별로 수집
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        // 에러 응답 생성
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), errors);

        log.error("[MethodArgumentNotValidException] : {}", exceptionResponse.getErrors());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


    // 제약조건 위반시 발생하는 예외처리
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
            errors.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        }

        // 에러 응답 생성
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), errors);

        log.error("[ConstraintViolationException] : {}", exceptionResponse.getErrors());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    // 잘못된 id 접근 예외처리
    @ExceptionHandler(NotExistIdException.class)
    public ResponseEntity<Object> handleIdNotExist(NotExistIdException ex) {
        // 오류 메세지 저장
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());

        // 에러 응답 생성
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), errors);

        log.error("[NotExistIdException]: {}", exceptionResponse.getErrors());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    // pw 불일치 예외처리
    // path Variable 변수의 타입과 다른 타입 값을 넣었을 때 예외처리
    // 그 외의 모든 예외처리
    @ExceptionHandler({NotMatchPwException.class, MethodArgumentTypeMismatchException.class, Exception.class})
    public ResponseEntity<Object> handleNotMatchPw(NotMatchPwException ex) {
        // 오류 메세지 저장
        Map<String, String> errors = new HashMap<>();
        errors.put("errors", ex.getMessage());

        // 에러 응답 생성
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), errors);

        log.error("[ {} ]: {}", ex.getClass(), exceptionResponse.getErrors());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
