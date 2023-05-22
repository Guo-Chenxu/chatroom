package com.chatroom.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
     * 发送者用户id
     */
    private Integer senderId;
    /**
     * 接收者
     * 如果是好友聊天, 则为对方的id;
     * 如果是群聊, 则是群的id
     */
    private Integer receiverId;
    /**
     * 发送者用户名
     */
    private String senderName;
    /**
     * 接收者用户名
     * 仅用于前后端交互
     */
    private String receiverName;
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
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;
    /**
     * 消息类型
     */
    private String messageType;
    /**
     * 是否是群消息
     */
    private Boolean isGroupMessage;
    /**
     * 是否已读
     */
    private Boolean isRead;

    public Message() {
    }
}
