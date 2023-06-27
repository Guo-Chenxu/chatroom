package com.chatroom.view.components;

import com.chatroom.utils.GroupChatViewManage;
import com.chatroom.entity.Group;
import com.chatroom.entity.User;
import com.chatroom.view.GroupChatView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Ye peixin
 */
public class GroupPanel extends JPanel implements MouseListener {
    private final int panelHeight = 60;
    private final int panelWidth = 280;
    private JLabel groupName;
    private User user;
    private Group group;

    public GroupPanel(User user, Group group){
        this.user = user;
        this.group = group;

        //群聊面板
        this.setLayout(null);
        this.setBackground(Color.white);
        this.setSize(panelWidth, panelHeight);
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
        this.addMouseListener(this);

        //群聊名称
        groupName = new JLabel();
        groupName.setBounds(60, 10, 210, 18);
        groupName.setForeground(new Color(0, 0, 0));
        groupName.setText(group.getGroupName()+")"+"\r\n  Level" +group.getLevel());

        this.add(groupName);
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if (e.getClickCount() == 2) {
            GroupChatView groupChatView = new GroupChatView(user, group);
            System.out.println(groupChatView);
            GroupChatViewManage.addGroupChatView(group.getGroupName(), groupChatView);
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
