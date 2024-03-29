package com.chatroom.service.impl;

import com.chatroom.mapper.FriendsMapper;
import com.chatroom.service.FriendService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    private FriendsMapper friendsMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> getFriendList(String username) {
        return friendsMapper.getByUsername(username);
    }

    @Override
    public boolean addAgree(String username, String friendName) {
        int change = friendsMapper.add(username, friendName);
        return change > 0;
    }

    @Override
    public boolean removeFriend(String username, String friendName) {
        int change = friendsMapper.delete(username, friendName);
        return change > 0;
    }
}
