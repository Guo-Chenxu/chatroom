package com.chatroom.service;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Ye peixin
 */
public interface ChatService {

    Socket getClient();

    void myStop();
}
