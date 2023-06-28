package com.chatroom.view;

import com.chatroom.entity.Group;
import com.chatroom.entity.User;
import com.chatroom.service.FriendsService;
import com.chatroom.service.impl.FriendsServiceImpl;
import com.chatroom.service.impl.GroupServiceImpl;
import com.chatroom.utils.ThreadManage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InviteFriend extends JFrame {
    private User user;
    private Group group;
    private JLabel label1;
    /**
     * 添加 JComboBox
     */
    private JComboBox<String> comboBox1;
    private JButton button1;

    public InviteFriend(User user, Group group) throws InterruptedException {
        this.user = user;
        this.group = group;
        this.setSize(300, 200);  // 调整窗口高度以适应新的组件
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setVisible(true);

        label1 = new JLabel();
        comboBox1 = new JComboBox<>();  // 实例化 JComboBox
        button1 = new JButton();

        // 窗口设置
        setVisible(true);
        Container containerPane = getContentPane();
        containerPane.setLayout(null);

        // label1
        label1.setText("邀请好友加入群聊");
        containerPane.add(label1);
        label1.setBounds(new Rectangle(new Point(70, 35), label1.getPreferredSize()));

        // 设置下拉多选框的位置和宽度
        comboBox1.setBounds(new Rectangle(new Point(70, 60), new Dimension(150, comboBox1.getPreferredSize().height)));

//        FriendsService friendsService = new FriendsServiceImpl();
        FriendsServiceImpl.getInstance().getFriendList(user.getUsername());

        new Thread().sleep(100);

        //获取用户好友列表
        List<String> list = ThreadManage.getThread(user.getUsername()).getFriends();

        for (int i = 0; i < list.size(); i++) {
            comboBox1.addItem(list.get(i));
        }

        containerPane.add(comboBox1);

        // button1
        button1.setText("发送邀请");
        containerPane.add(button1);
        button1.setBackground(new Color(3, 37, 108));
        button1.setForeground(Color.white);
        button1.setBounds(160, 120, 100, button1.getPreferredSize().height);  // 调整按钮位置
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取选中的好友
                Object[] selectedFriends = comboBox1.getSelectedObjects();
                if (selectedFriends != null) {
                    for (Object friend : selectedFriends) {
                        System.out.println("选中好友：" + friend);
                    }
                } else {
                    System.out.println("未选择好友");
                }
                GroupServiceImpl.getInstance().addGroup(user.getUsername(), (String) comboBox1.getSelectedObjects()[0], group.getGroupName());
                dispose();
            }
        });

        containerPane.setPreferredSize(new Dimension(400, 160));  // 调整容器的大小
        pack();
        setLocationRelativeTo(getOwner());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new InviteFriend(new User(), new Group());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
