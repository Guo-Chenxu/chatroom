package com.chatroom.utils;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * 图片处理类
 */
public class MyImageUtil {

    public static BufferedImage imageGenerateSmall(BufferedImage image, double smallSize) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        int widthSmall = (int) (width / smallSize);
        int heightSmall = (int) (height / smallSize);
        BufferedImage newImage = new BufferedImage(widthSmall, heightSmall, BufferedImage.TYPE_INT_RGB);
        Graphics g = newImage.getGraphics();
        g.drawImage(image, 0, 0, widthSmall, heightSmall, null);
        g.dispose();
        ;
        return newImage;
    }

    public static BufferedImage imageCompress(long fileSize, BufferedImage image) throws Exception {
        BufferedImage newImage = image;
        int kb = 100 * 1024;
        if (fileSize > 100 * 1024) {
            int smallSize = (int) (fileSize % kb == 0 ? fileSize / kb : fileSize / kb + 1);
            double size = Math.ceil(Math.sqrt(smallSize));
            newImage = MyImageUtil.imageGenerateSmall(image, size);
        }
        return newImage;
    }
}

