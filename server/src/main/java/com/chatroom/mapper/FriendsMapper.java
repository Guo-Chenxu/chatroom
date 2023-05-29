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
     * @param name1 用户名
     * @param name2 用户名
     * @return 改变行数
     */
    int add(@Param("name1") String name1, @Param("name2") String name2);

    /**
     * 删除好友
     *
     * @param name1 用户名
     * @param name2 用户名
     * @return 改变行数
     */
    int delete(@Param("name1") String name1, @Param("name2") String name2);

    /**
     * 获取用户的所有好友名
     *
     * @param username 用户名
     * @return 好友名列表
     */
    List<String> getByUsername(@Param("username") String username);
}
