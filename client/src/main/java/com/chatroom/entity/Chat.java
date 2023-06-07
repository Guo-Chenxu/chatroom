package com.chatroom.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: chatroom
 * @description: 消息的传输类
 * @author: 郭晨旭
 * @create: 2023-06-07 15:35
 * @version: 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    /**
     * 操作是否成功
     */
    Boolean flag;
    /**
     * 具体信息内容
     */
    Message message;

    public Chat(Boolean flag) {
        this.flag = flag;
    }

    public Chat(Message message) {
        this.message = message;
    }
}
