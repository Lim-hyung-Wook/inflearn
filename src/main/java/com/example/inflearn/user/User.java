package com.example.inflearn.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor //디폴트 생성자 만들어줌
//@JsonIgnoreProperties(value = {"password","ssn"}) // 배열이라 계속 추가해도됨 아래에서 하나하나 추가하는것보다 이 방법이 깔끔함
//@JsonFilter("UserInfo") //"UserInfo"이거는 컨트롤러나 서비스에서 사용
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
@Entity
public class User {
  @Id
  @GeneratedValue()
  private Integer id;

  @Size(min = 2, message = "Name은 2글자 이상 입력해주세요")
  @ApiModelProperty(notes = "사용자 이름을 입력해 주세요")
  private String name;
  @Past
  @ApiModelProperty(notes = "사용자 등록일을 입력해 주세요")
  private Date joinDate;


  //Filtering
  //    @JsonIgnore  //데이터값을 무시해주세요, 결과값을 보여주지 않음
  @ApiModelProperty(notes = "사용자 비밀번호를 입력해 주세요")
  private String password; //비밀번호
  //    @JsonIgnore
  @ApiModelProperty(notes = "사용자 주민번호를 입력해 주세요")
  private String ssn; // 주민번호

  @OneToMany(mappedBy = "user")  //mappedBy 어떤 데이터와 매핑이 될것이냐
  private List<Post> posts;


  public User(int id, String name, Date joinDate, String password, String ssn) {
    this.id = id;
    this.name = name;
    this.joinDate = joinDate;
    this.password = password;
    this.ssn = ssn;
  }
}
