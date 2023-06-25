package com.chatroom.view;

import com.chatroom.controller.ClientConnectServerThread;
import com.chatroom.entity.Chat;
import com.chatroom.entity.Message;
import com.chatroom.entity.User;
import com.chatroom.service.UserService;
import com.chatroom.service.impl.UserServiceImpl;
import com.chatroom.utils.ThreadManage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.Socket;
import java.util.List;


public class Login extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JLabel jlbTitle;
    private JLabel jlbNorth;
    private JLabel jlbUserName;
    private JLabel jlbPwd;
    private JTextField userName;
    private JPasswordField passWord;
    private JButton btnLogin;
    private JLabel jlbRegister;
    private JLabel jlbForget;
    private JLabel jlbFaceLogin;

    public Login() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 定义窗体的宽高
        int windowsWedth = 420;
        int windowsHeight = 340;

        // 获取此窗口容器 设置布局
        Container contentPane = this.getContentPane();
        contentPane.setLayout(null);

        // 设置标题
        jlbTitle = new JLabel("CHATROOM",JLabel.CENTER);
        jlbTitle.setFont(new Font("", Font.BOLD, 48));
        jlbTitle.setBounds((windowsWedth - 340) / 2, 20, 340, 100);
        jlbTitle.setForeground(Color.white);
        contentPane.add(jlbTitle, BorderLayout.CENTER);

        // 处理背景图片标签
        jlbNorth = new JLabel();
        jlbNorth.setOpaque(true);
        jlbNorth.setBackground(new Color(3, 37, 108));
        jlbNorth.setBounds(0, 0, windowsWedth, 150);
        contentPane.add(jlbNorth);

        // 处理用户名标签
        jlbUserName = new JLabel("用户名");
        jlbUserName.setBounds((windowsWedth - 280) / 2, 170, 200, 30);
        contentPane.add(jlbUserName);

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

        // 处理密码标签
        jlbPwd = new JLabel("密码");
        jlbPwd.setBounds((windowsWedth - 270) / 2, 220, 200, 30);
        contentPane.add(jlbPwd);

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
        jlbRegister = new JLabel("<html><u>" + "注册账号" + "</u></html>");
        jlbRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//设置鼠标样式
        jlbRegister.setBounds(windowsWedth - 90, 170, 100, 30);
        jlbRegister.setForeground(new Color(3, 37, 108));
        jlbRegister.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Register();
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        contentPane.add(jlbRegister);

        // 人脸登录
        jlbFaceLogin = new JLabel("<html><u>人脸登录</u></html>");
        jlbFaceLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jlbFaceLogin.setBounds((windowsWedth - 90), 260, 100, 30);
        jlbFaceLogin.setForeground(new Color(3, 37, 108));
        jlbFaceLogin.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 进行人脸登录处理
                new LoginByFace();
            }
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        contentPane.add(jlbFaceLogin);

        // 忘记密码
        jlbForget = new JLabel("<html><u>" + "忘记密码" + "</u></html>");
        jlbForget.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//设置鼠标样式
        jlbForget.setBounds(windowsWedth - 90, 220, 200, 30);
        jlbForget.setForeground(new Color(3, 37, 108));
        jlbForget.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Forget();
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        contentPane.add(jlbForget);

        // 处理南部登录按钮
        btnLogin = new JButton("登录");
        btnLogin.setForeground(Color.white);
        btnLogin.setBackground(new Color(3, 17, 108));
        btnLogin.setBounds((windowsWedth - 200) / 2 + 10, 260, 200, 30);
        btnLogin.addActionListener(this);
        contentPane.add(btnLogin);

        this.setSize(windowsWedth, windowsHeight);// 设置窗体大小
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);// 设置窗体可见
    }

    /**
     * 点击登录进行处理
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {//点击登录
            // 获取账号密码
            String username = userName.getText().trim();
            String pwd = new String(passWord.getPassword());
            User loginUser = new User(username, pwd);
            // 正则表达式
            String pattern = "^[a-zA-Z0-9_]{6,20}$";

            // 检查信息是否输入正确
            if (!username.equals("请输入用户名") && !pwd.equals("请输入密码")
                    && username.matches(pattern) && pwd.matches(pattern)) {
                // 检查与服务器的连接
                UserService userService = new UserServiceImpl();
                Socket client = userService.getClient();
                if (client != null && !client.isClosed()) {
                    // 将登录消息发送至服务器
                    Chat chat = userService.loginByPwd(username, pwd);
                    Boolean flag = chat.getFlag();
                    Message msg = chat.getMessage();
                    // 判断操作是否成功
                    if(flag){
                        // 处理服务器返回的结果
                        // 登录成功
                        // 创建与服务器通信的线程
                        String jsonContent = msg.getContent();
                        ObjectMapper objectMapper = new ObjectMapper();
                        List<Message> messageList = null;
                        try {
                            messageList = objectMapper.readValue(jsonContent, new TypeReference<List<Message>>(){});
                        } catch (JsonProcessingException ex) {
                            throw new RuntimeException(ex);
                        }

                        ClientConnectServerThread clientThread = new ClientConnectServerThread(username,userService.getClient());
                        clientThread.start();
                        ThreadManage.addThread(username, clientThread);

                        // 将用户信息和好友列表控件保存至通信线程中
                        ClientConnectServerThread thread = ThreadManage.getThread(username);
                        FriendList friendList = new FriendList(loginUser);// 创建好友列表主界面
                        thread.setFriendList(friendList);
                        thread.setUser(loginUser);
                        friendList.updateFriendList();// 获取好友列表
                        NotRead unReadList = new NotRead(loginUser,messageList);

                        this.dispose();// 关闭登录界面

                    } else {
                        JOptionPane.showMessageDialog(this, msg.getContent());
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "无法连接服务器！");
                }
            } else {
                JOptionPane.showMessageDialog(this, "请输入正确的QQ号和密码！");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Login();
            }
        });
    }
}
