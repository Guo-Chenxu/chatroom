package com.chatroom.controller;


import com.alibaba.fastjson2.JSON;
import com.chatroom.entity.Chat;
import com.chatroom.entity.Message;
import com.chatroom.entity.MessageType;
import com.chatroom.service.impl.FriendsServiceImpl;
import com.chatroom.utils.ChatViewManage;
import com.chatroom.utils.GroupChatViewManage;
import com.chatroom.view.ChatView;
import com.chatroom.view.FriendAddRequest;
import com.chatroom.view.GroupChatView;
import com.chatroom.view.ShowGroupInfo;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: chatroom
 * @description: 客户端与服务器连接的线程
 * @author: 郭晨旭
 * @create: 2023-05-17 00:17
 * @version: 1.0
 **/
public class ClientConnectServerThread extends JFrame implements Runnable {
    /**
     * 用户id
     */
    private String username;
    /**
     * 客户端socket
     */
    private Socket client;
    private boolean loop;

    /**
     * 好友列表
     */
    List<String> friends = new ArrayList<>();

    /**
     * 群聊列表
     */
    List<String> groups = new ArrayList<>();

    public ClientConnectServerThread(String username, Socket client) {
        this.username = username;
        this.client = client;
        this.loop = true;
    }

    public void myStop() throws IOException {
        this.loop = false;
        if (this.client != null) {
            this.client.close();
        }
    }

    public void send(Message message) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
            output.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (loop) {
                ObjectInputStream input = new ObjectInputStream(client.getInputStream());
                Chat chat = (Chat) input.readObject();
                Message msg = chat.getMessage();
                if (!chat.getFlag()) {
                    JOptionPane.showMessageDialog(this, msg.getContent(), "warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    switch (msg.getMessageType()) {
                        case MessageType.CHANGE_PWD:
                            JOptionPane.showMessageDialog(this, "密码修改成功！");
                            break;
                        case MessageType.GET_FRIENDS:
                            friends = JSON.parseArray(msg.getContent(), String.class);
                            break;
                        case MessageType.GET_GROUPS:
                            groups = JSON.parseArray(msg.getContent(), String.class);
                            break;
                        case MessageType.ADD_FRIEND:
                            new FriendAddRequest(msg.getSenderName(), msg.getReceiverName());
                            break;
                        case MessageType.ADD_AGREE:
                            FriendsServiceImpl.getInstance().getFriendList(username);
                            break;
                        case MessageType.COMMON_MESSAGE:
                            String senderName = msg.getSenderName();
                            ChatView chatView1 = ChatViewManage.getChatView(senderName);
                            chatView1.receiveChat(msg);
                            break;
                        case MessageType.GROUP_MESSAGE:
                            String groupName1 = msg.getReceiverName();
                            GroupChatView groupChatView1 = GroupChatViewManage.getGroupChatView(groupName1);
                            groupChatView1.receiveGroupChat(msg);
                        case MessageType.GET_FRIEND_MESSAGE:
                            List<Message> friendMessages = JSON.parseArray(msg.getContent(), Message.class);
                            System.out.println(friendMessages);
                            String receiverName = msg.getReceiverName();
                            ChatView chatView = ChatViewManage.getChatView(receiverName);
                            chatView.showChats((ArrayList<Message>) friendMessages);
                            break;
                        case MessageType.GET_GROUP_MESSAGE:
                            List<Message> groupMessages = JSON.parseArray(msg.getContent(), Message.class);
                            String groupName = msg.getReceiverName();
                            GroupChatView groupChatView = GroupChatViewManage.getGroupChatView(groupName);
                            groupChatView.showGroupList((ArrayList<Message>) groupMessages);
                            break;
                        case MessageType.GET_GROUP_INFO:
                            List<String> info = JSON.parseArray(msg.getContent(), String.class);
                            new ShowGroupInfo(info);
                            break;
                        case MessageType.DELETE_FRIEND:
                            JOptionPane.showMessageDialog(this, "删除成功！");
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getFriends() {
        return friends;
    }

    public List<String> getGroups() {
        return groups;
    }
}
