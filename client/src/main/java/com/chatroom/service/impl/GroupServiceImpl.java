package com.chatroom.service.impl;

import com.chatroom.entity.Chat;
import com.chatroom.entity.Message;
import static com.chatroom.entity.MessageType.*;
import com.chatroom.service.GroupService;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Ye peixin
 */
public class GroupServiceImpl implements GroupService {
    private Socket client;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public GroupServiceImpl(){
        try {
            // to do ip会变, 所以需要改
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
}
