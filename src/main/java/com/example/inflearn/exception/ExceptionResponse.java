package com.example.inflearn.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor // 모든 매개변수를 가지고 있는 생성자
@NoArgsConstructor // 매개변수가 없는 생성자
public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private String details;
}

//사용하고 있는 시스템에서 에러가 발생하면 에러를 핸들링하기 위한 클래스 예외가발생하면 해당 핸들러가 실행