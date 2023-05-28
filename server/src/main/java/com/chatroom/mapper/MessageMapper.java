package com.chatroom.mapper;

import com.chatroom.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: chatroom
 * @description: 数据库中关于消息表的操作
 * @author: 郭晨旭
 * @create: 2023-05-02 14:54
 * @version: 1.0
 **/
@Mapper
@Repository
public interface MessageMapper {
    /**
     * 增加一条新消息
     *
     * @param message 消息类
     * @return 改变行数
     */
    int add(Message message);

    /**
     * 删除此时间之前的群聊消息
     *
     * @param time 时间点
     * @return 改变行数
     */
    int delete(@Param("time") Long time);

    /**
     * 获取一对好友的聊天记录
     *
     * @return 消息列表
     */
    List<Message> getFriendMessage(@Param("id1") int id1, @Param("id2") int id2);

    /**
     * 获取群聊消息
     *
     * @param groupId 群id
     * @return 消息列表
     */
    List<Message> getGroupMessage(@Param("groupId") int groupId);

    /**
     * 获取所有信息
     */
    List<Message> getAllMessages();

    /**
     * 获取指定用户的所有好友的未读消息
     *
     * @param userId 用户id
     * @return 消息列表
     */
    List<Message> getNotReadMessage(@Param("userId") Integer userId);

    /**
     * 设置指定用户所有消息为已读状态
     *
     * @param userId 用户id
     * @return 改变行数
     */
    int setMessageReaded(@Param("userId") Integer userId);
}
