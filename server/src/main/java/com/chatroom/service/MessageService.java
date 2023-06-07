package com.chatroom.service;

import com.chatroom.entity.Message;
import com.chatroom.entity.MessageType;
import com.chatroom.entity.User;
import com.chatroom.utils.ThreadManage;

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

    /**
     * 发送消息的服务
     * @param message 消息
     * @return 返回发送结果
     */
    Message sendMessage(Message message) throws CloneNotSupportedException;

    /**
     * 请求添加的消息
     *
     * @param message 请求消息
     * @return 是否添加成功
     */
    Message addRequest(Message message);
}
