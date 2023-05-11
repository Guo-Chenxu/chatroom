package com.chatroom.client.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: chatroom
 * @description: 好友类
 * @author: 郭晨旭
 * @create: 2023-05-02 16:18
 * @version: 1.0
 **/
@Data
public class Friends implements Serializable {
    private static final long serialVersionUID = 174098509489871L;

    private Integer id;
    /**
     * 自己的id
     */
    private Integer userId;
    /**
     * 好友的id
     */
    private Integer friendId;

    public Friends() {
    }

    public Friends(Integer userId, Integer friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }
}
