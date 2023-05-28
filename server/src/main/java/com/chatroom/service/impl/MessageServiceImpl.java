package com.chatroom.service.impl;

import com.chatroom.entity.Message;
import com.chatroom.entity.User;
import com.chatroom.mapper.MessageMapper;
import com.chatroom.mapper.UserMapper;
import com.chatroom.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: chatroom
 * @description: 消息服务的实现类
 * @author: 郭晨旭
 * @create: 2023-05-15 00:16
 * @version: 1.0
 **/

@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    UserMapper userMapper;
    @Resource
    MessageMapper messageMapper;

    @Override
    public List<Message> getNotReadMessages(User user) {
        Integer id = userMapper.getByUsername(user.getUsername()).getUserId();
        List<Message> notRead = messageMapper.getNotReadMessage(id);
        messageMapper.setMessageReaded(id);
        return notRead;
    }
}
