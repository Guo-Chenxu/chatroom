package com.chatroom.view;

import com.chatroom.entity.Message;
import com.chatroom.entity.User;

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

import static com.chatroom.entity.MessageType.ADD_FRIEND;
import static com.chatroom.entity.MessageType.COMMON_MESSAGE;

public class NotRead extends JFrame {
    private final User user;
    private final List<Message> messageList;
    private JPanel friendPanel; // 好友消息面板

    public NotRead(User user, List<Message> messageList) {
        this.user = user;
        this.messageList = messageList;
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
        friendScrollPane.setPreferredSize(new Dimension(270, 440));
        contentPane.add(friendScrollPane, BorderLayout.WEST);

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false); // 隐藏窗口
            }
        });

        pack();
        setLocationRelativeTo(getOwner());
        setResizable(false);
        setVisible(true);
    }

    public void showNotRead() {
        for (Message msg : this.messageList) {
            // 未读好友消息
            if (Objects.equals(msg.getMessageType(), COMMON_MESSAGE)) {
                String sender = msg.getSenderName();
                String content = msg.getContent();
                Date time = msg.getSendTime();
                addFriendMessage(sender, content, time); // 添加好友消息到好友消息面板
                msg.setIsRead(true);
            } else if (Objects.equals(msg.getMessageType(), ADD_FRIEND)) {
                // 好友请求
                new FriendAddRequest(msg.getSenderName(), user.getUsername());
                msg.setIsRead(true);
            }
        }
        // 添加“没有未读消息”标签
        if (friendPanel.getComponentCount() == 0)
            addNoUnreadMessagesLabel(friendPanel);
    }

    // 添加“没有未读消息”标签
    private void addNoUnreadMessagesLabel(JPanel panel) {
        JLabel noUnreadLabel = new JLabel("没有未读消息", JLabel.CENTER);
        noUnreadLabel.setFont(new Font("", Font.BOLD, 18));
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

    private JTextPane createMessagePane(String sender, String message, Date time) {
        JTextPane messagePane = new JTextPane();
        messagePane.setPreferredSize(new Dimension(400, 60));
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
