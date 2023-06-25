package com.chatroom.service;

/**
 * @program: chatroom
 * @description: 好友消息服务
 * @author: 郭晨旭
 * @create: 2023-06-25 15:28
 * @version: 1.0
 **/
public interface MessageService {

    /**
     * 获取聊天记录
     *
     * @param sender   用户名
     * @param receiver 好友名/群名
     */
    void getMessages(String sender, String receiver);

    /**
     * 发送消息
     *
     * @param sender   用户名
     * @param receiver 好友名/群名
     * @param content  消息内容
     */
    void sendMessage(String sender, String receiver, String content);
}
