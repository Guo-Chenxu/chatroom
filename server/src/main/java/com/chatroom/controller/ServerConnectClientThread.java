package com.chatroom.controller;

import com.chatroom.entity.Message;
import com.chatroom.entity.User;
import com.chatroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @program: chatroom
 * @description: 服务器和客户端连接的线程
 * @author: 郭晨旭
 * @create: 2023-05-15 00:17
 * @version: 1.0
 **/

public class ServerConnectClientThread implements Runnable {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户socket
     */
    private Socket client;
    private boolean loop;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public ServerConnectClientThread(Integer userId, Socket client) {
        this.userId = userId;
        this.client = client;
        this.loop = true;
    }

    public void myStop() {
        this.loop = false;
    }

    public void send(Message message) {
        try {
            output = new ObjectOutputStream(client.getOutputStream());
            output.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (loop) {
                input = new ObjectInputStream(client.getInputStream());
                Message msg = (Message) input.readObject();
//                //判断消息类型
//                switch (msg.getType()) {
//                    case GET_FRIENDS:// 获取好友列表
//                        User user = (User) msg.getContent();
//                        new UserService().getFriends(user.getQq());
//                        break;
//                    case CHAT_MESSAGE:// 普通聊天信息
//                        Chat chat = (Chat) msg.getContent();
//                        new ChatService().receiveChat(chat);
//                        break;
//                    case GET_CHAT_LIST:// 获取聊天记录
//                        ArrayList<User> content = (ArrayList<User>) msg.getContent();
//                        User user1 = content.get(0);
//                        User friend = content.get(1);
//                        new ChatService().getChatList(user1.getQq(), friend.getQq());
//                        break;
//                    case OFFLINE:// 用户下线
//                        User offlineUser = (User) msg.getContent();
//                        new UserService().userOffline(offlineUser);
//                        break;
//                    default:
//                        System.out.println("未知类型");
//                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
