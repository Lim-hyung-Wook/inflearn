package com.example.inflearn.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value = {"password","ssn"}) // 배열이라 계속 추가해도됨 아래에서 하나하나 추가하는것보다 이 방법이 깔끔함
@JsonFilter("UserInfoV2") //"UserInfo"이거는 컨트롤러나 서비스에서 사용
public class UserV2 extends User {

    private String grade;

}
