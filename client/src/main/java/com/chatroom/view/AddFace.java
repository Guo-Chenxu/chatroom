package com.chatroom.view;


import com.chatroom.entity.User;
import com.chatroom.service.UserService;
import com.chatroom.service.impl.UserServiceImpl;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class AddFace extends JFrame implements ActionListener {

    private Webcam webcam; // 声明为成员变量

    private JFrame window; // 添加成员变量
    private User user;

    public AddFace(User user) {
        this.user = user;
        initComponents();
    }

    private void initComponents() {
        webcam = Webcam.getDefault(); // 初始化 webcam 对象
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
        JLabel jlbTop = new JLabel("添加人脸信息", JLabel.CENTER);
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
        window = new JFrame("Webcam Panel");
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

        // 拍照并转换为 BASE64 编码
        String base64Image = Camera.captureAndEncodeImage(webcam);
        // 检查与服务器的连接
        UserService userService = new UserServiceImpl();
//        Socket client = userService.getClient();
//        if (client != null && !client.isClosed()) {
        // 将登录消息发送至服务器
        userService.addFace(username, base64Image);
        this.setVisible(false);
//        } else {
//            JOptionPane.showMessageDialog(this, "无法连接服务器！");
//        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                User user = new User();
                new AddFace(user);
            }
        });
    }
}