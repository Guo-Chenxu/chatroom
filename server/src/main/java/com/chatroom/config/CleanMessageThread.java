package com.chatroom.config;

import com.chatroom.service.GroupMessageService;
import com.chatroom.service.impl.GroupMessageServiceImpl;
import com.chatroom.utils.GetBeanUtil;

/**
 * @program: chatroom
 * @description: 定期清理过期消息的线程
 * @author: 郭晨旭
 * @create: 2023-06-27 20:31
 * @version: 1.0
 **/
public class CleanMessageThread implements Runnable {
    /**
     * 群组消息服务
     */
    private GroupMessageService groupMessageService = GetBeanUtil.getBean(GroupMessageServiceImpl.class);

    @Override
    public void run() {
        while (true) {
            try {
                long time = System.currentTimeMillis();
                Thread.sleep(1000 * 60 * 60 * 24 * 7);
                groupMessageService.deleteExpireMessage(time);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
