package com.chatroom.service.impl;

import com.alibaba.fastjson2.JSON;
import com.chatroom.entity.Chat;
import com.chatroom.entity.Message;
import static com.chatroom.entity.MessageType.*;

import com.chatroom.entity.User;
import com.chatroom.service.UserService;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

    public UserServiceImpl() {
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

    // todo 测试方法, 成功后删除
    @Override
    public void testSend(Message msg) throws IOException, ClassNotFoundException {
        output.writeObject(msg);
        Message mmsg = (Message) input.readObject();
        System.out.println(mmsg);
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
     * 用于关闭多个io流
     *
     * @param ios io流
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
    public Message login(String userName, String pwd) {
        try {
            // 将用户信息发送服务器登录
            User user = new User(userName, pwd);
            Message message = new Message(LOGIN_BY_PWD);
            message.setContent((JSON.toJSONString(user)));
            output.writeObject(message);
            // 接收服务器返回到结果
            Message msg = (Message) input.readObject();
            return msg;
        } catch (IOException | ClassNotFoundException e) {
            close(client, output, input);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Chat getFriendList(String userName){
        Message message = new Message(GET_FRIENDS);
        message.setSenderName(userName);
        try {
            output.writeObject(message);
            //接收服务器返回的结果
            Chat chat = (Chat)input.readObject();
            return chat;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Chat getGroupList(String userName){
        Message message = new Message(GET_GROUPS);
        message.setSenderName(userName);
        try {
            output.writeObject(message);

            Chat chat = (Chat)input.readObject();
            return chat;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Chat addNewFriend(String userName){
        Message message = new Message(ADD_FRIEND);
        message.setSenderName(userName);
//        message.setReceiverName(friendUsername);
        try {
            output.writeObject(message);

            Chat chat = (Chat)input.readObject();
            return chat;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
