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
    private Integer id;
    /**
     * 群名
     */
    private String groupName;
    /**
     * 群主名字
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

    public Group(String groupName, String leaderName, String avatarId, int level) {
        this.groupName = groupName;
        this.leaderName = leaderName;
        this.avatarId = avatarId;
        this.level = level;
    }

    public Group() {
    }

    /**
     * 根据群等级获取群最大人数限制
     *
     * @return 最大人数
     */
    public int getGroupMaxNumber() {
        switch (this.level) {
            case 1:
                return 500;
            case 2:
                return 1000;
            case 3:
                return 2000;
            default:
                return 3000;

        }
    }
}
