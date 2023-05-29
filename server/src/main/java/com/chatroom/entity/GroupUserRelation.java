package com.chatroom.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: chatroom
 * @description: 群组和用户对应关系类
 * @author: 郭晨旭
 * @create: 2023-05-02 14:05
 * @version: 1.0
 **/
@Data
public class GroupUserRelation implements Serializable {
    private static final long serialVersionUID = 17409850948678098L;

    private Integer id;
    /**
     * 群名称
     */
    private String groupName;
    /**
     * 用户名
     */
    private String username;

    public GroupUserRelation(String groupName, String username) {
        this.groupName = groupName;
        this.username = username;
    }

    public GroupUserRelation() {
    }
}
