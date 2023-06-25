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
public class Message implements Serializable, Cloneable {
    private static final long serialVersionUID = 1740985094875904L;

    private Integer id;
    /**
     * 发送者用户名
     */
    private String senderName;
    /**
     * 接收者用户名
     * 如果时群聊则是群名
     */
    private String receiverName;
    /**
     * 消息内容
     */
    private String content;
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

    public Message(String senderName, String receiverName, Date sendTime) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.sendTime = sendTime;
    }

    public Message(String senderName, String receiverName, Date sendTime, String type) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.sendTime = sendTime;
        this.messageType = type;
    }

    public Message(String senderName, String receiverName, Date sendTime, String type, boolean isGroupMessage) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.sendTime = sendTime;
        this.messageType = type;
        this.isGroupMessage = true;
    }

    public Message(String messageType) {
        this.messageType = messageType;
    }

    public Message cloneMessage() throws CloneNotSupportedException {
        return (Message) this.clone();
    }
}
