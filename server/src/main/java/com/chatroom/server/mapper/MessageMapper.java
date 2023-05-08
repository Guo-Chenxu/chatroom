package com.chatroom.server.mapper;

import com.chatroom.server.entity.Message;
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
}
