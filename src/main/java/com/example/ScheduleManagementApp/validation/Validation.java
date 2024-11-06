package com.example.ScheduleManagementApp.validation;

import com.example.ScheduleManagementApp.exception.InputZeroException;
import com.example.ScheduleManagementApp.exception.NotExistIdException;
import com.example.ScheduleManagementApp.exception.NotMatchPwException;
import com.example.ScheduleManagementApp.exception.ValueNullException;
import com.example.ScheduleManagementApp.repository.ScheduleRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
@Component
public class Validation {
    public void ValidatePw(ScheduleRepository scheduleRepository, Long id, String pw){
        if(!Objects.equals(scheduleRepository.findSchedulePassWordById(id), pw)){
            throw new NotMatchPwException("잘못된 비밀번호입니다.");
        }
    }

    public void ValidateNotFoundId(int row, Long id){
        if (row == 0){
            throw new NotExistIdException("[id = " + id + "] 에 해당하는 정보가 존재하지 않습니다.");
        }
    }

    public void ValidateNotNull(Object object){
        if (object == null){
            throw new ValueNullException("잘못된 값(null)입니다.");
        }
    }

    public void ValidateZero(int number){
        if (number == 0){
            throw new InputZeroException("0은 잘못된 입력값 입니다.");
        }
    }

}
