package com.chatroom;

/**
 * @program: chatroom
 * @description: 服务端与客户端连接的线程
 * @author: 郭晨旭
 * @create: 2023-05-14 14:54
 * @version: 1.0
 **/

import com.alibaba.fastjson2.JSONObject;
import com.chatroom.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread implements Runnable {

    private Socket socket;
    private boolean loop = true;
    private User user;
    private static Logger log = LoggerFactory.getLogger(ServerThread.class);

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //输入流接收数据
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            //输出流发送数据
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            while (loop) {

                JSONObject jsonObject = (JSONObject) ois.readObject();
                System.out.println(jsonObject.toJSONString());
                String message = jsonObject.getString("msg");
                if ("close".equals(message)) {
                    oos.writeUTF("close");
                    oos.flush();
                    break;
                } else {
                    oos.writeUTF("接收数据成功" + message);
                    oos.flush();
                }
            }
            log.info("服务端关闭客户端[{}]", socket.getRemoteSocketAddress());
            oos.close();
            ois.close();
            socket.close();
        } catch (Exception e) {
            log.info("接收数据异常socket关闭");
            e.printStackTrace();
        } finally {
            log.info("数据异常数据要怎么保留");
        }
    }


}

