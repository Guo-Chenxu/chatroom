package com.chatroom.view;

import com.chatroom.entity.User;
import com.chatroom.service.FriendsService;
import com.chatroom.service.impl.FriendsServiceImpl;
import com.chatroom.utils.ThreadManage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ye peixin
 */
public class CreateGroup extends JFrame {
    private User user;
    private JLabel label1;
    private JButton button1;
    private List<JCheckBox> checkBoxes;

    public CreateGroup(User user) throws InterruptedException {
        this.user = user;
        this.setSize(300, 220); // Increased height to accommodate checkboxes
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        label1 = new JLabel();
        button1 = new JButton();
        checkBoxes = new ArrayList<>();

        Container containerPane = getContentPane();
        containerPane.setLayout(null);

        label1.setText("请选择好友建立群聊");
        containerPane.add(label1);
        label1.setBounds(new Rectangle(new Point(70, 15), label1.getPreferredSize()));

        FriendsService friendsService = new FriendsServiceImpl();
        friendsService.getFriendList(user.getUsername());

        new Thread().sleep(100);

        List<String> list =  ThreadManage.getThread(user.getUsername()).getFriends();

        for(int i=0; i<list.size(); i++){
            JCheckBox checkBox = new JCheckBox("好友"+list.get(i));
            containerPane.add(checkBox);
            checkBox.setBounds(70, 35, 100, checkBox.getPreferredSize().height);
            checkBoxes.add(checkBox);
        }
        JCheckBox checkBox1 = new JCheckBox("好友1");
        containerPane.add(checkBox1);
        checkBox1.setBounds(70, 35, 100, checkBox1.getPreferredSize().height);
        checkBoxes.add(checkBox1);

        JCheckBox checkBox2 = new JCheckBox("好友2");
        containerPane.add(checkBox2);
        checkBox2.setBounds(70, 55, 100, checkBox2.getPreferredSize().height);
        checkBoxes.add(checkBox2);

        JCheckBox checkBox3 = new JCheckBox("好友3");
        containerPane.add(checkBox3);
        checkBox3.setBounds(70, 75, 100, checkBox3.getPreferredSize().height);
        checkBoxes.add(checkBox3);

        button1.setText("建立群聊");
        containerPane.add(button1);
        button1.setBackground(new Color(3, 37, 108));
        button1.setForeground(Color.white);
        button1.setBounds(160, 115, 100, button1.getPreferredSize().height);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> selectedOptions = new ArrayList<>();
                for (JCheckBox checkBox : checkBoxes) {
                    if (checkBox.isSelected()) {
                        selectedOptions.add(checkBox.getText());
                    }
                }
                System.out.println("选中的选项：");
                for (String option : selectedOptions) {
                    System.out.println(option);
                }
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
