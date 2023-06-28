package com.chatroom.service.impl;

import com.chatroom.entity.Message;
import com.chatroom.entity.MessageType;
import com.chatroom.service.MessageService;
import com.chatroom.utils.ThreadManage;

import java.util.Date;

/**
 * @program: chatroom
 * @description: 好友消息服务实现类
 * @author: 郭晨旭
 * @create: 2023-06-25 15:33
 * @version: 1.0
 **/
public class FriendMessageServiceImpl implements MessageService {

    private static FriendMessageServiceImpl friendMessageService = new FriendMessageServiceImpl();

    public static FriendMessageServiceImpl getInstance() {
        return friendMessageService;
    }

    @Override
    public void getMessages(String sender, String receiver) {
        Message message = new Message(sender, receiver, new Date(), MessageType.GET_FRIEND_MESSAGE);
        ThreadManage.send(sender, message);
    }

    @Override
    public void sendMessage(String sender, String receiver, String content) {
        Message message = new Message(sender, receiver, new Date(), MessageType.COMMON_MESSAGE);
        message.setContent(content);
        ThreadManage.send(sender, message);
    }
}
