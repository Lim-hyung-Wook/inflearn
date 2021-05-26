package com.example.inflearn.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")  //공통 prefix url
public class UserJpaController {

  @Autowired
  public UserRepository userRepository;

  @Autowired
  public PostRepository postRepository;

  @GetMapping("/users")
  public List<User> retrieveAllUsers() {
    return userRepository.findAll();

  }

  @GetMapping("/users/{id}")
  public EntityModel<User> retrieveUser(@PathVariable int id) {
    Optional<User> user = userRepository.findById(id);  //findById의 리턴값은 Optional<T> findById(ID id);

    //데이터 null체크
    if (!user.isPresent()) {
      throw new UserNotFoundException(String.format("ID[%s} not found", id));
    }

    //헤테오스 기능
    EntityModel<User> userWebMvcLinkBuilder = new EntityModel(user.get());
    WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
    userWebMvcLinkBuilder.add(linkTo.withRel("all-Users"));

    return userWebMvcLinkBuilder; //유저가 가지고 있는 get메소드 사용
  }

  @DeleteMapping("/users/{id}")
  public void deleteUser(@PathVariable int id) {
    userRepository.deleteById(id);

  }

  @PostMapping("/users")
  public ResponseEntity<User> createUser(@RequestBody @Valid User user) { //유효성 검사와 사용자의 데이터를 받아옴
    User savedUser = userRepository.save(user); //save함수는 리턴값이 User 이다

    URI location = ServletUriComponentsBuilder.fromCurrentRequest() //생성된 데이터에 한해서 아이디값을 자동적으로 지정함
      .path("{id}")  //생성된 아이디 값을
      .buildAndExpand(savedUser.getId())  //저장되어진 객체에 아이디값을 매핑해서 헤더값으로 전달
      .toUri();  //uri로 전달

    return ResponseEntity.created(location).build();
  }

  @GetMapping("/users/{id}/posts")
  public List<Post> retrieveAllPostsByUser(@PathVariable int id) {
    Optional<User> user = userRepository.findById(id);  //findById의 리턴값은 Optional<T> findById(ID id);

    //데이터 null체크
    if (!user.isPresent()) {
      throw new UserNotFoundException(String.format("ID[%s} not found", id));
    }
    return user.get().getPosts();

  }

  @PostMapping("/users/{id}/posts")
  public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post) { //유효성 검사와 사용자의 데이터를 받아옴
    Optional<User> user = userRepository.findById(id);  //findById의 리턴값은 Optional<T> findById(ID id);

    if (!user.isPresent()) {
      throw new UserNotFoundException(String.format("ID[%s} not found", id));
    }
    post.setUser(user.get());
    Post savedPost = postRepository.save(post); //save함수는 리턴값이 User 이다

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("{id}") //url에 /{id}/까지
      .buildAndExpand(savedPost.getId())  //posts에 해당
      .toUri();  //uri로 전달

    return ResponseEntity.created(location).build();
  }

  @DeleteMapping("/users/{id}/posts")
  public void deletePost(@PathVariable int id){
    postRepository.deleteById(id);
  }

}

