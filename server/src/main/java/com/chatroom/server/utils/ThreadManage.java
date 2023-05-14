package com.chatroom.server.utils;


import com.chatroom.server.thread.ServerConnectClientThread;

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
    private static Map<Integer, ServerConnectClientThread> map = new ConcurrentHashMap<>();

    /**
     * 将某个线程添加到集合中
     *
     * @param userId                    用户名
     * @param serverConnectClientThread 服务器连接到客户端的线程
     */
    public static void addThread(Integer userId, ServerConnectClientThread serverConnectClientThread) {
        map.put(userId, serverConnectClientThread);
    }

    /**
     * 通过用户名获取对应线程
     *
     * @param userId 用户id
     * @return 服务器连接到客服端的线程
     */
    public static ServerConnectClientThread getThread(Integer userId) {
        return map.get(userId);
    }

    /**
     * 用户退出系统, 删除该用户的线程
     *
     * @param userId 用户Id
     */
    public static void deleteUsers(Integer userId) {
        map.remove(userId);
    }

    /**
     * 判断该用户当前是否在线
     *
     * @param userId 用户id
     * @return 在线返回true, 否则返回false
     */
    public static boolean userIsOnline(Integer userId) {
        return map.containsKey(userId);
    }
}

