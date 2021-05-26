package com.example.inflearn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  private static final Contact DEFAULT_CONCAT = new Contact("임형욱",
    "https://www.naver.com", "sda1996@naver.com");

  private static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Inflearn API",
    "공부용", "1.0", "urn:tos",
    DEFAULT_CONCAT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-1.0", new ArrayList<>());

  private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(
    Arrays.asList("application/json", "application/xml")); //asList()는 배열형태의 데이터값을 리스트형태로 바꿔줌


  // 관련된정보들을 지정해서 커스터마이징 해준다
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
      .apiInfo(DEFAULT_API_INFO)  //위에서 만든 apiinfo 지정
      .produces(DEFAULT_PRODUCES_AND_CONSUMES) //프로듀스의 대한 지정
      .consumes(DEFAULT_PRODUCES_AND_CONSUMES); //컨슘에 대한 데이터 지정
  }
}
