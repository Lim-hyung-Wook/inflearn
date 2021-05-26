package com.example.inflearn.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//HTTP Status Code
//2XX -> OK
//4XX -> Client 존재하지 않는 리소스를 요청, 권한이 없던가, 제공하지 않는 메소드를 호출하던가
//5XX -> Server 서버가 리소스랑 연결되지 않던가
@ResponseStatus(HttpStatus.NOT_FOUND)  //500번에러가 아니라 404번 오류로, 데이터가 존재하지않는 오류로
public class UserNotFoundException extends RuntimeException {  // RuntimeException 실행시 발생하는 오류
    public UserNotFoundException(String message) {
        super(message); // 부모쪽(Controller)으로 전달받은 메세지를 반환
    }
}
