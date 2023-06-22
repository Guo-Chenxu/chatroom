package com.chatroom.view;

import com.chatroom.entity.User;
import com.chatroom.service.impl.UserServiceImpl;
import com.chatroom.view.components.Avatar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Register extends JFrame implements ActionListener {


    public Register() {
        initComponents();
    }

    private void initComponents() {

        submit = new JButton();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        textField1 = new JTextField();
        textField2 = new JTextField();
        textField3 = new JPasswordField();
        textField4 = new JPasswordField();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(null);


        // 创建最上方的JLabel
        JLabel jlbTop = new JLabel("注册", JLabel.CENTER);
        jlbTop.setFont(new Font("", Font.BOLD, 18));
        jlbTop.setBounds(0, 0, 340, 30);
        jlbTop.setForeground(Color.white);
        jlbTop.setOpaque(true);
        jlbTop.setBackground(new Color(3, 37, 108));
        contentPane.add(jlbTop);

        //---- button1 ----
        submit.setText("提交");
        contentPane.add(submit);
        submit.setBackground(new Color(3, 37, 108));
        submit.setForeground(Color.black);
        submit.setBounds(120, 240, 100, 35);
        submit.addActionListener(this);

        //---- label1 ----
        label1.setText("昵称");
        label1.setHorizontalTextPosition(SwingConstants.CENTER);
        label1.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(label1);
        label1.setBounds(50, 90, 75, label1.getPreferredSize().height);

        //---- label3 ----
        label3.setText("密码");
        label3.setHorizontalTextPosition(SwingConstants.CENTER);
        label3.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(label3);
        label3.setBounds(50, 140, 75, 15);

        //---- label4 ----
        label4.setText("确认密码");
        label4.setHorizontalTextPosition(SwingConstants.CENTER);
        label4.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(label4);
        label4.setBounds(50, 190, 75, label4.getPreferredSize().height);


        contentPane.add(textField1);
        textField1.setBounds(140, 85, 150, textField1.getPreferredSize().height);
        contentPane.add(textField3);
        textField3.setBounds(140, 135, 150, 27);
        contentPane.add(textField4);
        textField4.setBounds(140, 185, 150, 27);

        contentPane.setPreferredSize(new Dimension(340, 300));
        pack();
        setLocationRelativeTo(getOwner());
        setResizable(false);
        setVisible(true);
    }

    private JButton submit;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private Avatar label5;
    private JButton button2;
    private JTextField textField1;
    private JTextField textField2;
    private JPasswordField textField3;
    private JPasswordField textField4;

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == submit) {
            String nickName = textField1.getText();
            String sign = textField2.getText();
            String pwd = String.valueOf(textField3.getPassword());
            String againPwd = String.valueOf(textField4.getPassword());
            if (check(nickName, sign, pwd, againPwd)) {
                UserServiceImpl userService = new UserServiceImpl();
                if (userService.getClient() != null) {
                    //User register = userService.register(avatarID, nickName, sign, pwd);
//                    if (register != null) {
//                        JOptionPane.showMessageDialog(this, "注册成功!\n您的QQ号为：" + register.getQq());
//                        this.dispose();
//                    }
                    userService.myStop();
                } else {
                    JOptionPane.showMessageDialog(this, "无法连接服务器!");
                }
            }
        }
    }

    private Boolean check(String n, String s, String p, String ap) {
        if (n.equals("")) {
            JOptionPane.showMessageDialog(this, "昵称不能为空!","warning",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (s.equals("")) {
            JOptionPane.showMessageDialog(this, "个性签名不能为空!","warning",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (p.equals("") || ap.equals("")) {
            JOptionPane.showMessageDialog(this, "密码不能为空，请重新输入！","warning",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!p.equals(ap)) {
            JOptionPane.showMessageDialog(this, "密码输入错误，请重新输入！","warning",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Register();
            }
        });
    }
}