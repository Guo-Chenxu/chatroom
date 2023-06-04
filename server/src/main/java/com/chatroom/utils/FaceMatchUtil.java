package com.chatroom.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 人脸对比
 */
public class FaceMatchUtil {

    private static final double THRESHOLD = 80;

    /**
     * 人脸对比
     *
     * @param imgStr1 base64字符串
     * @param imgStr2 base64字符串
     * @return 是否相同
     */
    public static boolean faceMatch(String imgStr1, String imgStr2) {
        if (imgStr1 == null || imgStr2 == null) {
            return false;
        }

        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
        try {
            ArrayList<HashMap<String, String>> map = new ArrayList<>();
            HashMap<String, String> map1 = new HashMap<>();
            HashMap<String, String> map2 = new HashMap<>();
            map1.put("image", imgStr1);
            map1.put("image_type", "BASE64");
            map1.put("face_type", "LIVE");
            map1.put("quality_control", "LOW");
            map1.put("liveness_control", "NONE");
            map2.put("image", imgStr2);
            map2.put("image_type", "BASE64");
            map2.put("face_type", "LIVE");
            map2.put("quality_control", "LOW");
            map2.put("liveness_control", "NONE");
            map.add(map1);
            map.add(map2);

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthUtil.getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);

            // 获取人脸比对的结果
            String result1 = JSON.toJSONString(JSONObject.parseObject(result).get("result"));
            double score = Double.parseDouble(String.valueOf(JSONObject.parseObject(result1).get("score")));
            System.out.println(score);
            return score >= THRESHOLD;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

//    public static void main(String[] args) {
//        FaceMatchUtil.faceMatch("", "");
//    }
}
