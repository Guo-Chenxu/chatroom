package com.chatroom.view.components;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Random;

/**
 * 封装JLabel 实现头像显示
 */
public class Avatar extends JLabel {

    private String avatarID;
    private int width;
    private int height;

    private static final File[] files = new File("D:\\JavaWork\\chatroom\\client\\src\\main\\resources\\image\\face").listFiles();

    /**
     * 现代化头像
     */
    public Avatar(String path, int width, int height) {
        this.width = width;
        this.height = height;
        ImageIcon imageIcon = new ImageIcon(path);
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        this.setIcon(imageIcon);
    }

    public static String getPath() {
        return files[new Random().nextInt(files.length - 1)].getPath();
    }

    public String getAvatarID() {
        return avatarID;
    }

    public void setAvatarID(String avatarID) {
        this.avatarID = avatarID;
        ImageIcon imageIcon = new ImageIcon("client/src/main/resources/image/avatar/" + avatarID + ".png");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        this.setIcon(imageIcon);
        this.repaint();
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
