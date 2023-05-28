package com.chatroom.service;

import com.chatroom.entity.Message;
import com.chatroom.entity.User;

import java.util.List;

/**
 * @program: chatroom
 * @description: 消息服务
 * @author: 郭晨旭
 * @create: 2023-05-15 00:15
 * @version: 1.0
 **/
public interface MessageService {
    /**
     * 获取用户的未读消息
     *
     * @param user 用户
     * @return 消息列表
     */
    List<Message> getNotReadMessages(User user);
}
