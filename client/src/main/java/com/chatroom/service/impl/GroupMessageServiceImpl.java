package com.chatroom.service.impl;

import com.chatroom.entity.Message;
import com.chatroom.entity.MessageType;
import com.chatroom.service.MessageService;
import com.chatroom.utils.ThreadManage;

import java.util.Date;

/**
 * @program: chatroom
 * @description: 群聊消息服务实现类
 * @author: 郭晨旭
 * @create: 2023-06-25 15:29
 * @version: 1.0
 **/
public class GroupMessageServiceImpl implements MessageService {

    private static MessageService groupMessageService = new GroupMessageServiceImpl();

    public static MessageService getInstance() {
        return groupMessageService;
    }

    @Override
    public void getMessages(String sender, String receiver) {
        Message message = new Message(sender, receiver, new Date(), MessageType.GET_GROUP_MESSAGE, true);
        ThreadManage.send(sender, message);
    }

    @Override
    public void sendMessage(String sender, String receiver, String content) {
        Message message = new Message(sender, receiver, new Date(), MessageType.GROUP_MESSAGE, true);
        message.setContent(content);
        ThreadManage.send(sender, message);
    }
}
