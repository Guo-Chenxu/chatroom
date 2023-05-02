package com.chatroom.server.mapper;

import com.chatroom.common.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: chatroom
 * @description: 数据库中关于用户表的操作
 * @author: 郭晨旭
 * @create: 2023-05-02 14:53
 * @version: 1.0
 **/
@Mapper
@Repository
public interface UserMapper {
    /**
     * 添加用户
     *
     * @param user 用户信息
     * @return 改变行数
     */
    Integer add(User user);

    /**
     * 根据用户id删除用户
     *
     * @param userId 用户id
     * @return 改变行数
     */
    int deleteById(@Param("userId") Integer userId);

    /**
     * todo 或许可能会有问题, 注意测试
     * 修改用户信息
     *
     * @param user 用户
     * @return 改变行数
     */
    int update(User user);

    /**
     * 根据用户id获取用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    User getByUserId(@Param("userId") Integer userId);

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    User getByUsername(@Param("userName") String username);

    /**
     * 获取所有用户信息
     *
     * @return 用户列表
     */
    List<User> getAll();
}
