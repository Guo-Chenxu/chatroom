package com.chatroom.view;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import com.chatroom.utils.MyImageUtil;

import com.github.sarxos.webcam.Webcam;

import java.io.IOException;
import java.net.Socket;
import java.util.Base64;
import java.util.List;
public class Camera {
    public static String captureAndEncodeImage(Webcam webcam) {
        // 获取当前相机图像
        BufferedImage image = webcam.getImage();
        // 图片压缩
        try {
            image = MyImageUtil.imageCompress(image.getWidth() * image.getHeight(), image);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        // 将图片转换为 BASE64 编码
        String base64Image = toBase64(image);
        return base64Image;
    }

    private static String toBase64(BufferedImage image) {
        String base64Image = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            javax.imageio.ImageIO.write(image, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            base64Image = Base64.getEncoder().encodeToString(imageBytes);
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64Image;
    }
}
