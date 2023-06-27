package com.chatroom.service.impl;

import com.chatroom.entity.Message;
import com.chatroom.entity.MessageType;
import com.chatroom.service.FriendsService;
import com.chatroom.utils.ThreadManage;

import java.util.Date;

import static com.chatroom.entity.MessageType.GET_FRIENDS;

/**
 * @author Ye peixin
 */
public class FriendsServiceImpl implements FriendsService {

    @Override
    public void getFriendList(String username) {
        Message message = new Message(username, "", new Date(), GET_FRIENDS);
        ThreadManage.send(username, message);
    }

    @Override
    public void addFriend(String username, String friend) {
        Message message = new Message(username, friend, new Date(), MessageType.ADD_FRIEND);
        ThreadManage.send(username, message);
    }

    @Override
    public void deleteFriend(String username, String friend) {
        Message message = new Message(username, friend, new Date(), MessageType.DELETE_FRIEND);
        ThreadManage.send(username, message);
    }

    @Override
    public void addAgree(String username, String friend) {
        Message message = new Message(username, friend, new Date(), MessageType.ADD_AGREE);
        System.out.println(message);
        ThreadManage.send(username, message);
    }
}
