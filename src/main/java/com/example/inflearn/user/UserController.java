package com.example.inflearn.user;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUser() {
        return service.findAll();  //findALl이라는 함수가 List형식이라 리턴 가능

    }

    //GET /users/1 or / users/10 ->String
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {  //가변변수를 사용할수 있다 (@PathVariable)
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id)); //throw new 에러를 보낸다는 느낌
        }

        //HATEOAS
        EntityModel<User> model = new EntityModel<>(user);
//        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUser());
        WebMvcLinkBuilder linkTo =linkTo(methodOn(this.getClass()).retrieveAllUser());  //import static로 짧게 연결
        model.add(linkTo.withRel("all-users")); //리소스 객체에 링크를 추가 ""URI랑 연결  retrieveAllUser의 메소드 이름과 "all-users"를 연결 hypermedia로 연결

        return model;
//        return service.findOne(id); 에서 findOne ctrl + alt + v 누르면 코드가 분리

    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) { //@RequestBody JSON이나 XML같은 오브젝트 형태의 데이터를 받아올때 사용
        User savedUser = service.save(user); // 서비스에 user 객체를 전달

        // 반환시키기위해 도메인으로 지정한 user값을 반환
        URI location = ServletUriComponentsBuilder.fromCurrentRequest() //사용자에게 요청값을 변환해주기위해 사용(ServletUriComponentsBuilder)현재가지고있는 리퀘스트 값을 사용하기위해(fromCurrentRequest())
                .path("/{id}") //반환시주고자할떄 PATH값 id로 지정
                .buildAndExpand(savedUser.getId()) //세이브되어진 유저값과아이디를 이용해서 가변변수에 새로 만들어진 아이디값을 지정
                .toUri(); //모든 형태를 URI형태로 변경
        //반환값을 URI location

        //responseEntitiy가 header, status, body를 가지고 넘기는데 location이 URI형태이기떄문에 void에서 responseEntitiy로 바꿈

        return ResponseEntity.created(location).build(); //build해서 반환
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {

        User user = service.deleteById(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }

    @PutMapping("/users/{id}")
    public void putUser(){

    }


}


// ResponseEntity<User>