package com.chatroom;

import com.alibaba.fastjson2.JSON;
import com.chatroom.controller.ServerConnectClientThread;
import com.chatroom.entity.Group;
import com.chatroom.entity.Message;
import com.chatroom.entity.MessageType;
import com.chatroom.entity.User;
import com.chatroom.mapper.*;
import com.chatroom.service.*;
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
    FriendService friendService;

    @Resource
    GroupService groupService;

    @Resource
    FriendMessageService friendMessageService;

    @Resource
    GroupMessageService groupMessageService;

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
    void testMessageMapperContent() {
        Message m = new Message();
        m.setContent("11111");
        System.out.println(m);
        messagesMapper.add(m);
        System.out.println(messagesMapper.getAllMessages());
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
    void testAddFriend() {
        friendsMapper.add("123", "321");
    }

    @Test
    void testAddFriendMessage() {
        Message m = new Message();
        m.setContent("1324");
        messagesMapper.add(m);
    }

//    @Test
//    void testGetGroups() {
//        System.out.println(groupService.getGroupInfo("123"));
//    }

    @Test
    void testSetGroup() {
        Group group = new Group("123", "123", null, 1);
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("321");
        group.setUsers(list);
        groupService.setGroup(group);
    }


    @Test
    void testDeleteExpireGroupMessage() {
        System.out.println(groupMessageService.deleteExpireMessage(System.currentTimeMillis()));
    }

    @Test
    void testGetNotRead() {
        System.out.println(friendMessageService.getNotReadMessages(new User("zhangsan123", "", "")));
    }

    @Test
    void testGetMessage() {
        System.out.println(friendMessageService.getMessageList("zhangsan123", "lisi123"));
    }

    @Test
    void testGetFriendList() {
        System.out.println(friendService.getFriendList("zhangsan123"));
    }
}
