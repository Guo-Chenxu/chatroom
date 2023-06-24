package com.chatroom.service;

import com.chatroom.entity.Chat;

import java.net.Socket;

/**
 * @author Ye peixin
 */
public interface GroupService {
    Socket getClient();

    void myStop();

    Chat getGroupList(String userName);
}
