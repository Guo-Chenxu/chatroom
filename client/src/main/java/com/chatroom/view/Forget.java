package com.chatroom.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author unknown
 */
public class Forget extends JFrame implements ActionListener {
    public Forget() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        button1 = new JButton();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        passwordField1 = new JPasswordField();
        passwordField2 = new JPasswordField();
        passwordField3 = new JPasswordField();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- button1 ----
        button1.setText("提交");
        contentPane.add(button1);
        button1.setBounds(145, 190, 110, 35);
        button1.setBackground(new Color(3, 37, 108));
        button1.setForeground(Color.white);
        button1.addActionListener(this);

        // 创建最上方的JLabel
        JLabel jlbTop = new JLabel("忘记密码", JLabel.CENTER);
        jlbTop.setFont(new Font("", Font.BOLD, 18));
        jlbTop.setBounds(0, 0, 400, 30);
        jlbTop.setForeground(Color.white);
        jlbTop.setOpaque(true);
        jlbTop.setBackground(new Color(3, 37, 108));
        contentPane.add(jlbTop);

        //---- label2 ----
        label2.setText("用户名");
        label2.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(label2);
        label2.setBounds(70, 75, 65, 15);

        //---- label3 ----
        label3.setText("新密码");
        label3.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(label3);
        label3.setBounds(70, 115, 65, 15);

        //---- label4 ----
        label4.setText("确认密码");
        label4.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(label4);
        label4.setBounds(70, 154, 65, 15);
        contentPane.add(passwordField1);
        passwordField1.setBounds(145, 70, 175, 27);
        contentPane.add(passwordField2);
        passwordField2.setBounds(145, 110, 175, 27);
        contentPane.add(passwordField3);
        passwordField3.setBounds(145, 149, 175, 27);

        contentPane.setPreferredSize(new Dimension(400, 260));
        pack();
        setLocationRelativeTo(getOwner());
        setVisible(true);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JButton button1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JPasswordField passwordField3;

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private Boolean check(String qq, String op, String p, String ap) {
        if (qq.equals("")) {
            JOptionPane.showMessageDialog(this, "QQ号不能为空！");
            return false;
        }
        if (qq.length() > 4) {
            JOptionPane.showMessageDialog(this, "QQ号格式错误，请重新输入！");
            return false;
        }
        if (op.equals("") || p.equals("") || ap.equals("")) {
            JOptionPane.showMessageDialog(this, "密码不能为空！");
            return false;
        }
        if (!p.equals(ap)) {
            JOptionPane.showMessageDialog(this, "密码错误，请重新输入！");
            return false;
        }
        return true;
    }

}
