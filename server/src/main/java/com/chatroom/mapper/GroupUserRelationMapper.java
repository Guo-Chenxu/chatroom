package com.chatroom.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: chatroom
 * @description: 数据库中关于群组与用户关系表的操作
 * @author: 郭晨旭
 * @create: 2023-05-02 14:55
 * @version: 1.0
 **/
@Mapper
@Repository
public interface GroupUserRelationMapper {
    /**
     * 添加一对映射关系
     *
     * @param groupName 群名
     * @param username  用户名
     * @return 改变行数
     */
    int add(@Param("groupName") String groupName, @Param("username") String username);

    /**
     * 删除一对映射关系
     *
     * @param groupName 群名
     * @param username  用户名
     * @return 改变行数
     */
    int delete(@Param("groupName") String groupName, @Param("username") String username);

    /**
     * 根据群名获取该群的所有用户名
     *
     * @param groupName 群名
     * @return 成员id
     */
    List<String> getUsersByGroupName(@Param("groupName") String groupName);

    /**
     * 根据用户名获取该用户参加的所有群
     *
     * @param username 用户名
     * @return 群名
     */
    List<String> getGroupsByUsername(@Param("username") String username);

    /**
     * 根据群名获取群当前人数
     *
     * @param groupName 群组id
     * @return 群人数
     */
    int getGroupCounts(@Param("groupName") String groupName);

    /**
     * 根据群名和用户名判断该用户是否在该群聊内
     *
     * @param groupName 群组id
     * @param username  用户名
     * @return 获取到的行数, 大于1表示在
     */
    int getByGroupAndUser(@Param("groupName") String groupName, @Param("username") String username);
}
