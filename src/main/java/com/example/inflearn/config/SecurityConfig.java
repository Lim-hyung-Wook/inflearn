package com.example.inflearn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration  //를 등록되어 있는 클래스들은  스프링부트가 기동하면서 메모리에 설정정보를 같이 로딩한다
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers("/h2-console/**").permitAll(); // 어떤 메서드를 전달하더라도 통과 하도록 h2-console뒤에 어떤 값이 있더라고 모두 허용하도록
    http.csrf().disable();  //
    http.headers().frameOptions().disable();  //헤더값에 프레임의 대한 속성 값을 사용하지 않는다.
  }

  @Autowired  //의존성 주입
  public void configureGlobal(AuthenticationManagerBuilder auth)
    throws Exception {  //예외처리
    auth.inMemoryAuthentication()  //매개변수로 전달받은 auth를 메모리 방식으로 Authentication()을 추가한다
      .withUser("wook")   //username 설정
      .password("{noop}11")  //{noop} 어떠한 동작도 하지 않고 인코딩 없이 바로 사용가능
      .roles("USER"); //로그인이 완료되면 유저권환을 얻을수 있다.
  }

}
