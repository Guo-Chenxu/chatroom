package com.chatroom.view;

import com.alibaba.fastjson2.JSON;
import com.chatroom.entity.Chat;
import com.chatroom.entity.Message;
import com.chatroom.entity.User;
import com.chatroom.service.UserService;
import com.chatroom.service.impl.UserServiceImpl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class Register extends JFrame implements ActionListener {


    public Register() {
        initComponents();
    }

    private void initComponents() {

        jlbSubmit = new JButton();
        jlbUserName = new JLabel();
        jlbPassWord = new JLabel();
        jlbConfirm = new JLabel();
        userName = new JTextField();
        passWord = new JPasswordField();
        confirm = new JPasswordField();

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

        //---- 提交按钮 ----
        jlbSubmit.setText("提交");
        contentPane.add(jlbSubmit);
        jlbSubmit.setBackground(new Color(3, 37, 108));
        jlbSubmit.setForeground(Color.white);
        jlbSubmit.setBounds(120, 240, 100, 35);
        jlbSubmit.addActionListener(this);

        //---- 用户名label ----
        jlbUserName.setText("用户名");
        jlbUserName.setHorizontalTextPosition(SwingConstants.CENTER);
        jlbUserName.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(jlbUserName);
        jlbUserName.setBounds(50, 90, 75, 15);

        //---- 密码label ----
        jlbPassWord.setText("密码");
        jlbPassWord.setHorizontalTextPosition(SwingConstants.CENTER);
        jlbPassWord.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(jlbPassWord);
        jlbPassWord.setBounds(50, 140, 75, 15);

        //---- 确认密码label ----
        jlbConfirm.setText("确认密码");
        jlbConfirm.setHorizontalTextPosition(SwingConstants.CENTER);
        jlbConfirm.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(jlbConfirm);
        jlbConfirm.setBounds(50, 190, 75, jlbConfirm.getPreferredSize().height);

        //---- 添加输入框 ----
        contentPane.add(userName);
        userName.setBounds(140, 85, 150, 27);
        contentPane.add(passWord);
        passWord.setBounds(140, 135, 150, 27);
        contentPane.add(confirm);
        confirm.setBounds(140, 185, 150, 27);

        contentPane.setPreferredSize(new Dimension(340, 300));
        pack();
        setLocationRelativeTo(getOwner());
        setResizable(false);
        setVisible(true);
    }


    private JButton jlbSubmit;
    private JLabel jlbUserName;
    private JLabel jlbPassWord;
    private JLabel jlbConfirm;
    private JTextField userName;
    private JPasswordField passWord;
    private JPasswordField confirm;


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == jlbSubmit) {
            String username = userName.getText();
            String pwd = String.valueOf(passWord.getPassword());
            String againPwd = String.valueOf(confirm.getPassword());
            // 正则表达式
            String pattern = "^[a-zA-Z0-9_]{6,20}$";
            if(username.matches(pattern) && pwd.matches(pattern)){
                //检查格式
                if (check(username, pwd, againPwd)) {
                    UserService userService = new UserServiceImpl();
                    // 检查与服务器的连接
                    if (userService.getClient() != null) {
                        Chat chat = userService.register(username, pwd);
                        Boolean flag = chat.getFlag();
                        Message msg = chat.getMessage();
                        User register = JSON.parseObject(msg.getContent(), User.class);
                        // 判断操作是否成功
                        if (flag) {
                            JOptionPane.showMessageDialog(this, "注册成功!\n您的QQ号为：" + register.getUsername());
                            this.dispose();
                        } else {
                            JOptionPane.showMessageDialog(this,  msg.getContent());
                        }
                        userService.myStop();
                    } else {
                        JOptionPane.showMessageDialog(this, "无法连接服务器!");
                    }
                }
            }else {
                JOptionPane.showMessageDialog(this, "请输入正确的QQ号和密码！\n(用户名和密码格式要求为 6-20 位的数字、字母和下划线)");
            }
        }
    }


    private Boolean check(String n,  String p, String ap) {
        if (n.equals("")) {
            JOptionPane.showMessageDialog(this, "昵称不能为空!","warning",JOptionPane.WARNING_MESSAGE);
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