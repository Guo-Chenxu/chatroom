package com.chatroom.common.entity;

/**
 * @program: chatroom
 * @description: 消息类型
 * @author: 郭晨旭
 * @create: 2023-05-02 13:37
 * @version: 1.0
 **/
public enum MessageType {
    USER_LOGIN("0"),// 用户登录
    SIGNED_IN("1"),// 用户已登录
    REGISTER("2"),// 用户注册
    CHANGE_PWD("3"),// 修改密码
    INFO_ERROR("4"),// 账号或密码错误
    GET_FRIENDS("5"),// 获取好友列表
    ADD_FRIEND("6"),// 请求添加好友
    ADD_AGREE("7"),// 好友申请通过
    CHAT_MESSAGE("8"),// 普通聊天信息
    GROUP_MESSAGE("9"),// 群聊信息
    FILE_MESSAGE("10"), // 文件类型消息
    GET_CHAT_LIST("11"),// 获取聊天记录
    USER_OFFLINE("12"),// 用户不在线
    OFFLINE("13");// 下线
    private String value;

    private MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
