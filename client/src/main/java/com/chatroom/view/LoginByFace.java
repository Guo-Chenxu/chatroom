package com.chatroom.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginByFace extends JFrame implements ActionListener  {

    public LoginByFace() {
        initComponents();
    }

    private void initComponents() {

        // 定义窗体的宽高
        int windowsWedth = 420;
        int windowsHeight = 380;

        // 获取此窗口容器 设置布局
        Container contentPane = this.getContentPane();
        contentPane.setLayout(null);

        // 创建最上方的JLabel
        JLabel jlbTop = new JLabel("人脸登录", JLabel.CENTER);
        jlbTop.setFont(new Font("", Font.BOLD, 18));
        jlbTop.setBounds(0, 0, windowsWedth, 30);
        jlbTop.setForeground(Color.white);
        jlbTop.setOpaque(true);
        jlbTop.setBackground(new Color(3, 37, 108));
        contentPane.add(jlbTop);


        this.setSize(windowsWedth, windowsHeight);
        setLocationRelativeTo(getOwner());
        this.setResizable(false);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        this.dispose();
    }
}
