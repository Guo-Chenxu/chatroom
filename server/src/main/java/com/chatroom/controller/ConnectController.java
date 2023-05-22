package com.chatroom.controller;


import com.alibaba.fastjson2.JSON;
import com.chatroom.entity.Message;
import com.chatroom.entity.MessageType;
import com.chatroom.entity.User;
import com.chatroom.service.MessageService;
import com.chatroom.service.UserService;
import com.chatroom.utils.ThreadManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * @program: chatroom
 * @description: 服务器和客户端建立连接
 * @author: 郭晨旭
 * @create: 2023-05-15 00:17
 * @version: 1.0
 **/

@RestController
public class ConnectController implements Runnable {
    /**
     * 服务端socket
     */
    private ServerSocket server;
    private boolean loop;

    @Resource
    UserService userService;

    @Resource
    MessageService messageService;

    private static Logger log = LoggerFactory.getLogger(ConnectController.class);

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
                ObjectInputStream input = new ObjectInputStream(client.getInputStream());
                ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());

                // 等待获取用户信息
                Message message = (Message) input.readObject();
                User user = JSON.parseObject(message.getContent(), User.class);
                Message res = new Message();

                if (MessageType.LOGIN_BY_PWD.equals(message.getMessageType())) {
                    // 使用密码登录
                    if (userService.loginByPwd(user)) {
                        // 登录成功, 创建线程并添加管理
                        ServerConnectClientThread serverConnectClientThread
                                = new ServerConnectClientThread(user.getUsername(), client);
                        new Thread(serverConnectClientThread).start();
                        ThreadManage.addThread(user.getUsername(), serverConnectClientThread);

                        // 发送登录成功的消息
                        log.info("用户 " + user.getUsername() + " 在 " + new Date() + " 使用密码登录成功, " +
                                "用户ip为: " + client.getRemoteSocketAddress());
                        res.setMessageType(MessageType.LOGIN_SUCCESS);
                        output.writeObject(res);
                    } else {
                        // 登陆失败
                        log.info("用户 " + user.getUsername() + " 在 " + new Date() + " 使用密码登录失败, " +
                                "用户ip为: " + client.getRemoteSocketAddress());
                        res.setMessageType(MessageType.LOGIN_FAIL);
                        output.writeObject(res);
                    }
                } else if (MessageType.LOGIN_BY_FACE.equals(message.getMessageType())) {
                    // 使用人脸登录
                    if (userService.loginByFace(user)) {
                        // 登录成功, 创建线程并添加管理
                        ServerConnectClientThread serverConnectClientThread
                                = new ServerConnectClientThread(user.getUsername(), client);
                        new Thread(serverConnectClientThread).start();
                        ThreadManage.addThread(user.getUsername(), serverConnectClientThread);

                        // 发送登录成功的消息
                        log.info("用户 " + user.getUsername() + " 在 " + new Date() + " 使用人脸登录成功, " +
                                "用户ip为: " + client.getRemoteSocketAddress());
                        res.setMessageType(MessageType.LOGIN_SUCCESS);
                        output.writeObject(res);
                    } else {
                        // 登陆失败
                        log.info("用户 " + user.getUsername() + " 在 " + new Date() + " 使用人脸登录失败, " +
                                "用户ip为: " + client.getRemoteSocketAddress());
                        res.setMessageType(MessageType.LOGIN_FAIL);
                        output.writeObject(res);
                    }
                } else if (MessageType.REGISTER.equals(message.getMessageType())) {
                    // 用户注册
                    if (userService.register(user)) {
                        // 发送注册成功的消息
                        log.info("用户 " + user.getUsername() + " 在 " + new Date() + " 注册成功, " +
                                "用户ip为: " + client.getRemoteSocketAddress());
                        res.setMessageType(MessageType.REGISTER_SUCCESS);
                        output.writeObject(res);
                    } else {
                        // 注册失败
                        log.info("用户 " + user.getUsername() + " 在 " + new Date() + " 注册失败, " +
                                "用户ip为: " + client.getRemoteSocketAddress());
                        res.setMessageType(MessageType.REGISTER_FAIL);
                        output.writeObject(res);
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
     * 结束线程运行
     */
    public void myStop() {
        loop = false;
        close(server);
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
