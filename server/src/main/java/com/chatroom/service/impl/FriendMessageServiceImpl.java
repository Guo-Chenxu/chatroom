package com.chatroom.service.impl;

import com.chatroom.entity.Message;
import com.chatroom.entity.MessageType;
import com.chatroom.mapper.MessageMapper;
import com.chatroom.mapper.UserMapper;
import com.chatroom.service.AbstractMessageService;
import com.chatroom.utils.ThreadManage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: chatroom
 * @description: 好友消息服务的实现类
 * @author: 郭晨旭
 * @create: 2023-05-15 00:16
 * @version: 1.0
 **/

@Service
public class FriendMessageServiceImpl extends AbstractMessageService {
    @Resource
    UserMapper userMapper;
    @Resource
    MessageMapper messageMapper;

    /**
     * 好友之间发送消息
     *
     * @param message 消息
     * @return 返回结果
     */
    @Override
    public Message sendMessage(Message message) {
        // 根据用户是否在线判断消息是否已读
        if (ThreadManage.userIsOnline(message.getReceiverName())) {
            message.setIsRead(false);
            message.setMessageType(MessageType.USER_OFFLINE);
        } else {
            message.setIsRead(true);
        }
        messageMapper.add(message);
        return message;
    }
}
