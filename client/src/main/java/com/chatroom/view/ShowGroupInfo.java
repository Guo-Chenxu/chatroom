package com.chatroom.view;

import com.chatroom.entity.Group;
import com.chatroom.entity.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ye peixin
 */
public class ShowGroupInfo extends JFrame {
    private User user;
    private Group group;
    private List<String> members;
    private JPanel panel1;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private DefaultListModel<String> listModel;
    private JList<String> list;
    private Container container = getContentPane();

    public ShowGroupInfo(List<String> info) {
        panel1 = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        label5 = new JLabel();
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(new Rectangle(new Point(130, 30), scrollPane.getPreferredSize()));

        this.setSize(300, 300);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setVisible(true);
        this.setBackground(Color.white);

        container.setLayout(null);

        container.add(scrollPane);

        label1.setText("群主：");
        label1.setBounds(new Rectangle(new Point(50, 15), label1.getPreferredSize()));
        container.add(label1);

        label4.setText("群聊等级：");
        label4.setBounds(new Rectangle(new Point(50, 60), label4.getPreferredSize()));
        container.add(label4);

        label2.setText("群成员：");
        label2.setBounds(new Rectangle(new Point(130, 15), label2.getPreferredSize()));
        container.add(label2);

        container.setPreferredSize(new Dimension(400, 180));
        pack();
        setLocationRelativeTo(getOwner());

        showLeader(info);
        showLevel(info.get(info.size() - 1));
        showMembers(info);
    }

    private void showLeader(List<String> items) {
        label3.setText(items.get(items.size() - 2));
        label3.setBounds(new Rectangle(new Point(50, 35), label3.getPreferredSize()));
        container.add(label3);
    }

    private void showLevel(String level) {
        label5.setText(level);
        label5.setBounds(new Rectangle(new Point(50, 80), label5.getPreferredSize()));
        container.add(label5);
    }

    private void showMembers(List<String> items) {
        listModel.clear();
        for (int i = 0; i < items.size() - 2; i++) {
            listModel.addElement(items.get(i));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                List<String> l = new ArrayList<>();
                l.add("123");
                l.add("321");
                l.add("1");
                new ShowGroupInfo(l);
            }
        });
    }
}
