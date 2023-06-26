package com.chatroom.utils;

import com.chatroom.view.ChatView;

import java.util.HashMap;

/**
 * @author Ye peixin
 */
public class ChatViewManage {
    private static HashMap<String, ChatView> chatFrames = new HashMap<>();

    /**
     * 私聊哈希表的添加
     * @param receiverName
     * @param chatView
     */
    public static void addChatView(String receiverName, ChatView chatView){
        chatFrames.put(receiverName, chatView);
    }

    /**
     * 私聊窗口元素的获取
     * @param receiverName
     * @return
     */
    public static ChatView getChatView(String receiverName){
        return chatFrames.get(receiverName);
    }

    /**
     * 私聊哈希表的删除
     * @param receiverName
     * @return
     */
    public static ChatView removeChatView(String receiverName){
        return chatFrames.remove(receiverName);
    }

    /**
     * 私聊哈希表的清空
     */
    public static void removeAllChatView(){
        chatFrames.clear();
    }
}
