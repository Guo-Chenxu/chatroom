package com.chatroom.server.service;

import com.chatroom.server.entity.User;

/**
 * @program: chatroom
 * @description: 用户相关服务
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
}
