package com.chatroom.view;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.chatroom.controller.ClientConnectServerThread;
import com.chatroom.entity.Chat;
import com.chatroom.entity.Message;
import com.chatroom.entity.MessageType.*;
import com.chatroom.entity.User;
import com.chatroom.service.FriendsService;
import com.chatroom.service.UserService;
import com.chatroom.service.impl.FriendsServiceImpl;
import com.chatroom.service.impl.UserServiceImpl;
import com.chatroom.utils.ThreadManage;
import com.chatroom.view.components.Avatar;
import com.chatroom.view.components.FriendPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ye peixin
 */
public class FriendList extends JFrame{
    private static int windowsWedth = 280;
    private static int windowsHeight = 600;
    private static Container container;// 本窗口面板
    private static JPanel friendList;// 好友列表
    private static JScrollPane jScrollPane;
    private JPanel bottom;// 底部面板
    private JButton addFriend;// 添加好友
    private User user;// 用户对象
//    private List<String> list;
    public FriendList(User user){
        this.user = user;
        this.setSize(windowsWedth, windowsHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 本窗口面板
        container = this.getContentPane();
        container.setLayout(null);
        //用户头像
        JLabel jlbPhoto = new Avatar(user.getAvatarId(), 80, 80);
        jlbPhoto.setBounds(20, 22, 80, 80);
        container.add(jlbPhoto);
        //用户昵称
        JLabel jlbName = new JLabel(user.getUsername() + "(" + user.getId() + ")");
        jlbName.setForeground(Color.WHITE);
        jlbName.setFont(new Font("", Font.BOLD, 18));
        jlbName.setBounds(120, 30, 140, 20);
        container.add(jlbName);
        //
        JLabel jlbBackground = new JLabel();
        jlbBackground.setBackground(new Color(3, 37, 108));
        jlbBackground.setOpaque(true);
        jlbBackground.setBounds(0, 0, windowsWedth, 128);
        container.add(jlbBackground);
        //设置窗体居中
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setBounds((width - windowsWedth) / 2, (height - windowsHeight) / 2, 0, 0);
        this.setMinimumSize(new Dimension(windowsWedth, windowsHeight));
        //添加好友按钮
//        addFriend = new JButton("添加好友");
//        addFriend.setBackground(new Color(3,37,108));
//        addFriend.setForeground(Color.white);
//        addFriend.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new AddFriend();
//            }
//        });
        //窗体底部面板
        bottom = new JPanel(new GridLayout(1, 0));
        bottom.setBounds(0, 542, windowsWedth, 30);
        bottom.add(addFriend);
        container.add(bottom);
        //窗体关闭事件
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                super.windowClosing(e);
                //发送下线消息
                Message message = new Message();
//                message.setContent(user);
//                message.Content = '1';
            }
        });
        //设置窗体为可见
        this.setVisible(true);
        updateFriendList();
    }

    //向服务器请求好友列表
    public void updateFriendList(){
//        FriendsService friendsService = new FriendsServiceImpl();
//        Socket client = friendsService.getClient();
//        String userName = user.getUsername();
//        JSONObject myFriendList = null;
//
//        if(client!=null && !client.isClosed()){
//            Chat chat = friendsService.getFriendList(userName);
//            Message msg = chat.getMessage();
//            if(chat.getFlag()){
//                myFriendList = JSON.parseObject(msg.getContent());
//                ClientConnectServerThread thread = ThreadManage.getThread(user.getUsername());
//
//            }
//        }

//        Message message = new Message();
//        ThreadManage.getThread(user.getUsername()).send(message);

        FriendsService friendsService = new FriendsServiceImpl();
        friendsService.getFriendList(user.getUsername());

        friendList = new JPanel();
        friendList.setLayout(null);
        if(jScrollPane != null){
            container.remove(jScrollPane);
        }
        jScrollPane = new JScrollPane();
        jScrollPane.setLocation(0, 128);
        jScrollPane.setViewportView(friendList);
        jScrollPane.setSize(windowsWedth, windowsHeight - 184);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);// 需要时显示（默认）
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);// 从不显示
        container.add(jScrollPane);

        List<String> friends = ThreadManage.getThread(user.getUsername()).getFriends();
        showFriendList(friends);
    }

//    List<String> friends = ThreadManage.getThread(user.getUsername()).getFriends();
    public void showFriendList(List<String> list){

        int panelHeight = 60;
        friendList.removeAll();
        for(int i=0; i<list.size(); i++){
            User friend = new User();
            friend.setUsername(list.get(i));
//            String friend = list.get(i);
            // 创建好友面板
            JPanel friendPanel = new FriendPanel(this.user, friend);
            friendPanel.setLocation(0, i * panelHeight);
            friendList.add(friendPanel);
        }
        friendList.setPreferredSize(new Dimension(windowsWedth, list.size() * panelHeight));
        container.repaint();
    }
//    class AddFriend extends JFrame implements ActionListener{
//        public AddFriend(){
//            initComponents();
//        }
//        private void initComponents() {
//            label1 = new JLabel();
//            textField1 = new JTextField();
//            button1 = new JButton();
//            //======== this ========
//            setVisible(true);
//            Container contentPane = getContentPane();
//            contentPane.setLayout(null);
//            //---- label1 ----
//            label1.setText("好友账号");
//            contentPane.add(label1);
//            label1.setBounds(new Rectangle(new Point(70, 35), label1.getPreferredSize()));
//            contentPane.add(textField1);
//            textField1.setBounds(140, 30, 200, textField1.getPreferredSize().height);
//            //---- button1 ----
//            button1.setText("添加");
//            contentPane.add(button1);
//            button1.setBackground(new Color(3, 37, 108));
//            button1.setForeground(Color.white);
//            button1.setBounds(160, 75, 80, button1.getPreferredSize().height);
//            button1.addActionListener(this);
//            contentPane.setPreferredSize(new Dimension(400, 140));
//            pack();
//            setLocationRelativeTo(getOwner());
//            // JFormDesigner - End of component initialization  //GEN-END:initComponents
//        }
//        private JLabel label1;
//        private JTextField textField1;
//        private JButton button1;
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            String text = textField1.getText();
//            if (text.equals("")) {
//                JOptionPane.showMessageDialog(this, "请输入QQ号！");
//            } else if (text.length() > 4) {
//                JOptionPane.showMessageDialog(this, "请输入正确的QQ号！");
//            } else {
//                int fqq = Integer.parseInt(text);
////                Boolean aBoolean = new UserService().addFriend(user.getId(), fqq);
////                if (aBoolean) {
////                    JOptionPane.showMessageDialog(this, "添加成功！");
////                    this.dispose();
////                    updateFriendList();
////                } else {
////                    JOptionPane.showMessageDialog(this, "添加失败！");
////                }
//            }
//        }
//    }
}