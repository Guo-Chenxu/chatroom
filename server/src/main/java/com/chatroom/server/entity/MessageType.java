package com.chatroom.server.entity;

/**
 * @program: chatroom
 * @description: 消息类型
 * @author: 郭晨旭
 * @create: 2023-05-02 13:37
 * @version: 1.0
 **/
public enum MessageType {
    LOGIN_BY_PWD("0"), // 用户使用密码登录
    USER_ONLINE("1"), // 用户已登录
    REGISTER("2"), // 用户注册
    CHANGE_PWD("3"), // 修改密码
    INFO_ERROR("4"), // 账号、密码或者人脸不匹配
    GET_FRIENDS("5"), // 获取好友列表
    GET_GROUPS("6"), // 获取群聊列表
    ADD_FRIEND("7"), // 请求添加好友
    ADD_AGREE("8"), // 好友申请通过
    COMMON_MESSAGE("9"), // 普通聊天信息
    GROUP_MESSAGE("10"), // 群聊信息
    FILE_MESSAGE("11"), // 文件类型消息
    GET_FRIEND_MESSAGE("12"), // 获取好友聊天记录
    USER_OFFLINE("13"), // 用户不在线
    OFFLINE("14"), // 下线
    LOGIN_SUCCESS("15"), //登录成功
    LOGIN_FAIL("16"), // 登录失败
    LOGIN_BY_FACE("17"), // 用户使用人脸登录
    ADD_FACE("18"), // 添加人脸
    UPDATE_FACE("19"), // 修改人脸
    DELETE_FACE("20"), // 删除人脸
    GET_GROUP_MESSAGE("21"),// 获取群聊聊天记录
    SET_GROUP("22"), // 建群
    LEAVE_GROUP("23"), // 退群
    DELETE_GROUP("24"), // 解散群
    ADD_GROUP("25"), // 拉好友入群
    GET_OFFLINE_MESSAGE("26"), // 获取离线消息
    GET_FRIENDS_ADD("27"); // 获取好友申请信息
    private String value;

    private MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
