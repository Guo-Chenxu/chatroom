package com.chatroom.service;

import java.util.List;

/**
 * @author Ye peixin
 */
public interface FriendsService {

    /**
     * 获取好友列表
     *
     * @param username
     * @return 好友列表
     */
    void getFriendList(String username);

    /**
     * 添加好友
     *
     * @param username 自己
     * @param friend   请求添加的用户
     */
    void addFriend(String username, String friend);

    /**
     * 删除好友
     */
    void deleteFriend(String username, String friend);

    /**
     * 同意好友申请
     */
    void addAgree(String username, String friend);
}
