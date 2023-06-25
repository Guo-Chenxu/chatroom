package com.chatroom.view;

import com.chatroom.entity.User;
import com.chatroom.service.UserService;
import com.chatroom.service.impl.UserServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectionPage extends JFrame {
    private final User user;
    private JPanel container;

    public SelectionPage(User user) {
        this.user = user;
        setTitle("选择");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // 禁止调整窗口大小
        setPreferredSize(new Dimension(250, 450)); // 设置窗口大小

        JPanel contentPane = new JPanel(new BorderLayout());

        // 创建最上方的JLabel
        JLabel jlbTop = new JLabel("选择", JLabel.CENTER);
        jlbTop.setFont(new Font("", Font.BOLD, 18));
        jlbTop.setForeground(Color.WHITE);
        jlbTop.setBounds(0, 0, 340, 30);
        jlbTop.setOpaque(true);
        jlbTop.setBackground(new Color(3, 37, 108));
        contentPane.add(jlbTop, BorderLayout.NORTH);

        container = new JPanel();

        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(55, 20, 20, 20)); // 设置容器留白

        JButton showFriendsBtn = createButton("展示好友列表");
        showFriendsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


            }
        });

        JButton showGroupsBtn = createButton("展示群组列表");
        showGroupsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        JButton addFriendBtn = createButton("添加好友");
        addFriendBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddFriend(new User());
            }
        });

        JButton createGroupBtn = createButton("建立群组");
        createGroupBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        JButton addFaceBtn = createButton("添加人脸信息");
        addFaceBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddFace(user);
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

        contentPane.add(container, BorderLayout.CENTER);

        setContentPane(contentPane);
        pack();
        setLocationRelativeTo(null); // 居中显示
        setVisible(true);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        System.out.println("1");
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                System.out.println(2);
                // 检查与服务器的连接
                UserService userService = new UserServiceImpl();
//                Socket client = ThreadManage.getThread(user.getUsername()).getClient();
                String username = user.getUsername();
                System.out.println(3);
//                if (client != null && !client.isClosed()) {
                // 将登录消息发送至服务器
                System.out.println(4);
                userService.offLine(username);
                System.out.println(5);
                System.exit(0);
//                } else {
//                    JOptionPane.showMessageDialog(null, "无法连接服务器！");
//                }
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
