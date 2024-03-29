package com.chatroom.service.impl;

import com.chatroom.entity.Group;
import com.chatroom.mapper.GroupMapper;
import com.chatroom.mapper.GroupUserRelationMapper;
import com.chatroom.mapper.UserMapper;
import com.chatroom.service.GroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: chatroom
 * @description: 群组服务的实现类
 * @author: 郭晨旭
 * @create: 2023-05-22 11:41
 * @version: 1.0
 **/

@Service
public class GroupServiceImpl implements GroupService {

    @Resource
    private GroupMapper groupMapper;
    @Resource
    private GroupUserRelationMapper groupUserRelationMapper;
    @Resource
    private UserMapper userMapper;

    /**
     * 正则匹配, 6-20位字母数字下划线组合, 必须以字母开头
     */
    private static final String REG = "^[a-zA-Z]\\w{5,17}$";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setGroup(Group group) {
        List<String> users = group.getUsers();
        if (users.size() > group.getGroupMaxNumber()
                || !group.getGroupName().matches(REG)
                || groupMapper.getByGroupName(group.getGroupName()) != null
                || userMapper.getByUsername(group.getGroupName()) != null) {
            /*
            不能超过群聊最大人数
            群聊名称要符合规范
            群聊名称不能重复
             */
            return false;
        }

        int groupChange = groupMapper.add(
                new Group(group.getGroupName(), group.getLeaderName(), group.getAvatarId(), group.getLevel()));
        int relationChange = 0;
        for (String u : users) {
            groupUserRelationMapper.add(group.getGroupName(), u);
            ++relationChange;
        }
        return groupChange > 0 && relationChange >= users.size();
    }

    @Override
    public boolean addGroup(String groupName, String username) {
        Group group = groupMapper.getByGroupName(groupName);
        int counts = groupUserRelationMapper.getGroupCounts(groupName);

        if (counts + 1 > group.getGroupMaxNumber()
                || groupUserRelationMapper.getByGroupAndUser(groupName, username) > 0) {
            // 不能超过群聊最大人数
            // 不能重复加群
            return false;
        }

        int change = groupUserRelationMapper.add(groupName, username);
        return change > 0;
    }

    @Override
    public boolean leaveGroup(String groupName, String username) {
        return groupUserRelationMapper.delete(groupName, username) > 0;
    }

    @Override
    public List<String> getGroupsByUsername(String username) {
        return groupUserRelationMapper.getGroupsByUsername(username);
    }

    @Override
    public List<String> getGroupInfo(String groupName) {
        List<String> res = groupUserRelationMapper.getUsersByGroupName(groupName);
        Group g = groupMapper.getByGroupName(groupName);
        res.add(g.getLeaderName());
        res.add(String.valueOf(g.getLevel()));
        return res;
    }
}
