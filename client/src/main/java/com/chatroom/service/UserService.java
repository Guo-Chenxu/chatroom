package com.chatroom.service;

import com.chatroom.entity.Message;

import java.io.IOException;

/**
 * @program: chatroom
 * @description: 用户服务接口
 * @author: 郭晨旭
 * @create: 2023-05-17 00:24
 * @version: 1.0
 **/
public interface UserService {
    public void testSend(Message msg) throws IOException, ClassNotFoundException;
}
