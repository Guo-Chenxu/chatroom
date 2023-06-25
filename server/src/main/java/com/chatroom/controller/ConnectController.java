package com.chatroom.controller;


import com.alibaba.fastjson2.JSON;
import com.chatroom.entity.Chat;
import com.chatroom.entity.Message;
import com.chatroom.entity.MessageType;
import com.chatroom.entity.User;
import com.chatroom.service.FriendMessageService;
import com.chatroom.service.GroupMessageService;
import com.chatroom.service.UserService;
import com.chatroom.utils.ThreadManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.List;

/**
 * @program: chatroom
 * @description: 服务器和客户端建立连接
 * @author: 郭晨旭
 * @create: 2023-05-15 00:17
 * @version: 1.0
 **/
@Component
public class ConnectController implements Runnable {
    /**
     * 服务端socket
     */
    private ServerSocket server;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private boolean loop;

    @Resource
    UserService userService;
    @Resource
    FriendMessageService friendMessageService;

    @Resource
    GroupMessageService groupMessageService;

    private static final Logger log = LoggerFactory.getLogger(ConnectController.class);

    public ConnectController() {
        loop = true;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            // 设置服务器套接字 ServerSocket(int port)创建绑定到指定端口的服务器套接字
            server = new ServerSocket(9623);
            while (loop) {
                // 调用accept()方法开始监听，等待客户端的连接
                Socket client = server.accept();
                log.info("客户端连接：" + client.getInetAddress());
                input = new ObjectInputStream(client.getInputStream());
                output = new ObjectOutputStream(client.getOutputStream());

                // 等待获取用户信息
                Message message = (Message) input.readObject();
                User user = JSON.parseObject(message.getContent(), User.class);

                if (MessageType.LOGIN_BY_PWD.equals(message.getMessageType())) {
                    // 使用密码登录
                    if (userService.loginByPwd(user)) {
                        loginSuccess(user, client);
                    } else {
                        loginFail(user, client);
                    }
                } else if (MessageType.LOGIN_BY_FACE.equals(message.getMessageType())) {
                    // 使用人脸登录
                    if (userService.loginByFace(user)) {
                        loginSuccess(user, client);
                    } else {
                        loginFail(user, client);
                    }
                } else if (MessageType.REGISTER.equals(message.getMessageType())) {
                    // 用户注册
                    Message res = new Message();
                    if (userService.register(user)) {
                        // 发送注册成功的消息
                        log.info("用户 " + user.getUsername() + " 在 " + new Date() + " 注册成功, " +
                                "用户ip为: " + client.getRemoteSocketAddress());
                        res.setMessageType(MessageType.REGISTER_SUCCESS);
                        output.writeObject(new Chat(true, res));
                    } else {
                        // 注册失败
                        log.info("用户 " + user.getUsername() + " 在 " + new Date() + " 注册失败, " +
                                "用户ip为: " + client.getRemoteSocketAddress());
                        res.setContent("注册失败, 请检查用户名/密码格式是否正确");
                        output.writeObject(new Chat(false, res));
                    }
                }
            }
            log.info(new Date() + "服务器关闭");
        } catch (IOException | ClassNotFoundException e) {
            myStop();
            e.printStackTrace();
        }
    }

    /**
     * 用户登录成功
     *
     * @param user   用户
     * @param client 客户端socket
     */
    private void loginSuccess(User user, Socket client) throws IOException {
        // 登录成功, 创建线程并添加管理
        ServerConnectClientThread serverConnectClientThread
                = new ServerConnectClientThread(user.getUsername(), client);
        new Thread(serverConnectClientThread).start();
        ThreadManage.addThread(user.getUsername(), serverConnectClientThread);

        // 发送登录成功的消息
        log.info("用户 " + user.getUsername() + " 在 " + new Date() + " 登录成功, " +
                "用户ip为: " + client.getRemoteSocketAddress());
        Message res = new Message();
        res.setMessageType(MessageType.LOGIN_SUCCESS);
        List<Message> notRead = friendMessageService.getNotReadMessages(user);
        notRead.addAll(groupMessageService.getNotReadMessages(user));
        res.setContent(JSON.toJSONString(notRead));
        output.writeObject(new Chat(true, res));
    }

    /**
     * 用户登录失败
     *
     * @param user   用户
     * @param client 客户端socket
     */
    private void loginFail(User user, Socket client) throws IOException {
        // 登陆失败
        log.info("用户 " + user.getUsername() + " 在 " + new Date() + " 登录失败, " +
                "用户ip为: " + client.getRemoteSocketAddress());
        Message res = new Message();
        res.setContent("信息不正确或该用户已登录");
        output.writeObject(new Chat(false, res));
    }

    /**
     * 结束线程运行
     */
    public void myStop() {
        loop = false;
        close(server, input, output);
    }

    /**
     * 用于关闭多个io流
     *
     * @param ios io流
     */
    private void close(Closeable... ios) {//可变长参数
        for (Closeable io : ios) {
            try {
                if (io != null) {
                    io.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
