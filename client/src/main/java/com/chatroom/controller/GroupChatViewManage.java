package com.chatroom.controller;

import com.chatroom.view.GroupChatView;

import java.util.HashMap;

/**
 * @author Ye peixin
 */
public class GroupChatViewManage {
    private static HashMap<String, GroupChatView> groupChatFrames = new HashMap<>();

    /**
     * 群聊哈希表的添加
     * @param groupName
     * @param groupChatView
     */
    public static void addGroupChatView(String groupName, GroupChatView groupChatView){
        groupChatFrames.put(groupName, groupChatView);
    }

    /**
     * 获取群聊窗口元素
     * @param groupName
     * @return
     */
    public static GroupChatView getGroupChatView(String groupName){
        return groupChatFrames.get(groupName);
    }

    /**
     * 群聊哈希表的删除
     * @param groupName
     * @return
     */
    public static GroupChatView removeGroupChatView(String groupName){
        return groupChatFrames.remove(groupName);
    }

    /**
     * 群聊哈希表的清空
     */
    public static void removeAllGroupChatView(){
        groupChatFrames.clear();
    }
}
