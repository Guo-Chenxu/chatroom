package com.chatroom.entity;

/**
 * @program: chatroom
 * @description: 消息类型
 * @author: 郭晨旭
 * @create: 2023-05-21 21:56
 * @version: 1.0
 **/
public interface MessageType {
    /**
     * 用户使用密码登录
     */
    String LOGIN_BY_PWD = "0";
    /**
     * 用户已登录
     */
    String USER_ONLINE = "1";
    /**
     * 用户注册
     */
    String REGISTER = "2";
    /**
     * 修改密码
     */
    String CHANGE_PWD = "3";
    /**
     * 账号、密码或者人脸不匹配
     */
    String INFO_ERROR = "4";
    /**
     * 获取好友列表
     */
    String GET_FRIENDS = "5";
    /**
     * 获取群聊列表
     */
    String GET_GROUPS = "6";
    /**
     * 请求添加好友
     */
    String ADD_FRIEND = "7";
    /**
     * 好友申请通过
     */
    String ADD_AGREE = "8";
    /**
     * 普通聊天信息
     */
    String COMMON_MESSAGE = "9";
    /**
     * 群聊信息
     */
    String GROUP_MESSAGE = "10";
    /**
     * 文件类型消息
     */
    String FILE_MESSAGE = "11";
    /**
     * 获取好友聊天记录
     */
    String GET_FRIEND_MESSAGE = "12";
    /**
     * 用户不在线
     */
    String USER_OFFLINE = "13";
    /**
     * 下线
     */
    String OFFLINE = "14";
    /**
     * 登录成功
     */
    String LOGIN_SUCCESS = "15";
    /**
     * 登录失败
     */
    String LOGIN_FAIL = "16";
    /**
     * 用户使用人脸登录
     */
    String LOGIN_BY_FACE = "17";
    /**
     * 添加人脸
     */
    String ADD_FACE = "18";
    /**
     * 修改人脸
     */
    String UPDATE_FACE = "19";
    /**
     * 删除人脸
     */
    String DELETE_FACE = "20";
    /**
     * 获取群聊聊天记录
     */
    String GET_GROUP_MESSAGE = "21";
    /**
     * 建群
     */
    String SET_GROUP = "22";
    /**
     * 退群
     */
    String LEAVE_GROUP = "23";
    /**
     * 解散群
     */
    String DELETE_GROUP = "24";
    /**
     * 拉好友入群
     */
    String ADD_GROUP = "25";
    /**
     * 获取离线消息
     */
    String GET_OFFLINE_MESSAGE = "26";
    /**
     * 获取好友申请信息
     */
    String GET_FRIENDS_ADD = "27";
    /**
     * 注册成功
     */
    String REGISTER_SUCCESS = "28";
    /**
     * 注册失败
     */
    String REGISTER_FAIL = "29";
}