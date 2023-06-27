package com.chatroom.view;

import com.chatroom.entity.Group;
import com.chatroom.entity.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author Ye peixin
 */
public class ShowGroupMember extends JFrame {
    private User user;
    private Group group;
    private JPanel panel1;
    private JLabel label1;
    private JLabel label2;
    private DefaultListModel<String> listModel;
    private JList<String> list;

    public ShowGroupMember(User user, Group group){
        this.user = user;
        this.group = group;

        panel1 = new JPanel();
        label1 = new JLabel();
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(list);

        this.setSize(300, 300);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setVisible(true);
        this.setBackground(Color.white);

        Container container = getContentPane();
        container.setLayout(null);

        container.add(scrollPane);

        label1.setText("群主：");
        label1.setBounds(new Rectangle(new Point(50,15), label1.getPreferredSize()));
        container.add(label1);

        label2.setText("群成员：");
        label2.setBounds(new Rectangle(new Point(80,30),label2.getPreferredSize()));
        container.add(label2);

        container.setPreferredSize(new Dimension(400, 180));
        pack();
        setLocationRelativeTo(getOwner());
    }

    public void showMembers(List<String> items){
        listModel.clear();

        for(String item: items){
            listModel.addElement(item);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ShowGroupMember(new User(), new Group());
            }
        });
    }
}
