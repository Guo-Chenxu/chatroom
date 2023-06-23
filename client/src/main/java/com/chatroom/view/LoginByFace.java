package com.chatroom.view;

import com.alibaba.fastjson2.JSON;
import com.chatroom.controller.ClientConnectServerThread;
import com.chatroom.entity.Chat;
import com.chatroom.entity.Message;
import com.chatroom.entity.User;
import com.chatroom.service.UserService;
import com.chatroom.service.impl.UserServiceImpl;
import com.chatroom.utils.ThreadManage;
import com.github.sarxos.webcam.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Base64;

import static com.chatroom.entity.MessageType.*;
import static com.chatroom.entity.MessageType.INFO_ERROR;

public class LoginByFace extends JFrame implements ActionListener {

    private Webcam webcam; // 声明为成员变量
    private final JTextField userName; // 声明为成员变量
    public LoginByFace() {

        webcam = Webcam.getDefault(); // 初始化 webcam 对象
        // 设置相机分辨率（示例分辨率为 640x480）
        webcam.setViewSize(new Dimension(640, 480));
        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(true);

        // 创建用户名标签
        JLabel jlbUserName = new JLabel("用户名");

        // 创建用户名输入框
        userName = new JTextField();
        userName.setPreferredSize(new Dimension(300, 30)); // 设置输入框的首选尺寸为300x30
        // 创建面板来容纳用户名标签和输入框
        JPanel userInputPanel = new JPanel(new FlowLayout());
        userInputPanel.add(jlbUserName);
        userInputPanel.add(userName);

        // 创建顶部面板来容纳人脸登录标签和用户名输入面板
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(Color.BLUE);

        // 设置人脸登录标签
        JLabel jlbTop = new JLabel("人脸登录", JLabel.CENTER);
        jlbTop.setFont(new Font("", Font.BOLD, 18));
        jlbTop.setForeground(Color.white);
        jlbTop.setOpaque(true);
        jlbTop.setBackground(new Color(3, 37, 108));

        // 将人脸登录标签和用户名输入面板添加到顶部面板
        topPanel.add(jlbTop, BorderLayout.NORTH);
        topPanel.add(userInputPanel, BorderLayout.CENTER);

        // 创建底部面板来容纳摄像头画面和拍照并提交按钮
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // 设置摄像头画面
        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(true);

        // 创建拍照并提交按钮
        JButton captureButton = new JButton("拍照并提交");

        // 将摄像头画面和拍照并提交按钮添加到底部面板
        bottomPanel.add(panel, BorderLayout.CENTER);
        bottomPanel.add(captureButton, BorderLayout.SOUTH);
        captureButton.addActionListener(this);
        // 创建主面板来容纳顶部面板和底部面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel, BorderLayout.CENTER);

        // 创建窗口并设置布局
        JFrame window = new JFrame("Webcam Panel");
        window.getContentPane().add(mainPanel);
        window.setResizable(true);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);


        // 添加窗口关闭事件监听器
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 关闭摄像头
                webcam.close();
            }
        });

    }
    @Override
    public void actionPerformed(ActionEvent e) {

        // 获取用户名
        String username = userName.getText().trim();
        // 检查是否输入用户名
        if(username.equals("")){
            JOptionPane.showMessageDialog(this, "用户名不能为空!","warning",JOptionPane.WARNING_MESSAGE);
        }
        else{
            // 获取当前相机图像
            BufferedImage image = webcam.getImage();
            JOptionPane.showMessageDialog(null, "拍照成功");
            // 将图片转换为BASE64编码
            String base64Image = toBase64(image);
            // 检查与服务器的连接
            UserService userService = new UserServiceImpl();
            Socket client = userService.getClient();
            if (client != null && !client.isClosed()) {
                // 将登录消息发送至服务器
                Chat chat = userService.loginByFace(username, base64Image);
                Boolean flag = chat.getFlag();
                Message msg = chat.getMessage();
                // 判断操作是否成功
                if(flag){
                    // 处理服务器返回的结果
                    User loginUser = null;
                    switch (msg.getMessageType()) {
                        case LOGIN_BY_FACE:// 登录成功
                            // 创建与服务器通信的线程
                            loginUser = JSON.parseObject(msg.getContent(), User.class);
                            ClientConnectServerThread clientThread = new ClientConnectServerThread(username,userService.getClient());
                            clientThread.start();
                            ThreadManage.addThread(loginUser.getUsername(), clientThread);

                            // 将用户信息和好友列表控件保存至通信线程中
                            ClientConnectServerThread thread = ThreadManage.getThread(loginUser.getUsername());
                            FriendList friendList = new FriendList(loginUser);// 创建好友列表主界面
                            thread.setFriendList(friendList);
                            thread.setUser(loginUser);
                            friendList.updateFriendList();// 获取好友列表
                            this.dispose();// 关闭登录界面
                            break;
                        case USER_ONLINE:// 用户已经登录
                            JOptionPane.showMessageDialog(this, "用户已经登录！");
                            userService.myStop();
                            break;
                        case INFO_ERROR:// 用户名或面部识别错误
                            JOptionPane.showMessageDialog(this, "用户名或人脸信息错误！");
                            userService.myStop();
                            break;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, msg.getContent());
                }
            } else {
                JOptionPane.showMessageDialog(this, "无法连接服务器！");
            }
        }
    }
    public static String toBase64(BufferedImage image) {
        String base64Image = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            javax.imageio.ImageIO.write(image, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            base64Image = Base64.getEncoder().encodeToString(imageBytes);
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64Image;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginByFace();
            }
        });
    }
}
