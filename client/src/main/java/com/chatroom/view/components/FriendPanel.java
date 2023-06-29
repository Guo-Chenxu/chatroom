package com.chatroom.view.components;

import com.chatroom.entity.User;
import com.chatroom.utils.ChatViewManage;
import com.chatroom.view.ChatView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Ye peixin
 */
public class FriendPanel extends JPanel implements MouseListener {
    private final int panelHeight = 60;
    private final int panelWidth = 280;
    private JLabel avatar;
    private JLabel nickname;
    private JLabel sign;

    private User user;
    private User friend;

    public FriendPanel(User user, User friend) {
        this.user = user;
        this.friend = friend;

        // 好友面板
        this.setLayout(null);
        this.setBackground(Color.white);
        this.setSize(panelWidth, panelHeight);
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
        this.addMouseListener(this);

        // 头像
        avatar = new Avatar(Avatar.getPath(), 40, 40);
        avatar.setBounds(10, 10, 40, 40);

        // 昵称
        nickname = new JLabel();
        nickname.setBounds(60, 10, 210, 18);
        nickname.setForeground(new Color(0, 0, 0));
        nickname.setText(friend.getUsername());

        this.add(avatar);
        this.add(nickname);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // 检测鼠标右键单击
        if (e.getClickCount() == 2) {
            ChatView chatView = new ChatView(user, friend);
            System.out.println(chatView);
            ChatViewManage.addChatView(friend.getUsername(), chatView);
//            ChatViewManage.addChatFrame(friend.getQq(), chatView);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JPanel jPanel = (JPanel) e.getSource();
        jPanel.setBackground(new Color(220, 220, 220));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JPanel jPanel = (JPanel) e.getSource();
        jPanel.setBackground(Color.white);
    }
}
