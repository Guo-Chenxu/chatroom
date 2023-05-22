package com.chatroom.service.impl;

import com.chatroom.entity.Group;
import com.chatroom.entity.User;
import com.chatroom.mapper.GroupMapper;
import com.chatroom.mapper.GroupUserRelationMapper;
import com.chatroom.service.GroupService;
import com.chatroom.service.UserService;
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
public class GroupServiceImpl implements GroupService {

    @Resource
    GroupMapper groupMapper;
    @Resource
    GroupUserRelationMapper groupUserRelationMapper;
    @Resource
    UserService userService;

    /**
     * 正则匹配, 6-20位字母数字下划线组合, 必须以字母开头
     */
    private static final String reg = "^[a-zA-Z]\\w{5,17}$";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setGroup(Group group) {
        List<User> users = group.getUsers();
        if (users.size() > getGroupMaxNumber(group.getLevel())
                || !group.getGroupName().matches(reg)
                || groupMapper.getByGroupName(group.getGroupName()) != null) {
            /*
                不能超过群聊最大人数
                群聊名称要符合规范
                群聊名称不能重复
             */
            return false;
        }

        int groupChange = groupMapper.add(group);
        int groupId = getIdByName(group.getGroupName());
        int relationChange = 0;
        for (User u : users) {
            groupUserRelationMapper.add(groupId, userService.getIdByName(u.getUsername()));
            ++relationChange;
        }
        return groupChange > 0 || relationChange >= users.size();
    }

    @Override
    public boolean addGroup(String groupName, String username) {
        Group group = groupMapper.getByGroupName(groupName);
        int groupId = group.getGroupId();
        int userId = userService.getIdByName(username);
        int counts = groupUserRelationMapper.getGroupCounts(groupId);

        if (counts + 1 > getGroupMaxNumber(group.getLevel())
                || groupUserRelationMapper.getByGroupAndUser(groupId, userId) > 0) {
            /*
                不能超过群聊最大人数
                不能重复加群
             */
            return false;
        }

        int change = groupUserRelationMapper.add(groupId, userId);
        return change > 1;
    }

    @Override
    public boolean leaveGroup(String groupName, String username) {
        return false;
    }

    /**
     * 根据群等级获取群最大人数限制
     *
     * @param level 等级
     * @return 最大人数
     */
    private int getGroupMaxNumber(int level) {
        switch (level) {
            case 1:
                return 500;
            case 2:
                return 1000;
            case 3:
                return 2000;
            default:
                return 3000;

        }
    }

    @Override
    public Integer getIdByName(String name) {
        return groupMapper.getByGroupName(name).getGroupId();
    }
}
