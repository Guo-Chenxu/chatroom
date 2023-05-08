package com.chatroom.client.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: chatroom
 * @description: 消息类
 * @author: 郭晨旭
 * @create: 2023-05-02 13:38
 * @version: 1.0
 **/
@Data
public class Message implements Serializable {
    private static final long serialVersionUID = 1740985094875904L;

    private Integer messageId;
    /**
     * 发送者
     */
    private Integer senderId;
    /**
     * 接收者
     */
    private Integer receiverId;
    /**
     * 如果是群消息的话, 则表示消息是哪个群发的
     */
    private Integer groupId;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 文件数组
     */
    private byte[] file;
    /**
     * 发送时间
     */
    private Long sendTime;
    /**
     * 消息类型
     */
    private String messageType;
    /**
     * 消息状态
     * 1表示一睹, 0表示未读
     */
    private Integer state;

    public Message() {
    }
}
