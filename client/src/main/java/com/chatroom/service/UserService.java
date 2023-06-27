package com.chatroom.service;

import com.chatroom.entity.Chat;

import java.net.Socket;

/**
 * @program: chatroom
 * @description: 用户服务接口
 * @author: 郭晨旭
 * @create: 2023-05-17 00:24
 * @version: 1.0
 **/
public interface UserService {

    /**
     * 停止线程
     */
    void myStop();

    /**
     * 获取用户socket (仅在登录时使用)
     */
    Socket getClient();

    /**
     * 使用密码登录
     *
     * @param userName 用户名
     * @param pwd      密码
     * @return 未读消息
     */
    Chat loginByPwd(String userName, String pwd);

    /**
     * 使用人脸登录
     *
     * @param userName 用户名
     * @param faceId   人脸信息
     * @return 未读消息
     */
    Chat loginByFace(String userName, String faceId);

    /**
     * 注册
     *
     * @param userName 用户名
     * @param pwd      密码
     * @return 注册结果
     */
    Chat register(String userName, String pwd);

    /**
     * 添加人脸信息
     *
     * @param userName 用户名
     * @param faceId   人脸信息
     */
    void addFace(String userName, String faceId);

    /**
     * 用户请求下线
     *
     * @param userName 用户名
     */
    void offLine(String userName);


}
