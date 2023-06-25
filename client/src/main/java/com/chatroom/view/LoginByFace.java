package com.chatroom.view;

import com.alibaba.fastjson2.JSON;
import com.chatroom.controller.ClientConnectServerThread;
import com.chatroom.entity.Chat;
import com.chatroom.entity.Message;
import com.chatroom.entity.User;
import com.chatroom.service.UserService;
import com.chatroom.service.impl.UserServiceImpl;
import com.chatroom.utils.ThreadManage;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;


public class LoginByFace extends JFrame implements ActionListener {

    private Webcam webcam; // 声明为成员变量

    private User user;

    private Login login;

    private JFrame window; // 添加成员变量

    public LoginByFace(User user) {
        this.user = user;
        initComponents();
    }

    private void initComponents() {

        // 创建窗口并设置布局
        window = new JFrame("人脸登录");

        webcam = Webcam.getDefault();

        // 设置相机分辨率（示例分辨率为 640x480）
        webcam.setViewSize(new Dimension(640, 480));
        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(true);


        // 定义窗体的宽高
        int windowsWedth = 420;
        int windowsHeight = 380;


        // 获取此窗口容器 设置布局
        Container contentPane = this.getContentPane();
        contentPane.setLayout(null);
        // 创建顶部面板来容纳人脸登录标签和用户名输入面板
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(Color.BLUE);

        // 创建最上方的JLabel
        // 设置人脸登录标签
        JLabel jlbTop = new JLabel("人脸登录", JLabel.CENTER);
        jlbTop.setFont(new Font("", Font.BOLD, 18));
        jlbTop.setBounds(0, 0, windowsWedth, 30);
        jlbTop.setForeground(Color.white);
        jlbTop.setOpaque(true);
        jlbTop.setBackground(new Color(3, 37, 108));
        contentPane.add(jlbTop);

        // 将人脸登录标签和用户名输入面板添加到顶部面板
        topPanel.add(jlbTop, BorderLayout.NORTH);

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

        this.setSize(windowsWedth, windowsHeight);
        setLocationRelativeTo(getOwner());
        this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String username = user.getUsername();
        User loginUser = new User(username);
        // 检查是否输入用户名
        if (username.equals("")) {
            JOptionPane.showMessageDialog(this, "用户名不能为空!", "warning", JOptionPane.WARNING_MESSAGE);
        } else {
            // 拍照并转换为 BASE64 编码
            String base64Image = Camera.captureAndEncodeImage(webcam);
            // 检查与服务器的连接
            UserService userService = new UserServiceImpl();
//            Socket client = userService.getClient();
//            if (client != null && !client.isClosed()) {
            // 将登录消息发送至服务器
            Chat chat = userService.loginByFace(username, base64Image);
            Boolean flag = chat.getFlag();
            Message msg = chat.getMessage();
            // 判断操作是否成功
            if (flag) {
                String jsonContent = msg.getContent();
                List<Message> messageList = JSON.parseArray(jsonContent, Message.class);

//                        ObjectMapper objectMapper = new ObjectMapper();
//                        try {
//                            messageList = objectMapper.readValue(jsonContent, new TypeReference<List<Message>>() {
//                            });
//                        } catch (JsonProcessingException ex) {
//                            throw new RuntimeException(ex);
//                        }
//
                ClientConnectServerThread clientThread = new ClientConnectServerThread(username, userService.getClient());
                new Thread(clientThread).start();
                ThreadManage.addThread(username, clientThread);

                // 显示未读消息
                NotRead unReadList = new NotRead(loginUser, messageList);
                unReadList.showNotRead();
                new SelectionPage(loginUser);

                // 隐藏登录页面
                window.dispose();
                login.hideLogin();

            } else {
                JOptionPane.showMessageDialog(this, msg.getContent());
            }
//            }
//            else {
//                JOptionPane.showMessageDialog(this, "无法连接服务器！");
//            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                User user = new User();
                new LoginByFace(user);
            }
        });
    }
}