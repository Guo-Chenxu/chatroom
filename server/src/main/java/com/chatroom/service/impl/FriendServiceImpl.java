package com.chatroom.service.impl;

import com.chatroom.entity.User;
import com.chatroom.mapper.FriendsMapper;
import com.chatroom.mapper.UserMapper;
import com.chatroom.service.FriendService;
import com.chatroom.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: chatroom
 * @description: 好友服务的实现类
 * @author: 郭晨旭
 * @create: 2023-05-22 11:07
 * @version: 1.0
 **/
@Service
public class FriendServiceImpl implements FriendService {

    @Resource
    FriendsMapper friendsMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<User> getFriendList(User user) {
        List<Integer> friendsIds = friendsMapper.getByUserId(user.getUserId());
        List<User> res = new ArrayList<>();
        for (Integer id : friendsIds) {
            res.add(userMapper.getByUserId(id));
        }
        return res;
    }

    @Override
    public boolean addFriend(User user, User friend) {
        Integer userId = userService.getIdByName(user.getUsername());
        Integer friendId = userService.getIdByName(friend.getUsername());
        int change = friendsMapper.add(userId, friendId);
        return change > 0;
    }

    @Override
    public boolean removeFriend(User user, User friend) {
        Integer userId = userService.getIdByName(user.getUsername());
        Integer friendId = userService.getIdByName(friend.getUsername());
        int change = friendsMapper.delete(userId, friendId);
        return change > 0;
    }
}
