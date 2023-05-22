package com.chatroom.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: chatroom
 * @description: 群组类
 * @author: 郭晨旭
 * @create: 2023-05-02 14:03
 * @version: 1.0
 **/
@Data
public class Group implements Serializable {
    private static final long serialVersionUID = 174098509487131L;
    /**
     * 群号
     */
    private Integer groupId;
    /**
     * 群名
     */
    private String groupName;
    /**
     * 群主id
     */
    private Integer leaderId;
    /**
     * 群主名字
     * 仅用于前后端交互
     */
    private String leaderName;
    /**
     * 群组成员的信息
     * 仅用于前后端交互
     */
    private List<User> users;
    /**
     * 群头像
     */
    private String avatarId;
    /**
     * 群等级
     * 一级最多500人, 二级1000人, 三级2000人, 四级3000人
     */
    private int level;

    public Group(String groupName, Integer leaderId, String avatarId, int level) {
        this.groupName = groupName;
        this.leaderId = leaderId;
        this.avatarId = avatarId;
        this.level = level;
    }

    public Group() {
    }
}
