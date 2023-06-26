package com.chatroom.controller;

import com.alibaba.fastjson2.JSON;
import com.chatroom.entity.*;
import com.chatroom.service.*;
import com.chatroom.service.impl.*;
import com.chatroom.utils.GetBeanUtil;
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
    private FriendService friendService = GetBeanUtil.getBean(FriendServiceImpl.class);
    /**
     * 群组服务
     */
    private GroupService groupService = GetBeanUtil.getBean(GroupServiceImpl.class);
    /**
     * 好友消息服务
     */
    private FriendMessageService friendMessageService = GetBeanUtil.getBean(FriendMessageServiceImpl.class);
    /**
     * 群组消息服务
     */
    private GroupMessageService groupMessageService = GetBeanUtil.getBean(GroupMessageServiceImpl.class);
    /**
     * 用户服务
     */
    private UserService userService = GetBeanUtil.getBean(UserServiceImpl.class);

    private static final Logger log = LoggerFactory.getLogger(ServerConnectClientThread.class);

    public ServerConnectClientThread(String username, Socket client) {
        this.username = username;
        this.client = client;
        this.loop = true;
    }

    public Socket getClient() {
        return this.client;
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
            if (message.getMessageType().equals(MessageType.COMMON_MESSAGE)) {
                int i = 1;
            }
            ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
            output.writeObject(chat);
            log.info("服务器向 " + this.username + " 发送了一条消息, 内容为 " + message);
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
                        log.info(new Date() + " 用户 " + msg.getSenderName() + " 请求更换密码");
                        flag = userService.changePassword(username, msg.getContent());
                        if (!flag) {
                            res.setContent("修改失败, 格式不符合要求");
                        }
                        send(flag, res);
                        break;
                    case MessageType.GET_FRIENDS:
                        log.info(new Date() + " 用户 " + msg.getSenderName() + " 请求获取好友列表");
                        List<String> friends = friendService.getFriendList(msg.getSenderName());
                        res.setContent(JSON.toJSONString(friends));
                        send(true, res);
                        break;
                    case MessageType.GET_GROUPS:
                        log.info(new Date() + " 用户 " + msg.getSenderName() + " 请求获取群聊列表");
                        List<String> groups = groupService.getGroupsByUsername(msg.getSenderName());
                        res.setContent(JSON.toJSONString(groups));
                        send(true, res);
                        break;
                    case MessageType.ADD_FRIEND:
                        log.info(new Date() + " 用户 " + msg.getSenderName() + " 请求添加 " + msg.getReceiverName() + " 为好友");
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
                    case MessageType.DELETE_FRIEND:
                        log.info(new Date() + " 用户 " + msg.getSenderName() + " 删除好友 " + msg.getReceiverName());
                        flag = friendService.removeFriend(msg.getSenderName(), msg.getReceiverName());
                        send(flag, res);
                        break;
                    case MessageType.ADD_AGREE:
                        log.info(new Date() + " 用户 " + msg.getSenderName() + " 同意了 " + msg.getReceiverName() + " 的好友申请");
                        flag = friendService.addAgree(msg.getSenderName(), msg.getReceiverName());
//                        ThreadManage.getThread(msg.getSenderName()).send(flag, msg);
                        sendError(flag, msg);
                        break;
                    case MessageType.COMMON_MESSAGE:
                        log.info(new Date() + " 用户 " + msg.getSenderName() + " 向 " + msg.getReceiverName() + " 发送了一条消息");
                        flag = friendMessageService.sendMessage(msg);
                        if (!flag) {
                            res.setContent("消息已送达, 对方将在上线后收到");
                            send(false, res);
                        } else {
                            ServerConnectClientThread thread = ThreadManage.getThread(msg.getReceiverName());
                            thread.send(true, msg);
                        }
                        break;
                    case MessageType.GROUP_MESSAGE:
                        log.info(new Date() + " 用户 " + msg.getSenderName() + " 在群聊 " + msg.getReceiverName() + " 中发送了一条消息");
                        flag = groupMessageService.sendMessage(msg);
                        if (!flag) {
                            res.setContent("消息发送失败, 请重试");
                            send(false, res);
                        }
                        break;
                    case MessageType.GET_FRIEND_MESSAGE:
                        log.info(new Date() + " 用户 " + msg.getSenderName() + " 获取和 " + msg.getReceiverName() + " 的好友聊天记录");
                        List<Message> fmessageList = friendMessageService.getMessageList(msg.getSenderName(), msg.getReceiverName());
                        res.setSenderName(msg.getSenderName());
                        res.setReceiverName(msg.getReceiverName());
                        res.setContent(JSON.toJSONString(fmessageList));
                        send(true, res);
                        break;
                    case MessageType.GET_GROUP_MESSAGE:
                        log.info(new Date() + " 用户 " + msg.getSenderName() + " 获取群聊 " + msg.getReceiverName() + " 的聊天记录");
                        List<Message> gmessageList = groupMessageService.getMessageList(msg.getSenderName(), msg.getReceiverName());
                        res.setSenderName(msg.getSenderName());
                        res.setReceiverName(msg.getReceiverName());
                        res.setContent(JSON.toJSONString(gmessageList));
                        send(true, res);
                        break;
                    case MessageType.ADD_FACE:
                    case MessageType.UPDATE_FACE:
                        log.info(new Date() + " 用户 " + msg.getSenderName() + " 增加/修改人脸信息");
                        flag = userService.addFace(JSON.parseObject(msg.getContent(), User.class));
                        sendError(flag, res);
                        break;
                    case MessageType.DELETE_FACE:
                        log.info(new Date() + " 用户 " + msg.getSenderName() + " 删除人脸信息");
                        flag = userService.deleteFace(msg.getContent());
                        sendError(flag, res);
                        break;
                    case MessageType.SET_GROUP:
                        log.info(new Date() + " 用户 " + msg.getSenderName() + " 创建群聊");
                        Group sgroup = JSON.parseObject(msg.getContent(), Group.class);
                        log.info("群聊信息 " + sgroup);
                        flag = groupService.setGroup(sgroup);
                        sendError(flag, res);
                        break;
                    case MessageType.LEAVE_GROUP:
                        log.info(new Date() + " 用户 " + msg.getSenderName() + " 退出群聊 " + msg.getReceiverName());
                        flag = groupService.leaveGroup(msg.getReceiverName(), msg.getSenderName());
                        sendError(flag, res);
                        break;
                    case MessageType.ADD_GROUP:
                        log.info(new Date() + " 用户 " + msg.getSenderName() + " 请求加入群聊 " + msg.getReceiverName());
                        flag = groupService.addGroup(msg.getReceiverName(), msg.getSenderName());
                        sendError(flag, res);
                        break;
                    case MessageType.OFFLINE:
                        log.info(new Date() + "用户 " + username + " 下线, 用户ip为: " + client.getRemoteSocketAddress());
                        userService.offline(msg.getSenderName());
                        break;
                    default:
                        break;
                }
            }
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
