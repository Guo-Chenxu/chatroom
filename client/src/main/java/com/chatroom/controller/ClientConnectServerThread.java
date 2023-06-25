package com.chatroom.controller;


import com.alibaba.fastjson2.JSON;
import com.chatroom.entity.Chat;
import com.chatroom.entity.Message;
import com.chatroom.entity.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * @program: chatroom
 * @description: 客户端与服务器连接的线程
 * @author: 郭晨旭
 * @create: 2023-05-17 00:17
 * @version: 1.0
 **/
public class ClientConnectServerThread extends Thread {
    /**
     * 用户id
     */
    private final String username;
    /**
     * 客户端socket
     */
    private Socket client;
    private boolean loop;


    public ClientConnectServerThread(String username, Socket client) {
        this.username = username;
        this.client = client;
        this.loop = true;
    }

    public void myStop() throws IOException {
        this.loop = false;
        if (this.client != null) {
            this.client.close();
        }
    }

    public void send(Message message) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
            output.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (loop) {
                ObjectInputStream input = new ObjectInputStream(client.getInputStream());
                Chat chat = (Chat) input.readObject();
                Message msg = chat.getMessage();
                // todo 补充具体的页面展示
                if (!chat.getFlag()) {
                } else {
                    switch (msg.getMessageType()) {
                        case MessageType.CHANGE_PWD:
                            // 提示修改成功
                        case MessageType.GET_FRIENDS:
                            List<String> friends = JSON.parseArray(msg.getContent(), String.class);
                            break;
                        case MessageType.GET_GROUPS:
                            List<String> groups = JSON.parseArray(msg.getContent(), String.class);
                            break;
                        case MessageType.ADD_FRIEND:
                            break;
                        case MessageType.ADD_AGREE:
                            // 提示***同意了你的好友申请
                            break;
                        case MessageType.COMMON_MESSAGE:
                            // 提示***向你发送了一条消息
                            break;
                        case MessageType.GROUP_MESSAGE:
                            // 提示***在***群聊中发送了一条消息
                        case MessageType.GET_FRIEND_MESSAGE:
                            List<Message> friendMessages = JSON.parseArray(msg.getContent(), Message.class);
                            break;
                        case MessageType.GET_GROUP_MESSAGE:
                            List<Message> groupMessages = JSON.parseArray(msg.getContent(), Message.class);
                            break;
                        case MessageType.DELETE_FRIEND:
                            // 提示删除成功
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
