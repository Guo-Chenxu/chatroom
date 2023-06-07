package com.chatroom.service;


import com.chatroom.entity.Message;

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
     *
     * @param username 用户
     * @return 好友列表
     */
    List<String> getFriendList(String username);

    /**
     * 同意添加好友
     *
     * @param username   用户
     * @param friendName 被请求添加好友的用户
     * @return 是否添加成功
     */
    boolean addAgree(String username, String friendName);

    /**
     * 删除好友
     *
     * @param username   用户
     * @param friendName 用户
     * @return 是否删除成功
     */
    boolean removeFriend(String username, String friendName);
}
