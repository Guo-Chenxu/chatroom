package com.chatroom.controller;

import com.alibaba.fastjson2.JSON;
import com.chatroom.entity.Chat;
import com.chatroom.entity.Message;
import com.chatroom.entity.MessageType;
import com.chatroom.service.FriendService;
import com.chatroom.service.GroupService;
import com.chatroom.service.MessageService;
import com.chatroom.service.UserService;
import com.chatroom.service.impl.*;
import com.chatroom.utils.ThreadManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.List;

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

    private void send(Boolean flag, Message message) {
        try {
            Chat chat = new Chat(flag, message);
            output = new ObjectOutputStream(client.getOutputStream());
            output.writeObject(chat);
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
                Message res = new Message(msg.getMessageType());
                boolean flag;
                switch (msg.getMessageType()) {
                    case MessageType.CHANGE_PWD:
                        flag = userService.changePassword(username, msg.getContent());
                        if (!flag) {
                            res.setContent("修改失败, 格式不符合要求");
                        }
                        send(flag, res);
                        break;
                    case MessageType.GET_FRIENDS:
                        List<String> friends = friendService.getFriendList(msg.getSenderName());
                        res.setContent(JSON.toJSONString(friends));
                        send(true, res);
                        break;
                    case MessageType.GET_GROUPS:
                        List<String> groups = groupService.getGroupsByUsername(msg.getSenderName());
                        res.setContent(JSON.toJSONString(groups));
                        send(true, res);
                        break;
                    case MessageType.ADD_FRIEND:
                        res = friendMessageService.addRequest(msg);
                        if (res != null) {
                            ServerConnectClientThread thread = ThreadManage.getThread(res.getReceiverName());
                            thread.send(true, res);
                        } else {
                            res = new Message();
                            res.setContent("消息已送达, 对方将在上线后收到");
                            send(false, res);
                        }
                        break;
                    case MessageType.ADD_AGREE:
                        flag = friendService.addAgree(msg.getSenderName(), msg.getReceiverName());
                        send(flag, res);
                        break;
                    case MessageType.OFFLINE:
                        userService.offline(msg.getSenderName());
                        break;
                }
            }
            log.info(new Date() + "用户 " + username + " 下线, 用户ip为: " + client.getRemoteSocketAddress());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
