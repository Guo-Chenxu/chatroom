package com.chatroom.mapper;

import com.chatroom.entity.User;
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
    int add(User user);

    /**
     * 根据用户名删除用户
     *
     * @param username 用户名
     * @return 改变行数
     */
    int delete(@Param("username") String username);

    /**
     * 修改用户信息
     *
     * @param user 用户
     * @return 改变行数
     */
    int update(User user);

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    User getByUsername(@Param("username") String username);

    /**
     * 用户添加/修改人脸
     *
     * @param face 人脸信息
     * @return 改变行数
     */
    int addFace(@Param("username") String username, @Param("face") String face);

    /**
     * 用户删除人脸
     * @param username 用户名
     * @return 改变行数
     */
    int deleteFace(@Param("username") String username);

    /**
     * 用户修改密码
     * @param username 用户名
     * @param password 密码
     * @return 改变行数
     */
    int changePassword(@Param("username") String username, @Param("password") String password);

    /**
     * 获取所有用户信息
     *
     * @return 用户列表
     */
    List<User> getAll();
}
