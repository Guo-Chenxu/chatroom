package com.chatroom.server;

import com.chatroom.common.entity.User;
import com.chatroom.server.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ServerApplicationTests {
    @Resource
    UserMapper userMapper;

    @Test
    void contextLoads() {
        User user = new User();
        user.setUsername("123123aa");
        user.setPassword("234");
        System.out.println(user);
        System.out.println(userMapper);
        System.out.println(userMapper.add(user));
    }
}
