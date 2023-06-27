package com.chatroom.service.impl;

import com.chatroom.entity.Message;
import com.chatroom.entity.MessageType;
import com.chatroom.entity.User;
import com.chatroom.mapper.MessageMapper;
import com.chatroom.mapper.UserMapper;
import com.chatroom.service.FriendMessageService;
import com.chatroom.utils.ThreadManage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: chatroom
 * @description: 好友消息服务的实现类
 * @author: 郭晨旭
 * @create: 2023-05-15 00:16
 * @version: 1.0
 **/

@Service
public class FriendMessageServiceImpl implements FriendMessageService {
    @Resource
    MessageMapper messageMapper;

    @Override
    public List<Message> getNotReadMessages(User user) {
        List<Message> notRead = messageMapper.getNotReadMessage(user.getUsername());
        messageMapper.setMessageReaded(user.getUsername());
        return notRead;
    }

    /**
     * 好友之间发送消息
     *
     * @param message 消息
     * @return 返回结果
     */
    @Override
    public boolean sendMessage(Message message) {
        // 根据用户是否在线判断消息是否已读
        message.setIsRead(ThreadManage.userIsOnline(message.getReceiverName()));
        messageMapper.add(message);
        return message.getIsRead();
    }

    /**
     * 用户添加好友(需要被请求方同意)
     *
     * @param message 请求消息
     * @return 消息
     */
    @Override
    public Message addRequest(Message message) {
        String friendName = message.getReceiverName();
        if (!ThreadManage.userIsOnline(friendName)) {
            message.setIsRead(false);
            messageMapper.add(message);
            return null;
        } else {
            message.setIsRead(true);
            messageMapper.add(message);
            return message;
        }
    }

    @Override
    public List<Message> getMessageList(String senderName, String receiverName) {
        return messageMapper.getFriendMessage(senderName, receiverName);
    }
}
