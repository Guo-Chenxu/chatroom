package com.chatroom.view;

import com.chatroom.view.components.Avatar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

public class Login extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JLabel jlb_title;
    private JLabel jlb_north;
    private JLabel jlb_photo;
    private JTextField userName;
    private JPasswordField passWord;
    private JButton btn_login;
    private JLabel jlb_register;
    private JLabel jlb_forget;
    private JLabel jlb_faceLogin;
    private JButton btn_faceLogin;
    public Login() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 定义窗体的宽高
        int windowsWedth = 420;
        int windowsHeight = 340;

        // 获取此窗口容器 设置布局
        Container contentPane = this.getContentPane();
        contentPane.setLayout(null);

        // 设置标题
        jlb_title = new JLabel("CHATROOM",JLabel.CENTER);
        jlb_title.setFont(new Font("", Font.BOLD, 48));
        jlb_title.setBounds((windowsWedth - 340) / 2, 20, 340, 100);
        jlb_title.setForeground(Color.white);
        contentPane.add(jlb_title, BorderLayout.CENTER);

        // 处理背景图片标签
        jlb_north = new JLabel();
        jlb_north.setOpaque(true);
        jlb_north.setBackground(new Color(3, 37, 108));
        jlb_north.setBounds(0, 0, windowsWedth, 150);
        contentPane.add(jlb_north);

        // 处理账号密码框左边图片标签
        jlb_photo = new Avatar("tx4012", 80, 80);
        jlb_photo.setBounds(25, 170, 80, 80);
        contentPane.add(jlb_photo);

        // 处理账号输入框
        userName = new JTextField();
        userName.setBounds((windowsWedth - 200) / 2 + 10, 170, 200, 30);
        String placeholder1 = "请输入账号";
        userName.setText(placeholder1);
        userName.setForeground(Color.GRAY);
        userName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //获取焦点时，清空提示内容
                String temp = userName.getText();
                if (temp.equals(placeholder1)) {
                    userName.setText("");
                    userName.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                //失去焦点时，没有输入内容，显示提示内容
                String temp = userName.getText();
                if (temp.equals("")) {
                    userName.setForeground(Color.GRAY);
                    userName.setText(placeholder1);
                }
            }
        });
        contentPane.add(userName);

        // 处理密码输入框
        passWord = new JPasswordField();
        passWord.setBounds((windowsWedth - 200) / 2 + 10, 220, 200, 30);
        String placeholder2 = "请输入密码";
        passWord.setEchoChar((char) 0);
        passWord.setText(placeholder2);
        passWord.setForeground(Color.GRAY);
        passWord.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //获取焦点时，清空提示内容
                String temp = String.valueOf(passWord.getPassword());
                if (temp.equals(placeholder2)) {
                    passWord.setText("");
                    passWord.setForeground(Color.BLACK);
                    passWord.setEchoChar('*');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                //失去焦点时，没有输入内容，显示提示内容
                String temp = String.valueOf(passWord.getPassword());
                if (temp.equals("")) {
                    passWord.setForeground(Color.GRAY);
                    passWord.setText(placeholder2);
                    passWord.setEchoChar((char) 0);
                }
            }
        });
        contentPane.add(passWord);

        // 注册账号
        jlb_register = new JLabel("<html><u>" + "注册账号" + "</u></html>");
        jlb_register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//设置鼠标样式
        jlb_register.setBounds(windowsWedth - 90, 170, 100, 30);
        jlb_register.setForeground(new Color(3, 37, 108));
        jlb_register.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Register();
            }
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        contentPane.add(jlb_register);
        // 人脸登录
        jlb_faceLogin = new JLabel("<html><u>人脸登录</u></html>");
        jlb_faceLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jlb_faceLogin.setBounds((windowsWedth - 90), 260, 100, 30);
        jlb_faceLogin.setForeground(new Color(3, 37, 108));
        jlb_faceLogin.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 进行人脸登录处理
                new Login_By_Face();
            }
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        contentPane.add(jlb_faceLogin);

        // 忘记密码
        jlb_forget = new JLabel("<html><u>" + "忘记密码" + "</u></html>");
        jlb_forget.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//设置鼠标样式
        jlb_forget.setBounds(windowsWedth - 90, 220, 200, 30);
        jlb_forget.setForeground(new Color(3, 37, 108));
        jlb_forget.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //new Forget();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        contentPane.add(jlb_forget);

        // 处理南部登录按钮
        btn_login = new JButton("登录");
        btn_login.setForeground(Color.black);
        btn_login.setBackground(new Color(108, 71, 3));
        btn_login.setBounds((windowsWedth - 200) / 2 + 10, 260, 200, 30);
        btn_login.addActionListener(this);
        contentPane.add(btn_login);

        this.setSize(windowsWedth, windowsHeight);// 设置窗体大小'
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);// 设置窗体可见
    }

    /**
     * 点击登录进行处理
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_login) {//点击登录
            // 获取账号密码
            String qq = userName.getText().trim();
            String pwd = new String(passWord.getPassword());

            // 正则表达式
            String regex = "^[a-zA-Z0-9_]{6,20}$";

            // 检查信息是否输入正确
            if (!qq.equals("请输入QQ号") && !pwd.equals("请输入密码") &&  qq.length() >= 6 && qq.length() <= 20 &&  Pattern.matches(regex, qq)) {


            } else {
                JOptionPane.showMessageDialog(this, "请输入正确的QQ号和密码！");
            }
        }
    }

}
