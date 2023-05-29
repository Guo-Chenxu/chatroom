package com.chatroom.service.impl;

import com.chatroom.controller.ServerConnectClientThread;
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
     * 正则匹配, 6-20位字母数字下划线组合, 必须以字母开头
     */
    private static final String reg = "^[a-zA-Z]\\w{5,17}$";

    @Override
    public boolean loginByPwd(User user) {
        if (ThreadManage.userIsOnline(user.getUsername())) {
            return false;
        }
        User u = userMapper.getByUsername(user.getUsername());
        return u != null && u.getPassword().equals(user.getPassword());
    }

    /**
     * todo 暂时没有实现
     * 根据人脸登录
     *
     * @param user 用户对象
     * @return 登录成功返回true, 否则返回false
     */
    @Override
    public boolean loginByFace(User user) {
        return false;
    }

    @Override
    public boolean register(User user) {
        String name = user.getUsername();
        String pwd = user.getPassword();
        if (userMapper.getByUsername(name) != null) {
            return false;
        }

        if (name.matches(reg) && pwd.matches(reg)) {
            userMapper.add(user);
        }
        return false;
    }

    @Override
    public boolean offline(User user) {
        ServerConnectClientThread thread = ThreadManage.getThread(user.getUsername());
        thread.myStop();
        ThreadManage.deleteUser(user.getUsername());
        return false;
    }
}
