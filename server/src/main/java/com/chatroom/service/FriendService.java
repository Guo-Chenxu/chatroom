package com.chatroom.service;


import com.chatroom.entity.User;

import java.util.List;

/**
 * @program: chatroom
 * @description: 好友服务接口
 * @author: 郭晨旭
 * @create: 2023-05-22 11:07
 * @version: 1.0
 **/
public interface FriendService {
    /**
     * 获取好友列表
     * @param user 用户
     * @return 好友列表
     */
    List<User> getFriendList(User user);

    /**
     * 添加好友
     * @param user 用户
     * @param friend 被请求添加好友的用户
     * @return 是否添加成功
     */
    boolean addFriend(User user, User friend);

    /**
     * 删除好友
     * @param user 用户
     * @param friend 用户
     * @return 是否删除成功
     */
    boolean removeFriend(User user, User friend);
}
