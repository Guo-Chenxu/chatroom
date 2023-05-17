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
     * 发送者
     */
    private Integer senderId;
    /**
     * 接收者
     * 如果是好友聊天, 则为对方的id;
     * 如果是群聊, 则是群的id
     */
    private Integer receiverId;
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
     * 1表示是, 0表示否
     */
    private Integer isGroupMessage;
    /**
     * 消息状态
     * 1表示已读, 0表示未读
     */
    private Integer state;

    public Message() {
    }
}
