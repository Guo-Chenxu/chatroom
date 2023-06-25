package com.chatroom.view;

import com.chatroom.service.FriendsService;
import com.chatroom.service.UserService;
import com.chatroom.service.impl.FriendsServiceImpl;
import com.chatroom.service.impl.UserServiceImpl;

import java.awt.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FriendAddRequest extends JFrame {
    private String friendName;
    private String username;

    public FriendAddRequest(String friendName,String username) {
        this.friendName = friendName;
        this.username = username;
        initComponents();
    }

    private void initComponents() {
        setTitle("好友申请");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // 创建面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // 创建标签和按钮
        JLabel nameLabel = new JLabel( friendName + "申请添加好友");
        JButton acceptButton = new JButton("同意");
        JButton declineButton = new JButton("拒绝");

        // 设置按钮监听器
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 处理同意按钮点击事件
                JOptionPane.showMessageDialog(FriendAddRequest.this, "您同意了好友申请");

                FriendsService friendsService = new FriendsServiceImpl();

                friendsService.addAgree(username,friendName);

                dispose(); // 关闭窗口
            }
        });

        declineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 处理拒绝按钮点击事件
                JOptionPane.showMessageDialog(FriendAddRequest.this, "您拒绝了好友申请");
                dispose(); // 关闭窗口
            }
        });

        // 设置面板布局和间距
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        // 设置标签和按钮的对齐方式
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        acceptButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        declineButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 将标签和按钮添加到面板
        infoPanel.add(Box.createVerticalGlue());
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalGlue());
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(acceptButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(declineButton);
        buttonPanel.add(Box.createHorizontalStrut(50));

        // 将面板添加到主面板
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 将主面板添加到窗口
        setContentPane(mainPanel);

        // 设置窗口属性
        pack(); // 自适应大小
        setLocationRelativeTo(null);
        setVisible(true);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
////            public void run() {
////                new FriendAddRequest("John Doe");
////            }
//        });
//    }
}