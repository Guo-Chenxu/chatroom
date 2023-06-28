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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ye peixin
 */
public class CreateGroup extends JFrame {
    private User user;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JButton button1;
    //群聊等级文本框
    private JTextField textField;
    //群聊名称文本框
    private JTextField textFieldGroupName;
    private List<JCheckBox> checkBoxes;

    public CreateGroup(User user) throws InterruptedException {
        this.user = user;
        this.setSize(300, 300); // Increased height to accommodate checkboxes
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setVisible(true);

        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        button1 = new JButton();
        checkBoxes = new ArrayList<>();
        textField = new JTextField();
        textFieldGroupName = new JTextField();

        Container containerPane = getContentPane();
        containerPane.setLayout(null);

        label1.setText("请选择好友建立群聊");
        containerPane.add(label1);
        label1.setBounds(new Rectangle(new Point(50, 15), label1.getPreferredSize()));

        label2.setText("请输入群聊等级（1-4）");
        containerPane.add(label2);
        label2.setBounds(new Rectangle(new Point(220, 60), label2.getPreferredSize()));

        label3.setText("请输入群聊名称");
        containerPane.add(label3);
        label3.setBounds(new Rectangle(new Point(220, 15), label3.getPreferredSize()));

//        FriendsService friendsService = new FriendsServiceImpl();
        FriendsServiceImpl.getInstance().getFriendList(user.getUsername());

        new Thread().sleep(100);

        List<String> list = ThreadManage.getThread(user.getUsername()).getFriends();

        for (int i = 0; i < list.size(); i++) {
            JCheckBox checkBox = new JCheckBox(list.get(i));
            containerPane.add(checkBox);
            checkBox.setBounds(50, 35 + (i * 20), 150, checkBox.getPreferredSize().height);
            checkBoxes.add(checkBox);
        }

        //输入群聊等级
        textField.setBounds(220, 85, 120, textField.getPreferredSize().height);
        containerPane.add(textField);

        //输入群聊名称
        textFieldGroupName.setBounds(220, 40, 120, textFieldGroupName.getPreferredSize().height);
        containerPane.add(textFieldGroupName);

        button1.setText("建立群聊");
        containerPane.add(button1);
        button1.setBackground(new Color(3, 37, 108));
        button1.setForeground(Color.white);
        button1.setBounds(150, 125, 100, button1.getPreferredSize().height);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> selectedOptions = new ArrayList<>();
                for (JCheckBox checkBox : checkBoxes) {
                    if (checkBox.isSelected()) {
                        selectedOptions.add(checkBox.getText());
                    }
                }
                selectedOptions.add(user.getUsername());
                Group group = new Group();
                group.setLeaderName(user.getUsername());
                group.setLevel(Integer.parseInt(textField.getText()));
                group.setGroupName(textFieldGroupName.getText());
                group.setUsers(selectedOptions);

//                GroupService groupService = new GroupServiceImpl();
                GroupServiceImpl.getInstance().setGroup(user.getUsername(), group);
//                groupService.setGroup(user.getUsername(), group);
//                new GroupServiceImpl().getGroups(user.getUsername());
                GroupServiceImpl.getInstance().getGroups(user.getUsername());
                dispose();
            }
        });

        containerPane.setPreferredSize(new Dimension(400, 180));
        pack();
        setLocationRelativeTo(getOwner());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new CreateGroup(new User());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
