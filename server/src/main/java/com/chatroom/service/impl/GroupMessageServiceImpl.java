package com.chatroom.service.impl;

import com.alibaba.fastjson2.JSON;
import com.chatroom.entity.Message;
import com.chatroom.entity.MessageType;
import com.chatroom.entity.User;
import com.chatroom.mapper.GroupMapper;
import com.chatroom.mapper.GroupUserRelationMapper;
import com.chatroom.mapper.MessageMapper;
import com.chatroom.mapper.UserMapper;
import com.chatroom.service.GroupMessageService;
import com.chatroom.utils.ThreadManage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @program: chatroom
 * @description: 群组消息服务的实现类
 * @author: 郭晨旭
 * @create: 2023-06-05 10:22
 * @version: 1.0
 **/

@Service
public class GroupMessageServiceImpl implements GroupMessageService {
    @Resource
    MessageMapper messageMapper;
    @Resource
    GroupUserRelationMapper groupUserRelationMapper;

    /**
     * 群聊天用未读消息提醒
     */
    @Override
    @Deprecated
    public List<Message> getNotReadMessages(User user) {
        List<Message> notRead = messageMapper.getNotReadMessage(user.getUsername());
        messageMapper.setMessageReaded(user.getUsername());
        return notRead;
    }

    /**
     * 群组发送消息
     *
     * @param message 消息
     * @return 返回结果
     */
    @Override
    public boolean sendMessage(Message message) {
        List<String> groupMembers = groupUserRelationMapper.getUsersByGroupName(message.getReceiverName());
        int flag = messageMapper.add(message);
        List<Message> messages = messageMapper.getGroupMessage(message.getReceiverName());
        Message res = new Message(MessageType.GET_GROUP_MESSAGE);
        res.setContent(JSON.toJSONString(messages));
        res.setReceiverName(message.getReceiverName());
        for (String username : groupMembers) {
            if (ThreadManage.userIsOnline(username)) {
                ThreadManage.getThread(username).send(true, res);
            }
        }
        return flag > 0;
    }

    /**
     * 群内成员拉用户进群(不需要经过同意)
     *
     * @param message 请求消息
     * @return message
     */
    @Override
    @Deprecated
    public Message addRequest(Message message) {
        String username = message.getSenderName();
        String groupName = message.getReceiverName();
        groupUserRelationMapper.add(groupName, username);
        messageMapper.add(message);
        return message;
    }

    @Override
    public List<Message> getMessageList(String senderName, String receiverName) {
        return messageMapper.getGroupMessage(receiverName);
    }

    @Override
    public int deleteExpireMessage(long time) {
        Date date = new Date(time);
        return messageMapper.delete(date);
    }
}
