package com.chatroom.service;

import com.chatroom.entity.Chat;

import java.net.Socket;

/**
 * @author Ye peixin
 */
public interface FriendsService {
    Socket getClient();

    void myStop();

    //获取好友列表
    Chat getFriendList(String userName);
}
