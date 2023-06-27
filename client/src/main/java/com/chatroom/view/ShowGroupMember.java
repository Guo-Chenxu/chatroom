package com.chatroom.view;

import com.chatroom.entity.Group;
import com.chatroom.entity.User;

import javax.swing.*;

/**
 * @author Ye peixin
 */
public class ShowGroupMember extends JFrame {
    private User user;
    private Group group;
    private JLabel label1;

    public ShowGroupMember(User user, Group group){
        this.user = user;
        this.group = group;
    }
}
