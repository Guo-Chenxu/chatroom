package com.chatroom.controller;

import com.chatroom.entity.Message;
import com.chatroom.service.FriendService;
import com.chatroom.service.GroupService;
import com.chatroom.service.MessageService;
import com.chatroom.service.UserService;
import com.chatroom.service.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

/**
 * @program: chatroom
 * @description: 服务器和客户端连接的线程
 * @author: 郭晨旭
 * @create: 2023-05-15 00:17
 * @version: 1.0
 **/

public class ServerConnectClientThread implements Runnable {
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户socket
     */
    private Socket client;
    /**
     * 控制线程
     */
    private boolean loop;
    /**
     * 输入流
     */
    private ObjectInputStream input;
    /**
     * 输出流
     */
    private ObjectOutputStream output;
    /**
     * 好友服务
     */
    private FriendService friendService;
    /**
     * 群组服务
     */
    private GroupService groupService;
    /**
     * 好友消息服务
     */
    private MessageService friendMessageService;
    /**
     * 群组消息服务
     */
    private MessageService groupMessageService;
    /**
     * 用户服务
     */
    private UserService userService;

    private static Logger log = LoggerFactory.getLogger(ServerConnectClientThread.class);

    public ServerConnectClientThread(String username, Socket client) {
        this.username = username;
        this.client = client;
        this.loop = true;
        this.friendService = new FriendServiceImpl();
        this.groupService = new GroupServiceImpl();
        this.friendMessageService = new FriendMessageServiceImpl();
        this.groupMessageService = new GroupMessageServiceImpl();
        this.userService = new UserServiceImpl();
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
                switch (msg.getMessageType()) {

                }
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
            log.info(new Date() + "用户 " + username + " 下线, 用户ip为: " + client.getRemoteSocketAddress());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
