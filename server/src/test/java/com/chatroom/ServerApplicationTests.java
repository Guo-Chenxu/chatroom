package com.chatroom;

import com.chatroom.entity.Message;
import com.chatroom.entity.MessageType;
import com.chatroom.mapper.UserMapper;
import com.chatroom.service.UserService;
import lombok.var;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class ServerApplicationTests {
    @Resource
    UserMapper userMapper;

    @Resource
    UserService userService;

    @Test
    void contextLoads() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        String string = list.toString();
        List<String> l = Arrays.asList(string.split(","));
        for (var i : l) {
            System.out.println(i);
        }
        Message message = new Message();
        message.setMessageType(MessageType.COMMON_MESSAGE.getValue());
        Assert.assertEquals(message.getMessageType(), MessageType.COMMON_MESSAGE.getValue());
    }

    @Test
    void testService() {
        System.out.println(userService);
    }
}
