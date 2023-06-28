package com.chatroom.service;

import com.chatroom.entity.Group;

/**
 * @author Ye peixin
 */
public interface GroupService {
    /**
     * 获取群聊列表
     *
     * @param username 用户名
     */
    void getGroups(String username);

    /**
     * 建立群聊
     *
     * @param username 用户名
     * @param group    群聊
     */
    void setGroup(String username, Group group);

    /**
     * 加入群聊
     *
     * @param username   用户名
     * @param friendName 被邀请的好友
     * @param groupName  群聊名
     */
    void addGroup(String username, String friendName, String groupName);

    /**
     * 离开群聊
     *
     * @param username  用户名
     * @param groupName 群聊名
     */
    void leaveGroup(String username, String groupName);

    /**
     * 获取群聊内的所有用户
     *
     * @param username  用户名
     * @param groupName 群聊名
     */
    void getGroupInfo(String username, String groupName);
}
