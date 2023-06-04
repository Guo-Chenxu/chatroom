package com.chatroom.service;

import com.chatroom.entity.User;

/**
 * @program: chatroom
 * @description: 用户相关服务接口
 * @author: 郭晨旭
 * @create: 2023-05-15 00:16
 * @version: 1.0
 **/
public interface UserService {
    /**
     * 根据密码登录
     *
     * @param user 用户对象
     * @return 登陆成功返回true, 否则返回false
     */
    boolean loginByPwd(User user);

    /**
     * 根据人脸登录
     *
     * @param user 用户对象
     * @return 登录成功返回true, 否则返回false
     */
    boolean loginByFace(User user);

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return 注册成功返回true, 否则返回false
     */
    boolean register(User user);

    /**
     * 用户下线
     *
     * @param user 用户
     * @return 下线成功返回true, 否则返回false
     */
    boolean offline(User user);

    /**
     * 用户添加人脸
     *
     * @param user 用户信息
     * @return 是否添加成功
     */
    boolean addFace(User user);
}
