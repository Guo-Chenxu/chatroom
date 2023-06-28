package com.chatroom.view;

import com.chatroom.entity.User;
import com.chatroom.service.impl.UserServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author unknown
 */
public class ChangPassword extends JFrame implements ActionListener {

    public ChangPassword() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        button1 = new JButton();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        username = new JTextField();
        password = new JPasswordField();
        passwordConfirm = new JPasswordField();

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
        JLabel jlbTop = new JLabel("修改密码", JLabel.CENTER);
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
        contentPane.add(username);
        username.setBounds(145, 70, 175, 27);
        contentPane.add(password);
        password.setBounds(145, 110, 175, 27);
        contentPane.add(passwordConfirm);
        passwordConfirm.setBounds(145, 149, 175, 27);

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
    private JTextField username;
    private JPasswordField password;
    private JPasswordField passwordConfirm;

    @Override
    public void actionPerformed(ActionEvent e) {
        // todo 修改密码, 直接调用下面的方法即可,参数是 用户名 密码
        String user = username.getText().trim();
        String pwd = new String(password.getPassword());
        String confirm = new String(passwordConfirm.getPassword());
        check(user,pwd,confirm);
        UserServiceImpl.getInstance().changePassword(user,pwd);
        this.dispose();
        JOptionPane.showMessageDialog(this, "密码修改成功！");
    }

    private Boolean check(String qq, String password, String confirm) {
        String REG = "^[a-zA-Z]\\w{5,17}$";
        if (qq.equals("")) {
            JOptionPane.showMessageDialog(this, "用户名不能为空！");
            return false;
        }else if(!qq.matches(REG)){
            JOptionPane.showMessageDialog(this, "用户名格式错误！");
            return false;
        }
        if(!password.matches(REG)){
            JOptionPane.showMessageDialog(this, "密码不能为空！");
            return false;
        }else if (password.equals("") || confirm.equals("")) {
            JOptionPane.showMessageDialog(this, "密码不能为空！");            JOptionPane.showMessageDialog(this, "密码格式错误！");
            return false;
        }else if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "密码错误，请重新输入！");
            return false;
        }
        return true;
    }

}
