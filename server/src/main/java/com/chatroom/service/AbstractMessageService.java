package com.chatroom.service;

import com.chatroom.entity.Message;
import com.chatroom.entity.User;
import com.chatroom.mapper.MessageMapper;
import com.chatroom.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: chatroom
 * @description: 消息服务的抽象类
 * @author: 郭晨旭
 * @create: 2023-06-05 10:20
 * @version: 1.0
 **/
public abstract class AbstractMessageService implements MessageService {
    @Resource
    UserMapper userMapper;
    @Resource
    MessageMapper messageMapper;

    @Override
    public List<Message> getNotReadMessages(User user) {
        List<Message> notRead = messageMapper.getNotReadMessage(user.getUsername());
        messageMapper.setMessageReaded(user.getUsername());
        return notRead;
    }
}
