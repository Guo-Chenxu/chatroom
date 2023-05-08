package com.chatroom.server.entity;

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
    GET_GROUPS("6"),// 获取群聊列表
    ADD_FRIEND("7"),// 请求添加好友
    ADD_AGREE("8"),// 好友申请通过
    CHAT_MESSAGE("9"),// 普通聊天信息
    GROUP_MESSAGE("10"),// 群聊信息
    FILE_MESSAGE("11"), // 文件类型消息
    GET_CHAT_LIST("12"),// 获取聊天记录
    USER_OFFLINE("13"),// 用户不在线
    OFFLINE("14"),// 下线
    LOGIN_SUCCESS("15"), //登录成功
    LOGIN_FAIL("16"); // 登录失败
    private String value;

    private MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
