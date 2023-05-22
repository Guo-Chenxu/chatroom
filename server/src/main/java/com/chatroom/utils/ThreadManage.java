package com.chatroom.utils;


import com.chatroom.controller.ServerConnectClientThread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: chatroom
 * @description: 管理服务器和客户端连接的线程
 * @author: 郭晨旭
 * @create: 2023-05-15 00:17
 * @version: 1.0
 **/
public class ThreadManage {
    /**
     * 在线用户线程集合
     */
    private static Map<String, ServerConnectClientThread> map = new ConcurrentHashMap<>();

    /**
     * 将某个线程添加到集合中
     *
     * @param username                  用户名
     * @param serverConnectClientThread 服务器连接到客户端的线程
     */
    public static void addThread(String username, ServerConnectClientThread serverConnectClientThread) {
        map.put(username, serverConnectClientThread);
    }

    /**
     * 通过用户名获取对应线程
     *
     * @param username 用户名
     * @return 服务器连接到客服端的线程
     */
    public static ServerConnectClientThread getThread(String username) {
        return map.get(username);
    }

    /**
     * 用户退出系统, 删除该用户的线程
     *
     * @param username 用户名
     */
    public static void deleteUser(String username) {
        map.remove(username);
    }

    /**
     * 判断该用户当前是否在线
     *
     * @param username 用户名
     * @return 在线返回true, 否则返回false
     */
    public static boolean userIsOnline(String username) {
        return map.containsKey(username);
    }
}

