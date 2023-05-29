package com.chatroom.entity;

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
     * 自己的用户名
     */
    private String username;
    /**
     * 好友的用户名
     */
    private String friendName;

    public Friends() {
    }

    public Friends(String username, String friendName) {
        this.username = username;
        this.friendName = friendName;
    }
}
