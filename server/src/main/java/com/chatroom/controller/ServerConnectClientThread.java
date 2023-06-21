package com.chatroom.controller;

import com.alibaba.fastjson2.JSON;
import com.chatroom.entity.*;
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
    private final String username;
    /**
     * 用户socket
     */
    private final Socket client;
    /**
     * 控制线程
     */
    private boolean loop;
    /**
     * 好友服务
     */
    private static final FriendService friendService = new FriendServiceImpl();
    /**
     * 群组服务
     */
    private static final GroupService groupService = new GroupServiceImpl();
    /**
     * 好友消息服务
     */
    private static final MessageService friendMessageService = new FriendMessageServiceImpl();
    /**
     * 群组消息服务
     */
    private static final MessageService groupMessageService = new GroupMessageServiceImpl();
    /**
     * 用户服务
     */
    private static final UserService userService = new UserServiceImpl();

    private static final Logger log = LoggerFactory.getLogger(ServerConnectClientThread.class);

    public ServerConnectClientThread(String username, Socket client) {
        this.username = username;
        this.client = client;
        this.loop = true;
    }

    public void myStop() {
        this.loop = false;
    }

    public void send(Boolean flag, Message message) {
        try {
            Chat chat = new Chat(flag, message);
            /*
             * 输出流
             */
            log.info("服务器向 " + this.username + " 发送了一条效写, 内容为 " + message);
            ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
            output.writeObject(chat);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (loop) {
                /*
                 * 输入流
                 */
                ObjectInputStream input = new ObjectInputStream(client.getInputStream());
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
                            res.setContent("添加请求已送达, 对方将在上线后收到");
                            send(false, res);
                        }
                        break;
                    case MessageType.ADD_AGREE:
                        flag = friendService.addAgree(msg.getSenderName(), msg.getReceiverName());
                        send(flag, res);
                        break;
                    case MessageType.COMMON_MESSAGE:
                        flag = friendMessageService.sendMessage(msg);
                        if (!flag) {
                            res.setContent("消息已送达, 对方将在上线后收到");
                            send(false, res);
                        } else {
                            ServerConnectClientThread thread = ThreadManage.getThread(msg.getSenderName());
                            thread.send(true, msg);
                        }
                        break;
                    case MessageType.GROUP_MESSAGE:
                        flag = groupMessageService.sendMessage(msg);
                        if (!flag) {
                            res.setContent("消息发送失败, 请重试");
                            send(false, res);
                        }
                        break;
                    case MessageType.GET_FRIEND_MESSAGE:
                        List<Message> fmessageList = friendMessageService.getMessageList(msg.getSenderName(), msg.getReceiverName());
                        res.setContent(JSON.toJSONString(fmessageList));
                        send(true, res);
                        break;
                    case MessageType.GET_GROUP_MESSAGE:
                        List<Message> gmessageList = groupMessageService.getMessageList(msg.getSenderName(), msg.getReceiverName());
                        res.setContent(JSON.toJSONString(gmessageList));
                        send(true, res);
                        break;
                    case MessageType.ADD_FACE:
                    case MessageType.UPDATE_FACE:
                        flag = userService.addFace(JSON.parseObject(msg.getContent(), User.class));
                        sendError(flag, res);
                        break;
                    case MessageType.DELETE_FACE:
                        flag = userService.deleteFace(msg.getContent());
                        sendError(flag, res);
                        break;
                    case MessageType.SET_GROUP:
                        Group sgroup = JSON.parseObject(msg.getContent(), Group.class);
                        flag = groupService.setGroup(sgroup);
                        sendError(flag, res);
                        break;
                    case MessageType.LEAVE_GROUP:
                        flag = groupService.leaveGroup(msg.getReceiverName(), msg.getSenderName());
                        sendError(flag, res);
                        break;
                    case MessageType.ADD_GROUP:
                        flag = groupService.addGroup(msg.getReceiverName(), msg.getSenderName());
                        sendError(flag, res);
                        break;
                    case MessageType.OFFLINE:
                        userService.offline(msg.getSenderName());
                        break;
                }
            }
            log.info(new Date() + "用户 " + username + " 下线, 用户ip为: " + client.getRemoteSocketAddress());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendError(Boolean flag, Message res) {
        if (!flag) {
            res.setContent("操作失败");
            send(false, res);
        }
    }
}
