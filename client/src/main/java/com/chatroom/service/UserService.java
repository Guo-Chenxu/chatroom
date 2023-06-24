package com.chatroom.service;

import com.chatroom.entity.Chat;
import com.chatroom.entity.Message;
import com.chatroom.entity.User;

import java.io.IOException;
import java.net.Socket;

/**
 * @program: chatroom
 * @description: 用户服务接口
 * @author: 郭晨旭
 * @create: 2023-05-17 00:24
 * @version: 1.0
 **/
public interface UserService {

    public void myStop();
    public Socket getClient();
    public Message login(String userName, String pwd);
    public Chat getFriendList(String userName);
    public void testSend(Message msg) throws IOException, ClassNotFoundException;

    Chat getGroupList(String userName);

    Chat addNewFriend(String userName);
}
