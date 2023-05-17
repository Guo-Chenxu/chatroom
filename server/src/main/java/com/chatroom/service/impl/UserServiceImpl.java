package com.chatroom.service.impl;

import com.chatroom.entity.User;
import com.chatroom.mapper.GroupMapper;
import com.chatroom.mapper.GroupUserRelationMapper;
import com.chatroom.mapper.MessageMapper;
import com.chatroom.mapper.UserMapper;
import com.chatroom.service.UserService;
import com.chatroom.utils.ThreadManage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: chatroom
 * @description: 用户服务的实现类
 * @author: 郭晨旭
 * @create: 2023-05-15 00:17
 * @version: 1.0
 **/

@Service
public class UserServiceImpl implements UserService {

    @Resource
    GroupMapper groupMapper;

    @Resource
    GroupUserRelationMapper groupUserRelationMapper;

    @Resource
    MessageMapper messageMapper;

    @Resource
    UserMapper userMapper;

    /**
     * 根据密码登录
     *
     * @param user 用户对象
     * @return 登陆成功返回true, 否则返回false
     */
    @Override
    public boolean loginByPwd(User user) {
        if (ThreadManage.userIsOnline(user.getUserId())) {
            return false;
        }
        User u = userMapper.getByUserId(user.getUserId());
        return u != null && u.getPassword().equals(user.getPassword());
    }
}
