package com.chatroom.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: chatroom
 * @description: 数据库中关于好友表的操作
 * @author: 郭晨旭
 * @create: 2023-05-18 21:19
 * @version: 1.0
 **/
@Mapper
@Repository
public interface FriendsMapper {
    /**
     * 添加好友信息
     *
     * @param id1 用户id
     * @param id2 用户id
     * @return 改变行数
     */
    int add(@Param("id1") Integer id1, @Param("id2") Integer id2);

    /**
     * 删除好友
     *
     * @param id1 用户id
     * @param id2 用户id
     * @return 改变行数
     */
    int delete(@Param("id1") Integer id1, @Param("id2") Integer id2);

    /**
     * 获取用户的所有好友id
     *
     * @param userId 用户id
     * @return 好友id列表
     */
    List<Integer> getByUserId(@Param("userId") Integer userId);
}
