package com.chatroom.view;

import com.chatroom.entity.Message;
import com.chatroom.entity.User;
import com.chatroom.view.FriendAddRequest;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.chatroom.entity.MessageType.*;

public class NotRead extends JFrame {
    private User user ;
    private List<Message> messageList ;
    private JPanel friendPanel; // 好友消息面板
    private JPanel groupPanel; // 群聊消息面板

    public NotRead(User user, List<Message> messageList) {
        this.user = user;
        this.messageList= messageList;
        initComponents();
    }

    private void initComponents() {
        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        // 创建最上方的JLabel
        JLabel jlbTop = new JLabel("未读消息", JLabel.CENTER);
        jlbTop.setFont(new Font("", Font.BOLD, 18));
        jlbTop.setForeground(Color.white);
        jlbTop.setOpaque(true);
        jlbTop.setBackground(new Color(3, 37, 108));
        contentPane.add(jlbTop, BorderLayout.NORTH);

        // 创建好友消息面板
        friendPanel = new JPanel();
        friendPanel.setLayout(new GridLayout(10, 1));
        friendPanel.setBackground(Color.white);
        JScrollPane friendScrollPane = new JScrollPane(friendPanel);
        friendScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        friendScrollPane.setPreferredSize(new Dimension(220, 400));
        contentPane.add(friendScrollPane, BorderLayout.WEST);

        // 创建群聊消息面板
        groupPanel = new JPanel();
        groupPanel.setLayout(new GridLayout(10, 1));
        groupPanel.setBackground(Color.white);
        JScrollPane groupScrollPane = new JScrollPane(groupPanel);
        groupScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        groupScrollPane.setPreferredSize(new Dimension(220, 400));
        contentPane.add(groupScrollPane, BorderLayout.EAST);

        // 添加"好友消息"标签
        JLabel friendLabel = new JLabel("好友消息", JLabel.CENTER);
        friendLabel.setFont(new Font("", Font.BOLD, 16));
        friendLabel.setForeground(Color.white);
        friendLabel.setOpaque(true);
        friendLabel.setBackground(new Color(0, 0, 0));
        friendScrollPane.setColumnHeaderView(friendLabel);

        // 添加"群聊消息"标签
        JLabel groupLabel = new JLabel("群聊消息", JLabel.CENTER);
        groupLabel.setFont(new Font("", Font.BOLD, 16));
        groupLabel.setForeground(Color.white);
        groupLabel.setOpaque(true);
        groupLabel.setBackground(new Color(0, 0, 0));
        groupScrollPane.setColumnHeaderView(groupLabel);

        // 设置窗口关闭操作为隐藏窗口而不是退出应用程序
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // 添加窗口关闭事件监听器
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false); // 隐藏窗口
            }
        });

        // 设置窗口属性
        pack();
        setLocationRelativeTo(getOwner());
        setResizable(false);
        setVisible(true);
    }

    public void showNotRead(){
        List<Message> list = this.messageList;
        for (Message msg : list) {
            // 未读好友消息
            if(Objects.equals(msg.getMessageType(), COMMON_MESSAGE)){
                String sender = msg.getSenderName();
                String content = msg.getContent();
                Date time = msg.getSendTime();
                addFriendMessage(sender, content, time); // 添加好友消息到好友消息面板
                msg.setIsRead(true);
            }
            else if(Objects.equals(msg.getMessageType(), GROUP_MESSAGE)){
                // 未读群聊消息
                String sender = msg.getSenderName();
                String content = msg.getContent();
                Date time = msg.getSendTime();
                addGroupMessage(sender,content,time);
                msg.setIsRead(true);
            }
            else if(Objects.equals(msg.getMessageType(), ADD_FRIEND)){
                // 好友请求
                new FriendAddRequest(msg.getSenderName(),user.getUsername());
                msg.setIsRead(true);
            }
        }
        // 添加“没有未读消息”标签
        if (friendPanel.getComponentCount() == 0)
            addNoUnreadMessagesLabel(friendPanel);
        if(groupPanel.getComponentCount() == 0)
            addNoUnreadMessagesLabel(groupPanel);
    }

    // 添加“没有未读消息”标签
    private void addNoUnreadMessagesLabel(JPanel panel) {
        JLabel noUnreadLabel = new JLabel("没有未读消息", JLabel.CENTER);
        noUnreadLabel.setFont(new Font("", Font.BOLD, 16));
        noUnreadLabel.setForeground(Color.gray);
        noUnreadLabel.setOpaque(true);
        noUnreadLabel.setBackground(Color.white);
        panel.add(noUnreadLabel);
    }

    // 添加好友消息到好友消息面板
    private void addFriendMessage(String sender, String message, Date time) {
        JTextPane messagePane = createMessagePane(sender, message, time);
        friendPanel.add(messagePane);
        friendPanel.revalidate();
        friendPanel.repaint();

    }

    // 添加群聊消息到群聊消息面板
    private void addGroupMessage(String sender, String message, Date time) {
        JTextPane messagePane = createMessagePane(sender, message, time);
        groupPanel.add(messagePane);
        groupPanel.revalidate();
        groupPanel.repaint();
    }

    private JTextPane createMessagePane(String sender, String message, Date time) {
        JTextPane messagePane = new JTextPane();
        messagePane.setPreferredSize(new Dimension(200, 60));
        messagePane.setEditable(false);

        StyledDocument doc = messagePane.getStyledDocument();

        Style senderStyle = doc.addStyle("SenderStyle", null);
        StyleConstants.setBold(senderStyle, true);

        Style messageStyle = doc.addStyle("MessageStyle", null);

        Style timeStyle = doc.addStyle("TimeStyle", null);
        StyleConstants.setAlignment(timeStyle, StyleConstants.ALIGN_RIGHT);

        insertText(doc, sender + "\n", senderStyle);
        insertText(doc, message + "\n", messageStyle);
        insertText(doc, time.toString() + "\n", timeStyle);

        return messagePane;
    }

    private void insertText(StyledDocument doc, String text, Style style) {
        try {
            doc.insertString(doc.getLength(), text, style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                User user1 = new User();
                List<Message> list = new ArrayList<>();
                NotRead test = new NotRead(user1, list);
                test.showNotRead();

            }
        });
    }
}
