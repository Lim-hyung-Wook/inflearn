package com.example.inflearn.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {  //AdminUserController는 특별한 기능은 아니고 일반적인 기능보다는 세밀한 부분을 할수 있는 컨트롤러
    private UserDaoService service;

    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUser() {
        List<User> users = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");  // 여기에 써놓은 정보만 출력됨

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(users);  //새로운 객체만들기
        mapping.setFilters(filters);

        return mapping;

    }

    //GET /admin/users/1 or / users/10 ->String      -->  /admin/v1/users/1
//    @GetMapping("v1/users/{id}")
    @GetMapping(value = "/users/{id}/", params = "version=1")
//    @GetMapping(value = "/users/{id}/", headers = "X-API-VERSION=1")
    public MappingJacksonValue retrieveUserV1(@PathVariable int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
//제어하고 싶은 데이터가 있으면 filter를 사용하면 됨

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");  // 여기에 써놓은 정보만 출력됨

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(user);  //새로운 객체만들기
        mapping.setFilters(filters);

        return mapping;

    }

    //    @GetMapping("v2/users/{id}")
//    @GetMapping(value = "/users/{id}/", params = "version=2")
    @GetMapping(value = "/users/{id}/", headers = "X-API-VERSION=2")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id) {
        User user = service.findOne(id);
        //여기서 검색된 내용을 userV2로 옮김김
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        //User -> userV2  유저의 내용을 userV2에 카피함

        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user, userV2); //빈들간에 관련된 작업들을 도와줌 인스턴스를 만들고, 두 인스턴스간의 공통적인 필드가 있을경우 해당내영을 카피 함
        userV2.setGrade("VIP");
        //정보를 추가하고


//제어하고 싶은 데이터가 있으면 filter를 사용하면 됨

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "grade");  // 여기에 써놓은 정보만 출력됨

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(userV2);  //새로운 객체만들기
        mapping.setFilters(filters);

        return mapping;

    }

}


// ResponseEntity<User>