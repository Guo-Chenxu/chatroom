package com.chatroom.service.impl;

import com.alibaba.fastjson2.JSON;
import com.chatroom.entity.Group;
import com.chatroom.entity.Message;
import com.chatroom.entity.MessageType;
import com.chatroom.service.GroupService;
import com.chatroom.utils.ThreadManage;

import java.util.Date;

import static com.chatroom.entity.MessageType.GET_GROUPS;

/**
 * @author Ye peixin
 */
public class GroupServiceImpl implements GroupService {

    @Override
    public void getGroups(String username) {
        Message message = new Message(username, "", new Date(), GET_GROUPS, true);
        ThreadManage.send(username, message);
    }

    @Override
    public void setGroup(String username, Group group) {
        Message message = new Message(username, "", new Date(), MessageType.SET_GROUP, true);
        message.setContent(JSON.toJSONString(group));
        ThreadManage.send(username, message);
    }

    @Override
    public void addGroup(String username, String groupName) {
        if (ThreadManage.getThread(username) == null) {
            return;
        }
        Message message = new Message(username, groupName, new Date(), MessageType.ADD_GROUP, true);
        ThreadManage.send(username, message);
    }

    @Override
    public void leaveGroup(String username, String groupName) {
        Message message = new Message(username, groupName, new Date(), MessageType.LEAVE_GROUP, true);
        ThreadManage.send(username, message);
    }

    @Override
    public void getUsersInGroup(String username, String groupName) {
        Message message = new Message(username, groupName, new Date(), MessageType.GET_USERS_IN_GROUP, true);
        ThreadManage.send(username, message);
    }
}
