package com.chatroom.view.components;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * @author Ye peixin
 */
public class ChatBubble extends JPanel{
    private String senderName;
    private Date time;
    private String content;
    private JLabel label1;
    private JLabel label2;

    public ChatBubble(String senderName, Date time, String content){
        this.senderName = senderName;
        this.time = time;
        this.content = content;
        //初始化
        initComponents();
    }

    public void initComponents(){
        label1 = new JLabel();
        label2 = new JLabel();

        //this
        setLayout(null);

        //label1
        label1.setText(senderName + "["+time.toLocaleString()+"]");
        label1.setBounds(0,0,300, label1.getPreferredSize().height);
        add(label1);

        //label2
        label2.setText(content);
        label2.setFont(new Font(".AppleSystemUIFontMonospaced", Font.PLAIN, 16));
        label2.setBounds(0,20,300, 20);
        add(label2);

        setPreferredSize(new Dimension(300, 40));
    }
}
