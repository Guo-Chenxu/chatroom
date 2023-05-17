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
     * @param groupId 群id
     * @param userId  用户id
     * @return 改变行数
     */
    int add(@Param("groupId") Integer groupId, @Param("userId") Integer userId);

    /**
     * 删除一对映射关系
     *
     * @param groupId 群id
     * @param userId  用户id
     * @return 改变行数
     */
    int delete(@Param("groupId") Integer groupId, @Param("userId") Integer userId);

    /**
     * 根据群id获取该群的所有成员id
     * @param groupId 群id
     * @return 成员id
     */
    List<Integer> getByGroupId(@Param("groupId") Integer groupId);

    /**
     * 根据用户id获取该用户参加的所有群
     * @param userId 用户id
     * @return 群id
     */
    List<Integer> getByUserId(@Param("userId") Integer userId);
}
