package com.example.inflearn.exception;

import com.example.inflearn.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice //모든 컨트롤러가 실행될때 반드시 지금 선언한 어노테이션을 가진 bean이 먼저 실행한다.
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class) // 이 메소드가 exception핸들러로 사용될수 있게 , Exception.class: 어떤 에러를 처리할 것인지
    public final ResponseEntity<Object> handlerAllExceptions(Exception ex, WebRequest request) {  // WebRequest request 어디서 발생했는지 request의 대한 정보를 파라미터로 받아온다
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
                                                                   // 언제 발생햇는지 , 메세지 객체, 어떤 정보를 가지고있는지

        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR); //ResponseEntity 오브젝트 형태있기때문에 제네릭은 노 필요 , exceptionResponse객체를 포함
    }

    @ExceptionHandler(UserNotFoundException.class) //UserNotFoundException이 발생하게 되면 handleUserNotFoundException이 실행되게 된다.
    public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override  //Validation 체크하기위한 메소드
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Validation Failed", ex.getBindingResult().toString());

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
