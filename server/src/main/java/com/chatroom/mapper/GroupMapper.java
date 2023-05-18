package com.chatroom.mapper;

import com.chatroom.entity.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @program: chatroom
 * @description: 数据库中关于群组表的操作
 * @author: 郭晨旭
 * @create: 2023-05-02 14:54
 * @version: 1.0
 **/
@Mapper
@Repository
public interface GroupMapper {
    /**
     * 增加一个群聊
     *
     * @param group 群信息
     * @return 改变行数
     */
    int add(Group group);

    /**
     * 根据群id删除群聊
     *
     * @param groupId 群id
     * @return 改变行数
     */
    int deleteByGroupId(@Param("groupId") Integer groupId);

    /**
     * 修改群群聊信息
     *
     * @param group 群聊信息
     * @return 改变行数
     */
    int update(Group group);

    /**
     * 根据群id获取群聊
     *
     * @param groupId 群id
     * @return 群组信息
     */
    Group getByGroupId(@Param("groupId") Integer groupId);

    /**
     * 根据群名获取群聊
     *
     * @param groupName 群名称
     * @return 群组信息
     */
    Group getByGroupName(@Param("groupName") String groupName);

    /**
     * 根据一个用户id获取他所管理的群(即他是群主的群)
     *
     * @param leaderId 群主id
     * @return 群组信息
     */
    Group getByLeaderId(@Param("leaderId") Integer leaderId);
}
