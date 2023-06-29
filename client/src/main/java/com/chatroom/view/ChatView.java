package com.chatroom.view;

import com.chatroom.entity.Message;
import com.chatroom.entity.User;
import com.chatroom.service.impl.FriendMessageServiceImpl;
import com.chatroom.service.impl.FriendsServiceImpl;
import com.chatroom.utils.ChatViewManage;
import com.chatroom.view.components.Avatar;
import com.chatroom.view.components.ChatBubble;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


/**
 * @author Ye peixin
 */
public class ChatView extends JFrame implements ActionListener, WindowListener {
    private User user;
    private User friend;

    public ChatView(User u, User f) {
        this.user = u;
        this.friend = f;
        initComponents();
        getChatList();
    }

    /**
     * 获取聊天记录
     */
    public void getChatList() {
//        MessageService friendMessageService = new FriendMessageServiceImpl();
//        friendMessageService.getMessages(user.getUsername(), friend.getUsername());
        FriendMessageServiceImpl.getInstance().getMessages(user.getUsername(), friend.getUsername());
    }

    /**
     * 显示聊天记录
     */
    public void showChats(ArrayList<Message> list) {
        textPane1.removeAll();
        for (Message message : list) {
            String senderName = (Objects.equals(message.getSenderName(), user.getUsername())) ? user.getUsername() : friend.getUsername();
            String content = message.getContent();
            ChatBubble chatBubble = new ChatBubble(senderName, message.getSendTime(), content);
            textPane1.add(chatBubble);
        }
        textPane1.updateUI();
        scrollToBottom();
    }

    /**
     * 聊天内容区域滚动到底部
     */
    public void scrollToBottom() {
        JScrollBar verticalScrollBar = scrollPane1.getVerticalScrollBar();
        // 不用SwingUtilities.invokeLater的话会报错
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                verticalScrollBar.updateUI();
                verticalScrollBar.setValue(verticalScrollBar.getMaximum());
            }
        });
    }

    /**
     * 接收实时聊天消息
     */
    public void receiveChat(Message msg) {
        String senderName = Objects.equals(msg.getSenderName(), user.getUsername()) ? user.getUsername() : friend.getUsername();
        String content = msg.getContent();
        ChatBubble chatBubble = new ChatBubble(senderName, msg.getSendTime(), content);
        textPane1.add(chatBubble);
        textPane1.updateUI();
        scrollToBottom();
    }

    private void initComponents() {

        label1 = new Avatar(Avatar.getPath(), 60, 60);
        panel1 = new JPanel();
        label2 = new JLabel();
        label3 = new JLabel();
        scrollPane1 = new JScrollPane();
        textPane1 = new JPanel();
        panel2 = new JPanel();
        button1 = new JButton();
        button2 = new JButton();
        scrollPane2 = new JScrollPane();
        textPane2 = new JTextPane();
        panel3 = new JPanel();
        button3 = new JButton();
        button4 = new JButton();
        button5 = new JButton();

        //======== this ========
        setVisible(true);
        setBackground(new Color(204, 204, 204));
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        this.addWindowListener(this);

        //======== panel1 ========
        {
            panel1.setBackground(new Color(3, 37, 108));
            panel1.setLayout(null);

            //---- label1 ----
            panel1.add(label1);
            label1.setBounds(10, 10, 60, 60);

            //---- label2 ----
            label2.setText(friend.getUsername());
            label2.setFont(new Font(".AppleSystemUIFontMonospaced", Font.PLAIN, 22));
            label2.setForeground(Color.white);
            panel1.add(label2);
            label2.setBounds(80, 14, 300, label2.getPreferredSize().height);

            //---- label3 ----
//            label3.setText(friend.getSign());
            label3.setForeground(new Color(204, 204, 204));
            panel1.add(label3);
            label3.setBounds(80, 46, 300, label3.getPreferredSize().height);
        }
        contentPane.add(panel1);
        panel1.setBounds(0, 0, 400, 80);

        //======== scrollPane1 ========
        {
            textPane1.setLayout(new GridLayout(0, 1));
            scrollPane1.setViewportView(textPane1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(0, 80, 400, 200);

        //======== panel2 ========
        {
            panel2.setLayout(null);


            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for (int i = 0; i < panel2.getComponentCount(); i++) {
                    Rectangle bounds = panel2.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel2.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel2.setMinimumSize(preferredSize);
                panel2.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(panel2);
        panel2.setBounds(0, 280, 400, 30);

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(textPane2);
        }
        contentPane.add(scrollPane2);
        scrollPane2.setBounds(0, 310, 400, 120);

        //======== panel3 ========
        {
            panel3.setLayout(null);

            //删除好友按钮
            button5.setText("删除好友");
            button5.setBackground(new Color(3, 37, 108));
            button5.setForeground(Color.white);
            panel3.add(button5);
            button5.setBounds(115, 5, 110, 30);
            button5.addActionListener(this);

            //---- 发送按钮 ----
            button3.setText("发送");
            button3.setBackground(new Color(3, 37, 108));
            button3.setForeground(Color.white);
            panel3.add(button3);
            button3.setBounds(315, 5, 80, 30);
            button3.addActionListener(this);

            //---- 关闭按钮 ----
            button4.setText("关闭");
            button4.addActionListener(this);
            panel3.add(button4);
            button4.setBounds(230, 5, 80, 30);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for (int i = 0; i < panel3.getComponentCount(); i++) {
                    Rectangle bounds = panel3.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel3.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel3.setMinimumSize(preferredSize);
                panel3.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(panel3);
        panel3.setBounds(0, 430, 400, 40);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for (int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }


    private JPanel panel1;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private static JScrollPane scrollPane1;
    private JPanel textPane1;
    private JPanel panel2;
    private JButton button1;
    private JButton button2;
    private JScrollPane scrollPane2;
    private JTextPane textPane2;
    private JPanel panel3;
    private JButton button3;
    private JButton button4;
    private JButton button5;

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        //关闭按钮
        if (source == button4) {
            //将该窗口从哈希表中删除
            ChatViewManage.removeChatView(friend.getUsername());
            this.dispose();
        } else if (source == button3) {
            //发送按钮
            if (!textPane2.getText().equals("")) {
                String text = textPane2.getText();
                System.out.println(text);
                textPane2.removeAll();
//                MessageService friendMessageService = new FriendMessageServiceImpl();
                FriendMessageServiceImpl.getInstance().sendMessage(user.getUsername(), friend.getUsername(), text);
                ChatBubble chatBubble = new ChatBubble(user.getUsername(), new Date(), text);
                textPane1.add(chatBubble);
                textPane1.updateUI();
                scrollToBottom();
                textPane2.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "消息不能为空！");
            }
        } else if (source == button5) {
            //删除好友
//            FriendsService friendsService = new FriendsServiceImpl();
            FriendsServiceImpl.getInstance().deleteFriend(user.getUsername(), friend.getUsername());
            this.dispose();
        }
    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
//        ChatViewManage.removeChatFrame(friend.getId());
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ChatView(new User(), new User());
            }
        });
    }
}
