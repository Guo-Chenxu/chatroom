package com.chatroom.utils;

import com.chatroom.view.GroupChatView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Ye peixin
 */
public class GroupChatViewManage {
    private static Map<String, GroupChatView> groupChatFrames = new ConcurrentHashMap<>();

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
