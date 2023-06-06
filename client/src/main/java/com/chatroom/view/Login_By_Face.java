package com.chatroom.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login_By_Face extends JFrame implements ActionListener  {

    public Login_By_Face() {
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
        JLabel jlb_top = new JLabel("人脸登录", JLabel.CENTER);
        jlb_top.setFont(new Font("", Font.BOLD, 18));
        jlb_top.setBounds(0, 0, windowsWedth, 30);
        jlb_top.setForeground(Color.white);
        jlb_top.setOpaque(true);
        jlb_top.setBackground(new Color(3, 37, 108));
        contentPane.add(jlb_top);


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
