package com.chatroom.service.impl;

import com.chatroom.entity.Message;
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

    public Socket getClient() {
        return client;
    }

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
}
