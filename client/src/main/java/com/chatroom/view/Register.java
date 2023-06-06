package com.chatroom.view;

import com.chatroom.view.components.Avatar;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Register extends JFrame implements ActionListener {

    private String avatarID = "tx4012";

    public Register() {
        initComponents();
    }

    private void initComponents() {

        button1 = new JButton();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        label5 = new Avatar("tx4012", 70, 70);
        button2 = new JButton();
        textField1 = new JTextField();
        textField2 = new JTextField();
        textField3 = new JPasswordField();
        textField4 = new JPasswordField();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(null);


        // 创建最上方的JLabel
        JLabel jlb_top = new JLabel("注册", JLabel.CENTER);
        jlb_top.setFont(new Font("", Font.BOLD, 18));
        jlb_top.setBounds(0, 0, 400, 30);
        jlb_top.setForeground(Color.white);
        jlb_top.setOpaque(true);
        jlb_top.setBackground(new Color(3, 37, 108));
        contentPane.add(jlb_top);

        //---- button1 ----
        button1.setText("提交");
        contentPane.add(button1);
        button1.setBackground(new Color(3, 37, 108));
        button1.setForeground(Color.black);
        button1.setBounds(150, 305, 100, 35);
        button1.addActionListener(this);

        //---- label1 ----
        label1.setText("昵称");
        label1.setHorizontalTextPosition(SwingConstants.CENTER);
        label1.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(label1);
        label1.setBounds(55, 150, 75, label1.getPreferredSize().height);

        //---- label2 ----
        label2.setText("个性签名");
        label2.setHorizontalTextPosition(SwingConstants.CENTER);
        label2.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(label2);
        label2.setBounds(55, 190, 75, 15);

        //---- label3 ----
        label3.setText("密码");
        label3.setHorizontalTextPosition(SwingConstants.CENTER);
        label3.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(label3);
        label3.setBounds(55, 230, 75, 15);

        //---- label4 ----
        label4.setText("确认密码");
        label4.setHorizontalTextPosition(SwingConstants.CENTER);
        label4.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(label4);
        label4.setBounds(55, 270, 75, label4.getPreferredSize().height);

        //---- label5 ----
        contentPane.add(label5);
        label5.setBounds(165, 33, 70, 70);

        //---- button2 ----
        button2.setText("选择头像");
        contentPane.add(button2);
        button2.setBackground(new Color(3, 37, 108));
        button2.setForeground(Color.black);
        button2.setBounds(160, 108, 80, button2.getPreferredSize().height);
        button2.addActionListener(this);

        contentPane.add(textField1);
        textField1.setBounds(145, 145, 200, textField1.getPreferredSize().height);
        contentPane.add(textField2);
        textField2.setBounds(145, 185, 200, 27);
        contentPane.add(textField3);
        textField3.setBounds(145, 225, 200, 27);
        contentPane.add(textField4);
        textField4.setBounds(145, 265, 200, 27);

        contentPane.setPreferredSize(new Dimension(400, 360));
        pack();
        setLocationRelativeTo(getOwner());
        setResizable(false);
        setVisible(true);
    }

    private JButton button1;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private Avatar label5;
    private JButton button2;
    private JTextField textField1;
    private JTextField textField2;
    private JPasswordField textField3;
    private JPasswordField textField4;

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == button1) {
            String nickName = textField1.getText();
            String sign = textField2.getText();
            String pwd = String.valueOf(textField3.getPassword());
            String againPwd = String.valueOf(textField4.getPassword());
            if (check(nickName, sign, pwd, againPwd)) {
//                UserService userService = new UserService();
//                if (userService.getClient() != null) {
//                    User register = userService.register(avatarID, nickName, sign, pwd);
//                    if (register != null) {
//                        JOptionPane.showMessageDialog(this, "注册成功!\n您的QQ号为：" + register.getQq());
//                        this.dispose();
//                    }
//                    userService.myStop();
//                } else {
//                    JOptionPane.showMessageDialog(this, "无法连接服务器!");
//                }
            }
        } else if (source == button2) {
            new SelectAvatar(this);
        }
    }

    private Boolean check(String n, String s, String p, String ap) {
        if (n.equals("")) {
            JOptionPane.showMessageDialog(this, "昵称不能为空!","warning",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (s.equals("")) {
            JOptionPane.showMessageDialog(this, "个性签名不能为空!","warning",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (p.equals("") || ap.equals("")) {
            JOptionPane.showMessageDialog(this, "密码不能为空，请重新输入！","warning",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!p.equals(ap)) {
            JOptionPane.showMessageDialog(this, "密码输入错误，请重新输入！","warning",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    class SelectAvatar extends JDialog implements ActionListener {
        public SelectAvatar(Window owner) {
            super(owner);
            initComponents();
            loadAvatar();
        }
        private void initComponents() {
            // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
            // Generated using JFormDesigner Evaluation license - unknown
            dialogPane = new JPanel();
            scrollPane1 = new JScrollPane();
            contentPanel = new JPanel();
            buttonBar = new JPanel();
            okButton = new JButton();

            //======== this ========
            setResizable(false);
            setVisible(true);
            Container contentPane = getContentPane();
            contentPane.setLayout(new BorderLayout());

            //======== dialogPane ========
            {
                dialogPane.setLayout(new BorderLayout());

                //======== scrollPane1 ========
                {

                    //======== contentPanel ========
                    {
                        contentPanel.setLayout(new GridLayout(0, 5, 2, 2));
                    }
                    scrollPane1.setViewportView(contentPanel);
                }
                dialogPane.add(scrollPane1, BorderLayout.CENTER);

                //======== buttonBar ========
                {
                    buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                    buttonBar.setLayout(new GridBagLayout());
                    ((GridBagLayout) buttonBar.getLayout()).columnWidths = new int[]{0, 80};
                    ((GridBagLayout) buttonBar.getLayout()).columnWeights = new double[]{1.0, 0.0};

                    //---- okButton ----
                    okButton.setText("OK");
                    okButton.addActionListener(this);
                    buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));
                }
                dialogPane.add(buttonBar, BorderLayout.PAGE_END);
            }
            contentPane.add(dialogPane, BorderLayout.CENTER);
            setSize(400, 300);
            setLocationRelativeTo(getOwner());
            // JFormDesigner - End of component initialization  //GEN-END:initComponents
        }

        private void loadAvatar() {
            File file = new File("client/src/main/resources/image/avatar");
            File[] tempList = file.listFiles();
            ArrayList<Avatar> avatars = new ArrayList<Avatar>();
            for (File file1 : tempList) {
                if (file1.getName().endsWith(".png")) {
                    String name = file1.getName();
                    String avatarID = name.substring(0, name.length() - 4);
                    Avatar avatar = new Avatar(avatarID, 70, 70);
                    avatars.add(avatar);
                }
            }
            for (Avatar avatar : avatars) {
                avatar.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        for (Avatar avatar1 : avatars) {
                            avatar1.setBorder(null);
                        }
                        avatar.setBorder(new LineBorder(Color.black));
                        avatarID = avatar.getAvatarID();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
                contentPanel.add(avatar);
            }
            contentPanel.revalidate();
            contentPanel.repaint();
        }

        private JPanel dialogPane;
        private JScrollPane scrollPane1;
        private JPanel contentPanel;
        private JPanel buttonBar;
        private JButton okButton;

        @Override
        public void actionPerformed(ActionEvent e) {
            label5.setAvatarID(avatarID);
            this.dispose();
        }

    }
}