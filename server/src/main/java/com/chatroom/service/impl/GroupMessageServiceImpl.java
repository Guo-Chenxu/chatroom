package com.chatroom.service.impl;

import com.chatroom.entity.Message;
import com.chatroom.entity.MessageType;
import com.chatroom.mapper.GroupMapper;
import com.chatroom.mapper.GroupUserRelationMapper;
import com.chatroom.mapper.MessageMapper;
import com.chatroom.mapper.UserMapper;
import com.chatroom.service.AbstractMessageService;
import com.chatroom.utils.ThreadManage;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: chatroom
 * @description: 群组消息服务的实现类
 * @author: 郭晨旭
 * @create: 2023-06-05 10:22
 * @version: 1.0
 **/
public class GroupMessageServiceImpl extends AbstractMessageService {
    @Resource
    UserMapper userMapper;
    @Resource
    GroupMapper groupMapper;
    @Resource
    MessageMapper messageMapper;
    @Resource
    GroupUserRelationMapper groupUserRelationMapper;

    /**
     * 群组发送消息
     *
     * @param message 消息
     * @return 返回结果
     */
    @Override
    public Message sendMessage(Message message) throws CloneNotSupportedException {
        messageMapper.add(message);
        List<String> groupMembers = groupUserRelationMapper.getUsersByGroupName(message.getReceiverName());
        List<Message> messages = new ArrayList<>();
        for (String username : groupMembers) {
            Message m = message.cloneMessage();
            // 根据用户是否在线判断消息是否已读
            if (ThreadManage.userIsOnline(message.getReceiverName())) {
                message.setIsRead(false);
                message.setMessageType(MessageType.USER_OFFLINE);
            } else {
                message.setIsRead(true);
            }
            m.setReceiverName(username);
            messages.add(m);
            // todo mybatis的for each标签似乎有点问题, 读不到标签内的sql语句, 所以暂时循环写数据库, 以后再改
            messageMapper.add(m);
        }
        // 给群组内每个用户都添加一条该消息
//        messageMapper.addList(messages);

        return message;
    }

    /**
     * 群内成员拉用户进群(不需要经过同意)
     *
     * @param message 请求消息
     * @return message
     */
    @Override
    public Message addRequest(Message message) {
        String username = message.getSenderName();
        String groupName = message.getReceiverName();
        groupUserRelationMapper.add(groupName, username);
        messageMapper.add(message);
        return message;
    }
}
