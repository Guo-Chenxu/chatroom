package com.chatroom.utils;


import com.chatroom.controller.ClientConnectServerThread;
import com.chatroom.entity.Message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: chatroom
 * @description: 管理服务器和客户端连接的线程
 * @author: 郭晨旭
 * @create: 2023-05-17 00:17
 * @version: 1.0
 **/
public class ThreadManage {
    /**
     * 在线用户线程集合
     */
    private static Map<String, ClientConnectServerThread> map = new ConcurrentHashMap<>();

    /**
     * 将某个线程添加到集合中
     *
     * @param username                  用户名
     * @param clientConnectServerThread 客户端连接到服务器的线程
     */
    public static void addThread(String username, ClientConnectServerThread clientConnectServerThread) {
        map.put(username, clientConnectServerThread);
    }

    /**
     * 通过用户名获取对应线程
     *
     * @param username 用户id
     * @return 服务器连接到客服端的线程
     */
    public static ClientConnectServerThread getThread(String username) {
        return map.get(username);
    }

    /**
     * 用户退出系统, 删除该用户的线程
     *
     * @param userId 用户Id
     */
    public static void deleteUser(Integer userId) {
        map.remove(userId);
    }

    /**
     * 向服务端发送消息
     *
     * @param username 用户名
     * @param message  消息
     */
    public static void send(String username, Message message) {
        ThreadManage.getThread(username).send(message);
    }
}

