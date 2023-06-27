package com.chatroom.service.impl;

import com.alibaba.fastjson2.JSON;
import com.chatroom.entity.Chat;
import com.chatroom.entity.Message;
import com.chatroom.entity.User;
import com.chatroom.service.UserService;
import com.chatroom.utils.ThreadManage;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

import static com.chatroom.entity.MessageType.*;

/**
 * @program: chatroom
 * @description: 用户服务接口的实现类
 * @author: 郭晨旭
 * @create: 2023-05-17 00:24
 * @version: 1.0
 **/
public class UserServiceImpl implements UserService {

    private Socket client;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    /**
     * 初始化服务
     */
    public void init() {
        try {
            // todo ip会变, 所以需要改
            client = new Socket("10.28.236.228", 9623);
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());
            System.out.println("连接服务器成功！");
        } catch (IOException e) {
            System.out.println("连接服务器失败！");
            e.printStackTrace();
            close(client, output, input);
        }
    }

    @Override
    public void addFace(String userName, String faceId) {
        // 将用户信息发送服务器登录
        User user = new User(userName);
        user.setFaceId(faceId);
        Message message = new Message(userName, "", new Date(), ADD_FACE);
        message.setContent((JSON.toJSONString(user)));
        ThreadManage.send(userName, message);
    }

    @Override
    public void offLine(String userName) {
        Message message = new Message(userName, "", new Date(), OFFLINE);
        ThreadManage.send(userName, message);
    }


    @Override
    public Socket getClient() {
        return client;
    }

    @Override
    public void myStop() {
        close(client, input, output);
        client = null;
        input = null;
        output = null;
    }

    /**
     * 关闭多个流
     */
    private void close(Closeable... ios) {//可变长参数
        for (Closeable io : ios) {
            try {
                if (null != io) {
                    io.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Chat loginByPwd(String userName, String pwd) {
        try {
            init();
            // 将用户信息发送服务器登录
            User user = new User(userName, pwd);
//            Message message = new Message(LOGIN_BY_PWD);
//            message.setContent((JSON.toJSONString(user)));
//            output.writeObject(message);
//            // 接收服务器返回到结果
//            Chat chat = (Chat) input.readObject();
//            if (!chat.getFlag()) {
//                client.close();
//            }
            return login(user, LOGIN_BY_PWD);
        } catch (IOException | ClassNotFoundException e) {
            close(client, output, input);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Chat loginByFace(String userName, String faceId) {
        try {
            init();
            // 将用户信息发送服务器登录
            User user = new User(userName);
            user.setFaceId(faceId);
//            Message message = new Message(LOGIN_BY_FACE);
//            message.setContent((JSON.toJSONString(user)));
//            output.writeObject(message);
//            // 接收服务器返回到结果
//            Chat chat = (Chat) input.readObject();
//
//            if (!chat.getFlag()) {
//                client.close();
//            }
//
//            return chat;
            return login(user, LOGIN_BY_FACE);
        } catch (IOException | ClassNotFoundException e) {
            close(client, output, input);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Chat register(String userName, String pwd) {
        try {
            init();

            // 将用户信息发送服务器
            User user = new User(userName, pwd);
            Message message = new Message(REGISTER);
            message.setContent((JSON.toJSONString(user)));
            output.writeObject(message);
            // 接收服务器返回到结果
            Chat chat = (Chat) input.readObject();

            client.close();

            return chat;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Chat login(User user, String type) throws IOException, ClassNotFoundException {
        Message message = new Message(type);
        message.setContent((JSON.toJSONString(user)));
        output.writeObject(message);
        // 接收服务器返回到结果
        Chat chat = (Chat) input.readObject();
        if (!chat.getFlag()) {
            client.close();
        }
        return chat;
    }
}
