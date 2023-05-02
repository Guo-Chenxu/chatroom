package com.chatroom.common.entity;

import lombok.Data;

import java.io.Serializable;

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
     * 群头像
     */
    private String avatarId;
}
