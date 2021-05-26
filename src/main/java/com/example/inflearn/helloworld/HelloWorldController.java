package com.example.inflearn.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    // GET방식
    // /hello-world (endPoint) URL주소창에 쓰여질 내용
    // @RequsetMapping(method=RequestMethod.GET, path="/hello-world") 요즘 잘 안씀
    @GetMapping(path = "/hello-world")
    public String helloworld() {
        return "Hello World";
    }

    // alt + enter(오류에 대한 해답을 알려줌)
    // restController를 사용하면 반환시키고자 하는 데이터값을 ResponseBody에 저장하지 않아도 JSON형태로 리턴한다
    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World");
    }

    @GetMapping(path = "/hello-world-bean/path-variable/{name}")  // 뒤에 가변데이터
    public HelloWorldBean helloWorldBean(@PathVariable String name) { //@PathVariable 이 변수({name}, String name)가 가변데이터로 사용될것이다라는 어노테이션
        return new HelloWorldBean(String.format("Hello World, %s", name)); // %s는 뒤에 가변데이터가 올수 있다. %s자리에 name의 값이 들어감
    }

}

// application.properties -> 설정이름 = 값
// application.yml -> 설정이름 : 값
