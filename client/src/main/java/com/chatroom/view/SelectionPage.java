package com.chatroom.view;

import com.chatroom.entity.User;
import com.chatroom.service.UserService;
import com.chatroom.service.impl.FriendsServiceImpl;
import com.chatroom.service.impl.GroupServiceImpl;
import com.chatroom.service.impl.UserServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;

public class SelectionPage extends JFrame {

    private JPanel container;

    public SelectionPage(User user) {
        setTitle("选择");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // 禁止调整窗口大小
        setPreferredSize(new Dimension(250, 450)); // 设置窗口大小

        JPanel contentPane = new JPanel(new BorderLayout());

        // 创建最上方的JLabel
        JLabel jlbTop = new JLabel(user.getUsername(), JLabel.CENTER);
        jlbTop.setFont(new Font("", Font.BOLD, 18));
        jlbTop.setForeground(Color.WHITE);
        jlbTop.setBounds(0, 0, 340, 40);
        jlbTop.setOpaque(true);
        jlbTop.setBackground(new Color(3, 37, 108));
        contentPane.add(jlbTop, BorderLayout.NORTH);

        container = new JPanel();

        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(55, 20, 20, 20)); // 设置容器留白

        JButton showFriendsBtn = createButton("展示好友列表");
        showFriendsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new FriendList(user);
            }
        });

        JButton showGroupsBtn = createButton("展示群组列表");
        showGroupsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new GroupList(user);
            }
        });

        JButton addFriendBtn = createButton("添加好友");
        addFriendBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddFriend(user);
            }
        });

        JButton createGroupBtn = createButton("建立群组");
        createGroupBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new CreateGroup(user);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JButton addFaceBtn = createButton("添加人脸信息");
        addFaceBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddFace(user);
            }
        });

        JButton changePwdBtn = createButton("修改密码");
        changePwdBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                new ChangPassword();
            }
        });

        container.add(showFriendsBtn);
        container.add(Box.createVerticalStrut(15)); // 添加垂直空白组件
        container.add(showGroupsBtn);
        container.add(Box.createVerticalStrut(15)); // 添加垂直空白组件
        container.add(addFriendBtn);
        container.add(Box.createVerticalStrut(15)); // 添加垂直空白组件
        container.add(createGroupBtn);
        container.add(Box.createVerticalStrut(15)); // 添加垂直空白组件
        container.add(addFaceBtn);
        container.add(Box.createVerticalStrut(15));
        container.add(changePwdBtn);

        contentPane.add(container, BorderLayout.CENTER);

        FriendsServiceImpl.getInstance().getFriendList(user.getUsername());
        GroupServiceImpl.getInstance().getGroups(user.getUsername());

        setContentPane(contentPane);
        pack();
        setLocationRelativeTo(null); // 居中显示
        setVisible(true);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                UserServiceImpl.getInstance().offLine(user.getUsername());
                System.exit(0);
            }
        });
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(3, 37, 108));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(150, 35)); // 设置按钮大小
        button.setMaximumSize(new Dimension(150, 35)); // 设置按钮最大大小，确保按钮不会变大
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // 按钮居中对齐
        button.setFocusPainted(false); // 删除焦点框
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                User user = new User();
                new SelectionPage(user);
            }
        });
    }
}
