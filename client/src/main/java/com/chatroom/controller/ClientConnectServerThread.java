package com.chatroom.controller;


import com.chatroom.entity.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
    private Integer userId;
    /**
     * 客户端socket
     */
    private Socket client;
    private boolean loop;
    private ObjectInputStream input;
    private ObjectOutputStream output;


    public ClientConnectServerThread(Integer userId, Socket client) {
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
//                // 判断消息类型
//                switch (msg.getType()) {
//                    case GET_FRIENDS:// 获取好友列表
//                        ArrayList<User> users = (ArrayList<User>) msg.getContent();
//                        friendList.showFriendList(users);
//                        break;
//                    case GET_CHAT_LIST:// 获取聊天记录
//                        ArrayList<Chat> content = (ArrayList<Chat>) msg.getContent();
//                        String remark = (String) msg.getRemark();
//                        ChatView chatFrame = ChatViewManage.getChatFrame(Integer.parseInt(remark));
//                        // todo 加了一个content不为空, 应该就没问题了
//                        if (chatFrame != null && content != null) {
//                            chatFrame.showChats(content);
//                        }
//                        break;
//                    case CHAT_MESSAGE:// 聊天消息
//                        new ChatService().receive(msg);
//                        break;
//                    default:
//                        System.out.println("未知类型");
//                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    public FriendList getFriendList() {
//        return friendList;
//    }
//
//    public void setFriendList(FriendList friendList) {
//        this.friendList = friendList;
//    }

}
