package com.example.inflearn.helloworld;

import lombok.AllArgsConstructor;
import lombok.Data;

//lombok 을 사용하면 메소드를 자동으로 생성해준다 개발시간을 단축시켜준다
@Data
@AllArgsConstructor   //@AllArgsConstructor는 모든 아규먼트를 가지고있는 컨스트럭쳐가 만들어진다.
// message의 생성자를 만들수 있다
public class HelloWorldBean {
    private String message;

//    public HelloWorldBean(String message) {
//        this.message = message;
//    } //@AllArgsConstructor로 이거 만들어줌
}
