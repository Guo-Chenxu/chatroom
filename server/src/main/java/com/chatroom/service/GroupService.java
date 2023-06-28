package com.chatroom.service;

import com.chatroom.entity.Group;

import java.util.List;

/**
 * @program: chatroom
 * @description: 群组服务接口
 * @author: 郭晨旭
 * @create: 2023-05-22 11:23
 * @version: 1.0
 **/
public interface GroupService {
    /**
     * 创建群组
     *
     * @param group 群组信息
     * @return 创建成功返回true, 否则返回false
     */
    boolean setGroup(Group group);

    /**
     * 用户加入群聊
     *
     * @param groupName 群聊名称
     * @param username  用户名
     * @return 添加成功返回true, 否则返回false
     */
    boolean addGroup(String groupName, String username);

    /**
     * 用户退出群聊
     *
     * @param groupName 群聊名称
     * @param username  用户名
     * @return 退出成功返回true, 否则返回false
     */
    boolean leaveGroup(String groupName, String username);

    /**
     * 根据用户名获取他的所有群聊
     *
     * @param username 用户名
     * @return 群聊列表
     */
    List<String> getGroupsByUsername(String username);

    /**
     * 获取群聊内所有群友
     *
     * @param groupName 群聊名
     * @return 好友列表
     */
    List<String> getGroupInfo(String groupName);
}
