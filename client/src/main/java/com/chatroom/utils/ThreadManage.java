package com.chatroom.utils;


import com.chatroom.controller.ClientConnectServerThread;

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
    private static Map<Integer, ClientConnectServerThread> map = new ConcurrentHashMap<>();

    /**
     * 将某个线程添加到集合中
     *
     * @param userId                    用户名
     * @param clientConnectServerThread 客户端连接到服务器的线程
     */
    public static void addThread(Integer userId, ClientConnectServerThread clientConnectServerThread) {
        map.put(userId, clientConnectServerThread);
    }

    /**
     * 通过用户名获取对应线程
     *
     * @param userId 用户id
     * @return 服务器连接到客服端的线程
     */
    public static ClientConnectServerThread getThread(Integer userId) {
        return map.get(userId);
    }

    /**
     * 用户退出系统, 删除该用户的线程
     *
     * @param userId 用户Id
     */
    public static void deleteUser(Integer userId) {
        map.remove(userId);
    }
}

