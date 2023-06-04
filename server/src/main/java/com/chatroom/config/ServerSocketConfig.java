package com.chatroom.config;

/**
 * @program: chatroom
 * @description: 服务端socket的配置类
 * @author: 郭晨旭
 * @create: 2023-05-14 14:54
 * @version: 1.0
 **/

import com.chatroom.controller.ConnectController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

@Component
public class ServerSocketConfig {

    private static Logger log = LoggerFactory.getLogger(ServerSocketConfig.class);

    private ConnectController server;

    @Bean
    public void socketCreate() throws UnknownHostException {
        log.info("==============================================================" +
                new Date() + " 服务器开启, 当前服务器ip地址为: " + InetAddress.getLocalHost()
                + "==============================================================");
        server = new ConnectController();
    }
}


