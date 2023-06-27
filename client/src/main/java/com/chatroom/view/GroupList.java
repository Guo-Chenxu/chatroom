package com.chatroom.view;

import com.chatroom.entity.Group;
import com.chatroom.entity.User;
import com.chatroom.service.GroupService;
import com.chatroom.service.impl.GroupServiceImpl;
import com.chatroom.utils.ThreadManage;
import com.chatroom.view.components.GroupPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * @author Ye peixin
 */
public class GroupList extends JFrame {
    private static int windowsWedth = 280;
    private static int windowsHeight = 600;
    /**
     * 本窗口面板
     */
    private static Container container;
    /**
     * 群聊列表
     */
    private static JPanel groupList;
    private static JScrollPane jScrollPane;
    /**
     * 底部面板
     */
    private JPanel bottom;
    /**
     * 用户对象
     */
    private User user;

    public GroupList(User user) {
        this.user = user;
        this.setSize(windowsWedth, windowsHeight);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        //窗口面板
        container = this.getContentPane();
        container.setLayout(null);
        //用户昵称
        JLabel jlbName = new JLabel(user.getUsername());
        jlbName.setForeground(Color.white);
        jlbName.setFont(new Font("", Font.BOLD, 18));
        jlbName.setBounds(120, 30, 140, 20);
        container.add(jlbName);
        //上半部分背景
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
        //设置窗体可见
        this.setVisible(true);
        updateGroupList();
    }

    /**
     * 请求群组列表
     */
    public void updateGroupList() {
        GroupService groupService = new GroupServiceImpl();
        groupService.getGroups(user.getUsername());

        groupList = new JPanel();
        groupList.setLayout(null);
        if (jScrollPane != null) {
            container.remove(jScrollPane);
        }
        jScrollPane = new JScrollPane();
        jScrollPane.setLocation(0, 128);
        jScrollPane.setViewportView(groupList);
        jScrollPane.setSize(windowsWedth, windowsHeight - 184);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        container.add(jScrollPane);

        List<String> groups = ThreadManage.getThread(user.getUsername()).getGroups();
        showGroupList(groups);
    }

    public void showGroupList(List<String> list) {
        int panelHeight = 60;
        groupList.removeAll();
        for (int i = 0; i < list.size(); i++) {
            Group group = new Group();
            group.setGroupName(list.get(i));
            group.setLevel(new Random().nextInt(4) + 1);
            //创建群聊面板
            JPanel groupPanel = new GroupPanel(this.user, group);
            groupPanel.setLocation(0, i * panelHeight);
            groupList.add(groupPanel);
        }
        groupList.setPreferredSize(new Dimension(windowsWedth, list.size() * panelHeight));
        container.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GroupList(new User());
            }
        });
    }
}
