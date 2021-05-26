package com.example.inflearn.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  //데이터베이스와 관련된 형태의 빈
public interface UserRepository extends JpaRepository<User, Integer> {
}

