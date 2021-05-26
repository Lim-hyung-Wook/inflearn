package com.example.inflearn.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    private static int usersCount = 3;

    static {
        users.add(new User(1, "Kenneth", new Date(), "pass1", "701010-1111111"));
        users.add(new User(2, "Alice", new Date(), "pass2", "701010-2222222"));
        users.add(new User(3, "Elena", new Date(), "pass3", "701010-3333333"));
    }

    public List<User> findAll() {
        return users;                  //전체 user를 리턴
    }


    public User findOne(int id) {      // 해당 id만 리턴
        for (User user : users) {      //전체데이터를 조회해서
            if (user.getId() == id) {  // 해당 아이디가 같으면 user 리턴
                return user;
            }
        }
        return null;                   //for문 돌릴동안 id값이 없으면 null값 리턴
    }

    public User save(User user) {      // 사용자 추가 메서드
        if (user.getId() == null) {    //해당 아이디가 존재 하지 않으면
            user.setId(++usersCount);  //id에 + 해나아간다
        }
        users.add(user);               //user에 add메서드를 추가
        return user;                   // 추가한 user를 리턴
    }

    public User deleteById(int id) {
        Iterator<User> iterator = users.iterator();

        while (iterator.hasNext()) {
            User user = iterator.next();

            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}
