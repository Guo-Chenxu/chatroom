package com.chatroom.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: chatroom
 * @description: 用户类
 * @author: 郭晨旭
 * @create: 2023-05-02 13:59
 * @version: 1.0
 **/
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 897689780087967678L;
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户头像
     */
    private String avatarId;
    /**
     * 用户的脸部信息
     */
    private String faceId;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
