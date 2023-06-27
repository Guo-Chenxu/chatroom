package com.chatroom;

import com.alibaba.fastjson2.JSON;
import com.chatroom.controller.ServerConnectClientThread;
import com.chatroom.entity.Group;
import com.chatroom.entity.Message;
import com.chatroom.entity.MessageType;
import com.chatroom.entity.User;
import com.chatroom.mapper.*;
import com.chatroom.service.UserService;
import lombok.var;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
class ServerApplicationTests {
    @Resource
    UserMapper userMapper;

    @Resource
    UserService userService;

    @Resource
    MessageMapper messagesMapper;

    @Resource
    GroupMapper groupMapper;

    @Resource
    FriendsMapper friendsMapper;

    @Resource
    GroupUserRelationMapper groupUserRelationMapper;

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
        message.setMessageType(MessageType.COMMON_MESSAGE);
        Assert.assertEquals(message.getMessageType(), MessageType.COMMON_MESSAGE);
    }

    @Test
    void testService() {
        System.out.println(userService);
    }

    @Test
    void testMessageMapper() {
        Message msg = new Message();
        long l = System.currentTimeMillis();
        msg.setIsGroupMessage(false);
        msg.setIsRead(false);
        msg.setSendTime(new Date(l + 3600L * 24 * 10 * 1000));
        System.out.println(new Date(l + 3600 * 24 * 10 * 1000));
        int add = messagesMapper.add(msg);
        System.out.println(add);
        List<Message> groupMessage = messagesMapper.getAllMessages();
        for (var m : groupMessage) {
            System.out.println(m);
        }
    }

    @Test
    void testGroupAdd() {
//        Group g = new Group();
//        g.setLeaderName("111");
//        g.setGroupName("123");
//        g.setAvatarId("123");
//        g.setLevel(1);
//        groupMapper.add(g);
    }

    @Test
    void testJson() {
        Message m = new Message();
        List<Message> l = new ArrayList<>();
        m.setContent("111");
        m.setMessageType(MessageType.COMMON_MESSAGE);
        l.add(m);
        m.setContent("222");
        l.add(m);
        m.setContent("333");
        l.add(m);
        m.setContent("444");
        l.add(m);
        System.out.println(l);
        String s = JSON.toJSONString(l);
        System.out.println(s);
        List<Message> ll = JSON.parseArray(s, Message.class);
        Message mm = ll.get(0);
        System.out.println(mm.getMessageType());
        System.out.println(mm.getContent());
        Assert.assertEquals(l, ll);
    }

    @Test
    void testMessageMapperContent() {
        Message m = new Message();
        m.setContent("11111");
        System.out.println(m);
        messagesMapper.add(m);
        System.out.println(messagesMapper.getAllMessages());
    }

    @Test
    void testAddList() {
//        List<Message> m = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Message mm = new Message();
//            mm.setContent(1 + "");
//            mm.setIsGroupMessage(i % 2 == 1);
//            mm.setIsRead(i % 2 == 0);
////            messagesMapper.add(mm);
//        }
//        messagesMapper.addList(m);
//        System.out.println(messagesMapper.getAllMessages());
    }

    @Test
    void testComponent() throws IOException {
        ServerConnectClientThread serverConnectClientThread =
                new ServerConnectClientThread("1223", new Socket(InetAddress.getLocalHost(), 9623));
        System.out.println(serverConnectClientThread);
        System.out.println("+++++++++++++++++++");
        ServerConnectClientThread serverConnectClientThread01 =
                new ServerConnectClientThread("1233", new Socket(InetAddress.getLocalHost(), 9623));
        System.out.println(serverConnectClientThread01);
    }

    @Test
    void testUserMapper() {
        userMapper.add(new User("12", "12", "12"));
    }

    ;

    @Test
    void testAddFriend() {
        friendsMapper.add("123", "321");
    }

    @Test
    void testAddFriendMessage() {
        Message m = new Message();
        m.setContent("1324");
        messagesMapper.add(m);
    }

    @Test
    void testSetGroup() {
        Group group = new Group("123", "123", null, 1);
        groupMapper.add(group);
    }

    @Test
    void testGetGroups() {
        System.out.println(groupUserRelationMapper.getGroupsByUsername("guochenxu123"));
    }
}
