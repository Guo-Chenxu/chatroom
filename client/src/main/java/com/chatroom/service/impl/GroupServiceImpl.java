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

    private static GroupService groupService = new GroupServiceImpl();

    public static GroupService getInstance() {
        return groupService;
    }

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
    public void addGroup(String username, String friendName, String groupName) {
        Message message = new Message(friendName, groupName, new Date(), MessageType.ADD_GROUP, true);
        ThreadManage.send(username, message);
    }

    @Override
    public void leaveGroup(String username, String groupName) {
        Message message = new Message(username, groupName, new Date(), MessageType.LEAVE_GROUP, true);
        ThreadManage.send(username, message);
    }

    @Override
    public void getGroupInfo(String username, String groupName) {
        Message message = new Message(username, groupName, new Date(), MessageType.GET_GROUP_INFO, true);
        ThreadManage.send(username, message);
    }
}
