package com.chatroom.view;

import com.chatroom.entity.User;
import com.chatroom.service.FriendsService;
import com.chatroom.service.impl.FriendsServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Ye peixin
 */
public class AddFriend extends JFrame {
    /**
     * 用户对象
     */
    private User user;
    private JLabel label1;
    private JTextField textField1;
    private JButton button1;

    public AddFriend(User user) {
        this.user = user;
        this.setSize(300, 150);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setVisible(true);

        label1 = new JLabel();
        textField1 = new JTextField();
        button1 = new JButton();

        //this
        setVisible(true);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //label1
        label1.setText("好友QQ号");
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(70, 35), label1.getPreferredSize()));
        contentPane.add(textField1);
        textField1.setBounds(140, 30, 200, textField1.getPreferredSize().height);

        //button1
        button1.setText("添加");
        contentPane.add(button1);
        button1.setBackground(new Color(3, 37, 108));
        button1.setForeground(Color.white);
        button1.setBounds(160, 75, 80, button1.getPreferredSize().height);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField1.getText();
                FriendsService friendsService = new FriendsServiceImpl();
                friendsService.addFriend(user.getUsername(), text);
                dispose();
            }
        });

        contentPane.setPreferredSize(new Dimension(400, 140));
        pack();
        setLocationRelativeTo(getOwner());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AddFriend(new User());
            }
        });
    }
}
