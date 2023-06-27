package com.chatroom.view;

import com.chatroom.entity.Message;
import com.chatroom.entity.User;
import com.chatroom.service.FriendsService;
import com.chatroom.service.impl.FriendsServiceImpl;
import com.chatroom.utils.ThreadManage;
import com.chatroom.view.components.FriendPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * @author Ye peixin
 */
public class FriendList extends JFrame {
    private static int windowsWedth = 280;
    private static int windowsHeight = 600;
    /**
     * 本窗口面板
     */
    private static Container container;
    /**
     * 好友列表
     */
    private static JPanel friendList;
    private static JScrollPane jScrollPane;
    /**
     * 底部面板
     */
    private JPanel bottom;
    private User user;// 用户对象

    //    private List<String> list;
    public FriendList(User user) {
        this.user = user;
        this.setSize(windowsWedth, windowsHeight);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        // 本窗口面板
        container = this.getContentPane();
        container.setLayout(null);
        //用户头像
//        JLabel jlbPhoto = new Avatar(user.getAvatarId(), 80, 80);
//        jlbPhoto.setBounds(20, 22, 80, 80);
//        container.add(jlbPhoto);
        //用户昵称
        JLabel jlbName = new JLabel(user.getUsername());
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

        //窗体底部面板
        bottom = new JPanel(new GridLayout(1, 0));
        bottom.setBounds(0, 542, windowsWedth, 30);

        container.add(bottom);
        //窗体关闭事件
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
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
    public void updateFriendList() {

        FriendsService friendsService = new FriendsServiceImpl();
        friendsService.getFriendList(user.getUsername());

        friendList = new JPanel();
        friendList.setLayout(null);
        if (jScrollPane != null) {
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

    public void showFriendList(List<String> list) {

        int panelHeight = 60;
        friendList.removeAll();
        for (int i = 0; i < list.size(); i++) {
            User friend = new User();
            friend.setUsername(list.get(i));
            // 创建好友面板
            JPanel friendPanel = new FriendPanel(this.user, friend);
            friendPanel.setLocation(0, i * panelHeight);
            friendList.add(friendPanel);
        }
        friendList.setPreferredSize(new Dimension(windowsWedth, list.size() * panelHeight));
        container.repaint();
    }
}