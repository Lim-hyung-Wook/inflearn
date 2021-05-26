package com.example.inflearn.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor //모든 생성자(필드)를 가짐
@NoArgsConstructor  //디폴트 생성자를 가짐
public class Post {
  @Id
  @GeneratedValue
  private Integer id;

  private String description;

  //User : Post -> 1 : (0~N), Main : Sub -> Parent: Child
  @ManyToOne(fetch = FetchType.LAZY)  //ManyToOne 은 현재 post라는 값이 여러개가 들어올수 있고 그것이 하나랑 매칭이된다.
                                      //fetch = FetchType.LAZY 지연 로딩 방식: 포스트 데이터가 로딩되는 시점에 데이터를 가지고 오겟다
  @JsonIgnore  // json데이터 타입으로 데이터를 외부에 공개하고자 할때 공개 되지 않는다
  private User user;
}
